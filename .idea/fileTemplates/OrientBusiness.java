#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import com.orient.sysmodel.service.PageBean;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Component
public class ${NAME}Business extends BaseBusiness {

    @Autowired
    I${NAME}Service ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service;

    public Integer count() {
        return ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service.count();
    }


    public List<${HIBERNATEBEANNAME}> list(Integer page, Integer limit) {
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.addOrder(Order.asc("id"));
        return ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service.listByPage(pageBean);
    }

    public void delete(Long[] toDelIds) {
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service.delete(toDelIds);
    }

    public void save(${HIBERNATEBEANNAME} formValue) {
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service.save(formValue);
    }

    public void update(${HIBERNATEBEANNAME} formValue) {
        ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Service.update(formValue);
    }
}
