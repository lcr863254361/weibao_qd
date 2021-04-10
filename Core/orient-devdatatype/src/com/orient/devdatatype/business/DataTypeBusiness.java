package com.orient.devdatatype.business;

import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.taskdata.IDataSubTypeEntityService;
import com.orient.sysmodel.service.taskdata.IDataTypeEntityService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 研发数据类型业务
 *
 * @author mengbin
 * @create 2016-07-04 下午5:25
 */
@Component
public class DataTypeBusiness extends BaseBusiness {

    @Autowired
    IDataTypeEntityService dataTypeEntityService;
    @Autowired
    IDataSubTypeEntityService dataSubTypeEntityService;
    @Autowired
    DataSubTypeBusiness dataSubTypeBusiness;





    /**
     *  根据数据分类获取所有的数据类型(只显示最新的,已经发布状态的)
     * @param rank  类型的分类 1 基本类型；2 扩展类型；4枚举类型；8 复杂类型
     * @return
     */
    public List<DataTypeEntity> getAllDataTypeListByRank( short rank ){

        Criterion fliter1 = Restrictions.eq("rank",rank);
        Criterion fliter2 = Restrictions.eq("isnewest",1);
        Criterion fliter3 = Restrictions.eq("status",DataTypeEntity.Status_Publish);
        return dataTypeEntityService.list(fliter1,fliter2,fliter3);

    }

    /**
     *
     * @param dataType
     * @return
     */
    public DataTypeEntity createDataType(DataTypeEntity dataType)
    {
        boolean sameName =  checkRename(dataType);
        if (sameName){
            return null;
        }
        dataType.setDatatypecode(new Date().getTime()+"");
        dataType.setCreatetime(new Date());
        User user = UserContextUtil.getCurrentUser();
        dataType.setUserid(user.getId());
        dataType.setIsnewest(1);
        dataType.setStatus(DataTypeEntity.Status_Publish);
        dataTypeEntityService.save(dataType);
        return dataType;
    }


    /**
     * 更新DataType,已经包括版本升级
     * @param dt
     * @return
     */
    public boolean updateDataType(DataTypeEntity dt)
    {
        boolean sameName =  checkRename(dt);
        if (sameName){
            return false;
        }
        DataTypeEntity newdt=  this.upVersion(dt.getId());
        newdt.setCheckstr(dt.getCheckstr());
        newdt.setDatatype(dt.getDatatype());
        newdt.setDatatypename(dt.getDatatypename());
        newdt.setDescription(dt.getDescription());
        newdt.setRank(dt.getRank());
        newdt.setStatus(DataTypeEntity.Status_Publish);
        newdt.setUserid(UserContextUtil.getCurrentUser().getId());
        newdt.setCreatetime(new Date());
        dataTypeEntityService.update(newdt);

        return true;
    }


    /**
     * 及联删除,把状态设置为已经废弃
     * @param dataTypeId
     * @return
     */
    public  int deleteDataTypeCascade(String dataTypeId){
        //TODO:检查是否已被DataObject引用
        DataTypeEntity dataType =  dataTypeEntityService.getById(dataTypeId);

        Criterion filter1 = Restrictions.eq("datatypecode",dataType.getDatatypecode());
        List<DataTypeEntity> allDataTypes = dataTypeEntityService.list(filter1);
        for (DataTypeEntity loopDt: allDataTypes){
            loopDt.setStatus(DataTypeEntity.Status_Abord);
            //如果是复杂类型,同时DataSubType也设置成无效
            if (loopDt.getRank()==DataTypeEntity.Rank_PHYSIC||loopDt.getRank()==DataTypeEntity.Rank_ENUM){

               List<DataSubTypeEntity> dstList =  dataSubTypeBusiness.getAllDataSubTypebyDataTypeId(loopDt.getId());
               for (DataSubTypeEntity dst: dstList){
                   dst.setStatus(DataSubTypeEntity.Status_Abord);
               }
            }
        }
        return 0;
    }


