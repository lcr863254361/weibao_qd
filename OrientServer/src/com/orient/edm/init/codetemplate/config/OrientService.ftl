<#if PACKAGE_NAME && PACKAGE_NAME != "">package ${PACKAGE_NAME};</#if>

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${HIBERNATEBEANPATH};
/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${CURRENTDATE}
 */
@Service
public class ${NAME}Service extends BaseService<${HIBERNATEBEANNAME}> implements I${NAME}Service {

    @Autowired
    I${NAME}Dao ${LOWERNAME}Dao;

    @Override
    public IBaseDao getBaseDao() {
        return this.${LOWERNAME}Dao;
    }
}
