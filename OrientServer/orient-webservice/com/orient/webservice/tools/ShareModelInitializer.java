package com.orient.webservice.tools;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metadomain.Schema;
import com.orient.sqlengine.api.ISqlEngine;
import org.springframework.stereotype.Component;

@Component
public class ShareModelInitializer {

    private IBusinessModelService businessModelService;

    private ISqlEngine sqlEngine;

//	private UserRight userRight;

    private String collabSchema;


    /**
     * 初始化模型数据.
     * <p>
     * <p>数据模型中数据初始化</p>
     *
     * @param schema
     * @author [创建人] qjl <br/>
     * [创建时间] 2014-7-1 下午3:23:17 <br/>
     * @see
     */
    public void initSchema(Schema schema) {

        if (collabSchema.equals(schema.getName())) {

            IBusinessModel businessModel = businessModelService.getBusinessModelBySName("PRORIGHT", schema.getId(), BusinessModelEnum.Table);
//			saveRightData(userRight, businessModel);

        }


    }


    /**
     * 保存权限数据.
     *
     * <p>权限数据递归入库</p>
     * @author [创建人] qjl <br/>
     * 		   [创建时间] 2014-7-1 下午4:42:46 <br/>
     * @param userRight
     * @param businessModel
     * @see
     */
//	private void saveRightData(UserRight userRight, IBusinessModel businessModel)
//	{
//
//		String schemaId = businessModel.getSchema().getId();
//		String tRightId = businessModel.getId();
//
//		Map<String, String> dataMap = new HashMap<String, String>();
//		dataMap.put("PR_NAME_"+tRightId, userRight.getRightName());
//		dataMap.put("PR_CODE_"+tRightId, userRight.getRightCode());
//		dataMap.put("PR_DESCRIPTION_"+tRightId, userRight.getRightDes());
//
//		if(userRight.getParent()!=null) {
//			dataMap.put("PRORIGHT_"+schemaId+"_ID", userRight.getParent().getRightId());
//		}
//
//		userRight.setRightId(sqlEngine.getBmService().insertModelData(businessModel, dataMap));
//
//		for(UserRight child : userRight.getChildren())
//		{
//			saveRightData(child, businessModel);
//		}
//
//
//	}


    /**
     * @return the businessModelService
     */
    public IBusinessModelService getBusinessModelService() {
        return businessModelService;
    }


    /**
     * @param businessModelService the businessModelService to set
     */
    public void setBusinessModelService(IBusinessModelService businessModelService) {
        this.businessModelService = businessModelService;
    }


    /**
     * @return the sqlEngine
     */
    public ISqlEngine getSqlEngine() {
        return sqlEngine;
    }


    /**
     * @param sqlEngine the sqlEngine to set
     */
    public void setSqlEngine(ISqlEngine sqlEngine) {
        this.sqlEngine = sqlEngine;
    }


//	/**
//	 * @return the userRight
//	 */
//	public UserRight getUserRight()
//	{
//		return userRight;
//	}
//
//
//	/**
//	 * @param userRight the userRight to set
//	 */
//	public void setUserRight(UserRight userRight)
//	{
//		this.userRight = userRight;
//	}


    /**
     * @return the collabSchema
     */
    public String getCollabSchema() {
        return collabSchema;
    }


    /**
     * @param collabSchema the collabSchema to set
     */
    public void setCollabSchema(String collabSchema) {
        this.collabSchema = collabSchema;
    }


}
