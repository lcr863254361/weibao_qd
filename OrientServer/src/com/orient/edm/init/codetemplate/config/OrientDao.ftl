<#if PACKAGE_NAME && PACKAGE_NAME != "">package ${PACKAGE_NAME};</#if>
import com.orient.sysmodel.dao.impl.BaseHibernateDaoImpl;
import org.springframework.stereotype.Repository;
/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${CURRENTDATE}
 */
@Repository
public class ${NAME}Dao extends BaseHibernateDaoImpl implements I${NAME}Dao {
}
