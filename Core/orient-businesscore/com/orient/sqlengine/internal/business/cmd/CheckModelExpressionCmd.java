package com.orient.sqlengine.internal.business.cmd;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.ConsExpression;
import com.orient.metamodel.operationinterface.IEnum;
import com.orient.metamodel.operationinterface.IRestriction;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sqlengine.internal.sys.cmd.RestrictionCmd;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 校验数据是否满足定义在模型上的业务表达式命令
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 12, 2012
 */
@SuppressWarnings("unchecked")
public class CheckModelExpressionCmd extends AbstractCmd {

    protected EDMCommandService commandService;
    private static final long serialVersionUID = 1L;

    private IBusinessModel bm;
    private Map<String, String> modelData;

    StringBuilder msg = new StringBuilder();

    /**
     * 校验数据是否满足定义在模型上的业务表达式
     *
     * @param bm        业务模型
     * @param modelData 业务数据
     */
    public CheckModelExpressionCmd(IBusinessModel bm, Map<String, String> modelData) {
        this.bm = bm;
        this.modelData = modelData;
    }

    /**
     * @param jdbcTemplate
     * @return
     * @throws Exception
     */
    @Override
    protected Object executeInternal(JdbcTemplate jdbcTemplate) throws Exception {
        sql();
        return msg.toString();
    }

    /**
     * @return
     */
    @Override
    public String sql() {
        Set exps = bm.getMatrix().getMainTable().getCwmConsExpressions();
        Iterator expIter = exps.iterator();
        while (expIter.hasNext()) {
            ConsExpression exp = (ConsExpression) expIter.next();
            String expression = exp.getExpression();
            String message = exp.getResult();
            expression = changeExpression(expression, modelData);
            Object result = SqlEngineHelper.scriptExpression(expression, modelData);
            if (result == null) {
                msg.append("[error]系统错误");
                msg.append("[").append(message).append("]");
            } else if ((Boolean) result == true) {
                msg.append("[error]").append(message);
            }
        }
        return "";
    }

    /**
     * 把数据类校验表达式中的字段替换,约束进行转换
     *
     * @param expression
     * @param value
     * @return
     */
    private String changeExpression(String expression, Map<String, String> value) {
        StringBuffer sb = new StringBuffer(expression);
        int start = 0;
        while (start != -1) {//处理所有的约束字段
            int a = sb.indexOf("$[", start);
            if (a == -1) {
                break;
            }
            int b = sb.indexOf("]", a);
            int c = sb.indexOf("(", b);
            int d = sb.indexOf(")", c);
            int e = sb.indexOf("0", d);
            start = sb.indexOf("$[", e);
            String restrictionName = sb.substring(a + 2, b);
            String str1 = sb.substring(c + 1, d);
            String str2 = sb.substring(c + 3, d - 1);
            String flag = sb.substring(d + 1, e);

            StringBuffer sb2 = new StringBuffer();
            IRestriction res = bm.getSchema().getRestrictionByName(restrictionName);
            int type = res.getRestionType();
            //调用枚举命令
            RestrictionCmd resCmd = new RestrictionCmd(res);
            resCmd.setCommandService(commandService);
            if (type == 1 || type == 2) {
                List<IEnum> enumList = null;
                if (type == 1) {
                    //普通枚举
                    enumList = res.getAllEnums();
                } else if (type == 2) {
                    //数据表枚举,调用枚举命令
                    enumList = resCmd.tableEnum();
                }
                sb2.append("(");
                if (flag.equals("!=")) {
                    sb2.append("!");
                }
                //正则表达式中的字段名，js引擎不给替换，所以手动替换为真实值（str->strValue）
                sb2.append("new RegExp('\\\\b").append(str2).append("\\\\b').test([");
                for (IEnum m : enumList) {
                    if (m.isOpen()) {
                        sb2.append("'").append(m.getValue()).append("',");
                    }
                }
                if (enumList.size() > 0) {
                    sb2.deleteCharAt(sb2.length() - 1);
                }
                sb2.append("].join(' '))");
                sb2.append(")");
            } else if (type == 3 || type == 4) {
                String mindata = "";
                String maxdata = "";
                if (type == 3) {
                    mindata = SqlEngineHelper.Obj2String(res.getMinLength());
                    maxdata = SqlEngineHelper.Obj2String(res.getMaxLength());
                } else if (type == 4) {
                    Map<String, String> dynamicRange = resCmd.dynamicRange();
                    mindata = SqlEngineHelper.Obj2String(dynamicRange.get("mindata"));
                    maxdata = SqlEngineHelper.Obj2String(dynamicRange.get("maxdata"));
                }
                sb2.append("(");
                if (flag.equals("==")) {
                    if (!SqlEngineHelper.isNullString(mindata)) {
                        sb2.append(str1).append(">=").append(mindata);
                    }
                    if (!SqlEngineHelper.isNullString(maxdata)) {
                        if (!SqlEngineHelper.isNullString(mindata)) {
                            sb2.append(" && ");
                        }
                        sb2.append(str1).append("<=").append(maxdata);
                    }
                } else if (flag.equals("!=")) {
                    if (!SqlEngineHelper.isNullString(mindata)) {
                        sb2.append(str1).append("<").append(mindata);
                    }
                    if (!SqlEngineHelper.isNullString(maxdata)) {
                        if (!SqlEngineHelper.isNullString(mindata)) {
                            sb2.append(" || ");
                        }
                        sb2.append(str1).append(">").append(maxdata);
                    }
                }
                sb2.append(")");
            }
            sb.replace(a, e + 1, sb2.toString());
        }
        expression = sb.toString();

        //将${name}转换为name
        for (Iterator<String> it = value.keySet().iterator(); it.hasNext(); ) {
            String name = it.next();
            expression = expression.replaceAll("\\$\\{" + name + "\\}", name);
        }
        return expression;
    }

    public void setCommandService(EDMCommandService commandService) {
        this.commandService = commandService;
    }

}
