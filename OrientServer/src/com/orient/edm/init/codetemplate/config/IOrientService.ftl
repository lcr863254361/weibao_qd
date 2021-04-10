<#if PACKAGE_NAME && PACKAGE_NAME != "">package ${PACKAGE_NAME};</#if>
import com.orient.sysmodel.service.IBaseService;
import ${HIBERNATEBEANPATH};
/**
 * ${DESCRIPTION}
 * @author ${USER}
 * @create ${CURRENTDATE}
 */
public interface I${NAME}Service extends IBaseService<${HIBERNATEBEANNAME}> {
}