    /**
     *  升级DataType的版本,如果该版本已经不是最新版本,则获取最新的版并升级
     * @param dataTypeId 要升级的数据类型实例的Id
     * @return 新的版本实例
     */
   public DataTypeEntity upVersion(String dataTypeId ){
       DataTypeEntity olddt = dataTypeEntityService.getById(dataTypeId);
       if (olddt.getIsnewest()==0){
           //不是最新版本,则获取最新版本
           String dateTypeCode  =  olddt.getDatatypecode();
           olddt = getNewestDataTypeByTypeCode(dateTypeCode);
           if(olddt==null){
               return null;
           }
       }

       olddt.setIsnewest(0);
       dataTypeEntityService.update(olddt);//原来的老版本更新

       DataTypeEntity newdt = new DataTypeEntity();
       newdt.setCheckstr(olddt.getCheckstr());
       newdt.setDatatype(olddt.getDatatype());
       newdt.setDatatypecode(olddt.getDatatypecode());
       newdt.setDatatypename(olddt.getDatatypename());
       newdt.setDescription(olddt.getDescription());
       newdt.setUserid(UserContextUtil.getCurrentUser().getId());
       newdt.setIsnewest(1);
       newdt.setRank(olddt.getRank());
       newdt.setStatus(olddt.getStatus());
       newdt.setCreatetime(new Date());
       newdt.setVersion(olddt.getVersion()+1);
       dataTypeEntityService.save(newdt);

      //更新子类型的版本
       if(newdt.getRank()==DataTypeEntity.Rank_PHYSIC||newdt.getRank()==DataTypeEntity.Rank_ENUM){
           Criterion filter  =  Restrictions.eq("datatypeId",olddt.getId());

          List<DataSubTypeEntity> subTypes =   dataSubTypeEntityService.list(filter);
           for (DataSubTypeEntity subType : subTypes){
               dataSubTypeBusiness.upVersion(subType,newdt.getId());
           }
       }

       return  newdt;
    }



    /**
     *创建或修改数据类型时,判断是否与已有重名
     * @param dataType 有重名返回True
     * @return
     */
    public boolean checkRename(DataTypeEntity dataType)
    {

        boolean saveflag = false;

        List<DataTypeEntity> allDataType = getAllDataTypeListByRank(dataType.getRank());
        for (DataTypeEntity loopDataType:  allDataType)
        {

            if(loopDataType.getIsnewest()==1){
                if(!CommonTools.isNullString(loopDataType.getDatatypename())&&
                        loopDataType.getDatatypename().equals(dataType.getDatatypename())){
                    saveflag = true;
                }
                if(!CommonTools.isNullString(loopDataType.getDatatypename())&&
                        CommonTools.null2String(dataType.getId()).equals(loopDataType.getId().toString())&&
                        !CommonTools.isNullString(dataType.getId())){
                    //Id相同说明是相同数据
                    saveflag = false;
                }
            }
            if(saveflag==true){

                return saveflag;
            }
        }

        return saveflag;
    }

    /**
     * 根据数据类型,获取其历史版本(不包括当前版本)
     * @param dataType
     * @return
     */

    public List<DataTypeEntity> getHistoryVersionDataType(DataTypeEntity dataType){

        Restrictions.ne("isnewest",dataType.getIsnewest());

        List<Criterion> filterList = new ArrayList<>();
        filterList.add(Restrictions.eq("datatypecode",dataType.getDatatypecode()));
        filterList.add(Restrictions.eq("status",DataTypeEntity.Status_Publish));
        filterList.add( Restrictions.ne("isnewest",dataType.getIsnewest()));
        Order orders[] = new Order[1];
        orders[0] = Order.asc("version");

        Criterion[] data = filterList.toArray(new Criterion[0]);

       return dataTypeEntityService.list(data,orders);

    }

    /**
     * 根据Id获取DataType
     * @param dataTypeID
     * @return
     */
    public DataTypeEntity getDataType(String dataTypeID){

        return dataTypeEntityService.getById(dataTypeID);

    }

    /**
     * 根据DataTypeCode 获取最新版本的DataTypeCode
     * @param dataTypeCode
     * @return
     */
    public  DataTypeEntity getNewestDataTypeByTypeCode(String dataTypeCode){
        Criterion filter1 =  Restrictions.eq("datatypecode",dataTypeCode);
        Criterion fliter2 = Restrictions.eq("isnewest",1);
        Criterion fliter3 = Restrictions.eq("status",DataTypeEntity.Status_Publish);
        List<DataTypeEntity>   list = dataTypeEntityService.list(filter1,fliter2,fliter3);
        if (list.size()>0){
            return list.get(0);

        }
        return null;
    }
}
