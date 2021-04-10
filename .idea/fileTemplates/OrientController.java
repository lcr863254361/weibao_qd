#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import com.orient.web.base.ExtFormResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Controller
@RequestMapping("/${NAME}")
public class ${NAME}Controller {
    @Autowired
    ${NAME}Business ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business;
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<${HIBERNATEBEANNAME}> list(Integer page, Integer limit) {
        ExtGridData<${HIBERNATEBEANNAME}> retVal = new ExtGridData<>();
        Integer count = ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business.count();
        retVal.setTotalProperty(count);
        retVal.setResults(${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business.list(page, limit));
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public ExtFormResponseData create(${HIBERNATEBEANNAME} formValue) {
        ExtFormResponseData retVal = new ExtFormResponseData();
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public ExtFormResponseData update(${HIBERNATEBEANNAME} formValue) {
        ExtFormResponseData retVal = new ExtFormResponseData();
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business.update(formValue);
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
    public ExtFormResponseData delete(Long[] toDelIds) {
        ExtFormResponseData retVal = new ExtFormResponseData();
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Business.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
