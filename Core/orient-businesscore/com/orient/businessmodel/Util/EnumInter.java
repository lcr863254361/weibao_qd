package com.orient.businessmodel.Util;

import com.orient.businessmodel.bean.IBusinessColumn;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName EnumInter
 * 业务模型的类型分类;字段分类;Sql操作的分类;Sql连接符分类
 * @date Apr 13, 2012
 */

public interface EnumInter {
    public enum BusinessModelEnum implements EnumInter {
        Table, View, BusinessModelEnum;

        public enum BusinessTableEnum implements EnumInter {
            SimpleTable, SysTable, ShareTable, DynamicTable;

            public static BusinessTableEnum getBusinessTableType(String type) {
                if (null == type) {
                    return null;
                }
                if (type.equals("0")) {
                    return SimpleTable;
                }
                if (type.equals("1")) {
                    return SysTable;
                }
                if (type.equals("2")) {
                    return ShareTable;
                }
                if (type.equals("3")) {
                    return DynamicTable;
                }
                return null;
            }
        }

        public enum BusinessColumnEnum implements EnumInter {
            C_Relation, C_Arith, C_Date, C_DateTime, C_Boolean,
            C_SingleEnum, C_MultiEnum, C_SingleTableEnum, C_MultiTableEnum,
            C_Integer, C_BigInteger, C_Decimal, C_Float, C_Double, C_Text, C_Simple,
            C_Dynamic, C_NameValue, C_SubTable, C_File, C_Check, C_Ods, C_Hadoop;

            public static BusinessColumnEnum getBusinessColType(IBusinessColumn bc) {
                String init_type = bc.getCol().getType();
                String category = String.valueOf(bc.getCol().getCategory());

                String restriction_type = "";
                boolean is_multi_selected = false;
                if (bc.getCol().getRefRestriction() != null) {
                    restriction_type = Obj2String(bc.getCol().getRefRestriction().getRestionType());
                    is_multi_selected = bc.getCol().getRefRestriction().isMutiSelected();
                }

                if (category.equals("2")) {
                    return C_Relation;
                }

                if (category.equals("3")) {
                    return C_Arith;
                }

                if (init_type.equals("Date")) {
                    return C_Date;
                }
                if (init_type.equals("DateTime")) {
                    return C_DateTime;
                }

                if (init_type.equals("Boolean")) {
                    return C_Boolean;
                }

                if (restriction_type.equals("1")) {
                    if (!is_multi_selected) {
                        return C_SingleEnum;
                    } else {
                        return C_MultiEnum;
                    }
                }

                if (restriction_type.equals("2")) {
                    if (!is_multi_selected) {
                        return C_SingleTableEnum;
                    } else {
                        return C_MultiTableEnum;
                    }
                }

                if (init_type.equals("Integer")) {
                    return C_Integer;
                }

                if (init_type.equals("BigInteger")) {
                    return C_BigInteger;
                }

                if (init_type.equals("Decimal")) {
                    return C_Decimal;
                }

                if (init_type.equals("Float")) {
                    return C_Float;
                }

                if (init_type.equals("Double")) {
                    return C_Double;
                }

                if (init_type.equals("Text")) {
                    return C_Text;
                }
                if (init_type.equals("NameValue")) {
                    return C_NameValue;
                }
                if (init_type.equals("SubTable")) {
                    return C_SubTable;
                }
                if (init_type.equals("File")) {
                    return C_File;
                }
                if (init_type.equals("Check")) {
                    return C_Check;
                }
                if (init_type.equals("ODS")) {
                    return C_Ods;
                }
                if (init_type.equals("Hadoop")) {
                    return C_Hadoop;
                }
                return C_Simple;
            }

        }

        public static String Obj2String(Object obj) {
            return obj == null ? "" : obj.toString();
        }

        public static BusinessModelEnum getBusinessModelType(String type) {
            if (type.equals("0")) {
                return Table;
            }
            if (type.equals("1")) {
                return View;
            }
            return null;
        }

    }

    public enum SqlOperation implements EnumInter {
        Like, Equal, UnEqual, In, NotIn, BetweenAnd, IsNull, IsNotNull, Contains, Great, GreatEqual, Less, LessEqual;

        public static SqlOperation getSqlOperation(String type) {
            if (null == type) {
                return null;
            }
            if (type.equalsIgnoreCase("LIKE")) {
                return Like;
            }
            if (type.equalsIgnoreCase("=")) {
                return Equal;
            }
            if (type.equalsIgnoreCase("!=")) {
                return UnEqual;
            }
            if (type.equalsIgnoreCase("in")) {
                return In;
            }
            if (type.equalsIgnoreCase("notin")) {
                return NotIn;
            }
            if (type.equalsIgnoreCase("BetweenAnd")) {
                return BetweenAnd;
            }
            if (type.equalsIgnoreCase("IsNull")) {
                return IsNull;
            }
            if (type.equalsIgnoreCase("IsNotNull")) {
                return IsNotNull;
            }
            if (type.equalsIgnoreCase("Contains")) {
                return Contains;
            }
            if (type.equalsIgnoreCase("Great")) {
                return Great;
            }
            if (type.equalsIgnoreCase("GreatEqual")) {
                return GreatEqual;
            }
            if (type.equalsIgnoreCase("Less")) {
                return Less;
            }
            if (type.equalsIgnoreCase("LessEqual")) {
                return LessEqual;
            }
            return null;
        }

        public String toString() {
            String opStr = null;
            switch (this) {
                case Like:
                    opStr = "like";
                    break;
                case Equal:
                    opStr = "=";
                    break;
                case UnEqual:
                    opStr = "!=";
                    break;
                case In:
                    opStr = "in";
                    break;
                case NotIn:
                    opStr = "not in";
                    break;
                case BetweenAnd:
                    opStr = "BetweenAnd";
                    break;
                case IsNull:
                    opStr = "Is Null";
                    break;
                case IsNotNull:
                    opStr = "Is Not Null";
                    break;
                case Contains:
                    opStr = "Contains";
                    break;
                case Great:
                    opStr = ">";
                    break;
                case GreatEqual:
                    opStr = ">=";
                    break;
                case Less:
                    opStr = "<";
                    break;
                case LessEqual:
                    opStr = "<=";
                    break;

            }
            return opStr;
        }
    }

    public enum SqlConnection implements EnumInter {
        And, Or;

        public static EnumInter getSqlConnection(String type) {
            if (type.equalsIgnoreCase("And")) {
                return And;
            }
            if (type.equalsIgnoreCase("Or")) {
                return Or;
            }
            return null;
        }

        public String toString() {
            String connectStr = null;
            switch (this) {
                case And:
                    connectStr = "and";
                    break;
                case Or:
                    connectStr = "or";
                    break;

            }
            return connectStr;
        }
    }
}
