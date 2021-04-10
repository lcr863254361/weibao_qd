#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Service
public class ${NAME}Service extends BaseService<${HIBERNATEBEANNAME}> implements I${NAME}Service {

    @Autowired
    I${NAME}Dao ${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Dao;

    @Override
    public IBaseDao getBaseDao() {
        return this.${NAME.substring(0,1).toLowerCase()}${NAME.substring(1)}Dao;
    }
}
