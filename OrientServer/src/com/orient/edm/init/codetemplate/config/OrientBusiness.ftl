<#if PACKAGE_NAME && PACKAGE_NAME != "">package ${PACKAGE_NAME};</#if>
import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import ${HIBERNATEBEANPATH};

/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${CURRENTDATE}
 */
@Component
public class ${NAME}Business extends BaseHibernateBusiness<${HIBERNATEBEANNAME}> {

    @Autowired
    I${NAME}Service ${LOWERNAME}Service;

    @Override
    public I${NAME}Service getBaseService() {
        return ${LOWERNAME}Service;
    }
}
