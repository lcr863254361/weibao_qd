#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
import com.orient.sysmodel.dao.impl.BaseHibernateDaoImpl;
import org.springframework.stereotype.Repository;
/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Repository
public class ${NAME}Dao extends BaseHibernateDaoImpl implements I${NAME}Dao {
}
