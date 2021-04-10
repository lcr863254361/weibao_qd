<#if PACKAGE_NAME && PACKAGE_NAME != "">package ${PACKAGE_NAME};</#if>
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ${HIBERNATEBEANPATH};

/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${CURRENTDATE}
 */
@Controller
@RequestMapping("/${NAME}")
public class ${NAME}Controller  extends BaseController{
    @Autowired
    ${NAME}Business ${LOWERNAME}Business;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<${HIBERNATEBEANNAME}> list(Integer page, Integer limit, ${HIBERNATEBEANNAME} filter) {
        return ${LOWERNAME}Business.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(${HIBERNATEBEANNAME} formValue) {
        CommonResponseData retVal = new CommonResponseData();
        ${LOWERNAME}Business.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(${HIBERNATEBEANNAME} formValue) {
        CommonResponseData retVal = new CommonResponseData();
        ${LOWERNAME}Business.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        ${LOWERNAME}Business.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
