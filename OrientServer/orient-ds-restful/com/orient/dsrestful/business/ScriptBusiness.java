package com.orient.dsrestful.business;

import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.script.*;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.sysmodel.domain.sys.SeqGenerator;
import com.orient.sysmodel.service.sys.SeqGeneratorService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-26 20:42
 */
@Component
public class ScriptBusiness extends BaseBusiness {

    @Autowired
    SeqGeneratorService seqGeneratorService;

    @Autowired
    MetaDAOFactory metaDaoFactory;

    public ScriptResponse getById(long id) {
        ScriptResponse retVal = new ScriptResponse();
        ScriptBean result = new ScriptBean();
        try {
            SeqGenerator seqGenerator = seqGeneratorService.findById(id);
            String name = seqGenerator.getName();
            List<IColumn> list = metaDaoFactory.getColumnDAO().findColumnsByIsvalidAndSequenceName(new Long(1), name);
            result.setId(seqGenerator.getId());
            result.setName(seqGenerator.getName());
            result.setContent(seqGenerator.getContent());
            result.setEnable(seqGenerator.getEnable());
            result.setReturnType(seqGenerator.getReturnType());
            result.setUsed(list.size() > 0);
            retVal.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("服务器异常");
            return retVal;
        }
        return retVal;
    }

    public ScriptListResponse getList() {
        ScriptListResponse retVal = new ScriptListResponse();
        List<ScriptBean> result = new ArrayList<>();
        seqGeneratorService.findAll()
                .forEach(seqGenerator -> {
                    ScriptBean scriptBean = new ScriptBean();
                    scriptBean.setId(seqGenerator.getId());
                    scriptBean.setName(seqGenerator.getName());
                    scriptBean.setContent(seqGenerator.getContent());
                    scriptBean.setEnable(seqGenerator.getEnable());
                    scriptBean.setReturnType(seqGenerator.getReturnType());
                    result.add(scriptBean);
                });
        retVal.setResult(result);
        return retVal;
    }

    public AddScriptResponse insert(AddScriptRequest addScriptRequest) {
        AddScriptResponse retVal = new AddScriptResponse();
        AddScriptBean result = new AddScriptBean();
        try {
            String name = addScriptRequest.getName();
            if (!CommonTools.isNullString(name)) {
                List<SeqGenerator> list = seqGeneratorService.findByName(name);
                if (list.size() > 0) {
                    retVal.setSuccess(false);
                    retVal.setMsg("脚本名称重名，请重命名");
                    retVal.setResult(result);
                } else {
                    SeqGenerator seqGenerator = new SeqGenerator();
                    Long id = seqGeneratorService.findNextVal();
                    String content = addScriptRequest.getContent();
                    String returnType = addScriptRequest.getReturnType();
                    seqGenerator.setId(id);
                    seqGenerator.setName(name);
                    seqGenerator.setContent(content);
                    seqGenerator.setReturnType(returnType);
                    seqGenerator.setChanged("TRUE");
                    seqGeneratorService.insert(seqGenerator);
                    result.setId(id.toString());
                    retVal.setResult(result);
                    retVal.setMsg("保存成功");
                    retVal.setResult(result);
                }
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("脚本名称不能为空");
                retVal.setResult(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("服务端异常");
            retVal.setResult(result);
            return retVal;
        }
        return retVal;
    }

    public AddScriptResponse update(UpdateScriptRequest updateScriptRequest) {
        AddScriptResponse retVal = new AddScriptResponse();
        AddScriptBean result = new AddScriptBean();
        //查询脚本不存在则直接保存
        SeqGenerator seqGenerator = seqGeneratorService.findById(updateScriptRequest.getId());
        if (seqGenerator == null) {
            return insert(new AddScriptRequest(updateScriptRequest.getName(), updateScriptRequest.getContent(), updateScriptRequest.getReturnType()));
        }
        //根据id查询更新前的name,若此脚本正在被使用，则不能更新
        String name = updateScriptRequest.getName();
        //脚本正被使用,不可更新
        List msg = seqGeneratorService.checkScriptUsed("'" + name.replace("'", "''") + "'");
        if (!msg.isEmpty()) {
            retVal.setSuccess(false);
            retVal.setMsg("此脚本正在使用,不可更改");
            return retVal;
        }

        //名称已经存在,不可保存
        if (!CommonTools.isNullString(name)) {
            List<SeqGenerator> list = seqGeneratorService.findByName(name);
            for (SeqGenerator map : list) {
                Long mapId = map.getId();
                if (mapId.equals(updateScriptRequest.getId())) {
                    retVal.setSuccess(false);
                    retVal.setMsg("脚本名称重名");
                    return retVal;//脚本重名
                }
            }
        } else {
            retVal.setSuccess(false);
            retVal.setMsg("脚本名称不能为空");
            return retVal;
        }
        //更新
        seqGenerator.setName(name);
        seqGenerator.setContent(updateScriptRequest.getContent());
        seqGenerator.setReturnType(updateScriptRequest.getReturnType());
        seqGenerator.setEnable(updateScriptRequest.isEnable());
        seqGenerator.setChanged("TRUE");
        seqGeneratorService.update(seqGenerator);
        retVal.setMsg("保存成功");
        return retVal;
    }

    public CommonResponse delete(List<String> idList) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        List lockSchemaList = metaDaoFactory.getSchemaDAO().findByIsLock(1);
        if (lockSchemaList.size() > 0) {
            result.setStatus("1");
            retVal.setResult(result);
            retVal.setSuccess(false);
            retVal.setMsg("数据模型被锁定");
            return retVal;
        }
        for (String id : idList) {
            SeqGenerator seqGenerator = seqGeneratorService.findById(Long.parseLong(id));
            if (seqGenerator != null) {
                //脚本正被使用,不可删除
                List msg = seqGeneratorService.checkScriptUsed(seqGenerator.getName());
                if (!msg.isEmpty()) {
                    result.setStatus("1");
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("此脚本正在使用,不可更改");
                    return retVal;
                }
                seqGeneratorService.delete(seqGenerator);
            }
        }
        result.setStatus("0");
        retVal.setResult(result);
        retVal.setMsg("删除成功");
        return retVal;
    }

}
