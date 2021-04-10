--*******************************************--
--              算法相关                    --
--*******************************************--

Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A1', '总和', 0, '数字型算法', '算法【总和】，数据库内置算法，计算某数值型列的数据的总和，参数的类型必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 1, 0, NULL, 
    NULL, NULL, 'SUM(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A2', '均值', 0, '数字型算法', '算法【均值】，数据库内置算法，计算某数值型列的数据的平均值，参数的类型必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 1, 0, NULL, 
    NULL, NULL, 'AVG(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A3', '相加', 0, '数字型算法', '算法【相加】，数据库内置算法，提供多个数值型列的数据相加得到的数据，参数不定，但必须都是数字类型，用户需注意参数前后顺序', 
    NULL, NULL, 0, 'Number', NULL, 
    'Number', 1, 0, 2, NULL, 
    NULL, NULL, '+', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A4', '相减', 0, '数字型算法', '算法【相减】，数据库内置算法，提供多个数值型列的数据相减得到的数据，参数不定，但必须都是数字类型，用户需注意参数前后顺序', 
    NULL, NULL, 0, 'Number', NULL, 
    'Number', 1, 0, 2, NULL, 
    NULL, NULL, '-', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A5', '相乘', 0, '数字型算法', '算法【相乘】，数据库内置算法，提供多个数值型列的数据相乘得到的数据，参数不定，但必须都是数字类型', 
    NULL, NULL, 0, 'Number', NULL, 
    'Number', 1, 0, 2, NULL, 
    NULL, NULL, '*', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A6', '相除', 0, '数字型算法', '算法【相除】，数据库内置算法，提供多个数值型列的数据相除得到的数据，参数不定，但必须都是数字类型，且注意除数不为0', 
    NULL, NULL, 0, 'Number', NULL, 
    'Number', 1, 0, 2, NULL, 
    NULL, NULL, '/', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A7', '绝对值', 0, '数字型算法', '算法【绝对值】，数据库内置算法，提供单个数值型列的数据的绝对值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 0, 0, NULL, 
    NULL, NULL, 'ABS(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A8', '最大值', 0, '数字型算法', '算法【最大值】，数据库内置算法，提供单个数值型列的数据的最大值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 1, 0, NULL, 
    NULL, NULL, 'MAX(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A9', '最小值', 0, '数字型算法', '算法【最小值】，数据库内置算法，提供单个数值型列的数据的最小值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 1, 0, NULL, 
    NULL, NULL, 'MIN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A10', '方根', 0, '数字型算法', '算法【方根】，数据库内置算法，提供某个数值型列M的数据的n次方根，参数为2个，参数m为具体的数据列，参数n为几次方根，都必须都是数字类型，参数M必须是非负数', 
    NULL, NULL, 2, 'Number,Number', NULL, 
    'Number', 1, 0, 0, NULL, 
    NULL, NULL, 'POWER(=1=,=2=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A11', '平方根', 0, '数字型算法', '算法【平方根】，数据库内置算法，提供某个数值型列M的数据的平方根，参数为一个，必须是数字类型，且必须为非负数', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 1, 0, 0, NULL, 
    NULL, NULL, 'SQRT(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A12', '取模', 0, '数字型算法', '算法【取模】，数据库内置算法，该算法返回参数m除以参数n的模数，或者余数。如果n为0，则返回m。参数m和n必须是数字类型', 
    NULL, NULL, 2, 'Number,Number', NULL, 
    'Number', 1, 0, 0, NULL, 
    NULL, NULL, 'MOD(=1=,=2=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A13', '行数', 0, '通用算法', '算法【行数】，数据库内置算法，提供查询列的行数，参数为一个，类型任意', 
    NULL, NULL, 1, 'General', NULL, 
    'Double', 1, 1, 0, NULL, 
    NULL, NULL, 'COUNT(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A14', '反余弦值', 0, '数字型算法', '算法【反余弦值】，数据库内置算法，返回一个数字的反余弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'ACOS(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A15', '反正弦值', 0, '数字型算法', '算法【反正弦值】，数据库内置算法，返回一个数字的反正弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'ASIN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A16', '反正切值', 0, '数字型算法', '算法【反正切值】，数据库内置算法，返回一个数字的反正切值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'ATAN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A27', '正切值', 0, '数字型算法', '算法【正切值】，数据库内置算法，返回一个数字的正切值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'TAN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A28', '双曲正切值', 0, '数字型算法', '算法【双曲正切值】，数据库内置算法，返回一个数字的双曲正切值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'SINH(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A17', '余弦值', 0, '数字型算法', '算法【余弦值】，数据库内置算法，返回一个数字的余弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'COS(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A18', '双曲余弦值', 0, '数字型算法', '算法【双曲余弦值】，数据库内置算法，返回一个数字的双曲余弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'COSH(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A19', 'e的x次方根', 0, '数字型算法', '算法【e的x次方根】，数据库内置算法，返e的x方根值。参数为1个既e的方根数x，必须是数字类型。e=2.71828183...', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'EXP(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A20', '自然对数值', 0, '数字型算法', '算法【自然对数值】，数据库内置算法，返回一个数字的自然对数值。参数为1个，必须是数字类型，且必须大于0。', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'LN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A21', '对数值', 0, '数字型算法', '算法【对数值】，数据库内置算法，返回一个以m为底n的对数值log(m,n)，参数为2个，必须是数字类型。', 
    NULL, NULL, 2, 'Number,Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'LOG(=1=,=2=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A22', '正弦值', 0, '数字型算法', '算法【正弦值】，数据库内置算法，返回一个数字的正弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'SIN(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A23', '双曲正弦值', 0, '数字型算法', '算法【双曲正弦值】，数据库内置算法，返回一个数字的双曲正弦值，参数为1个，必须是数字类型', 
    NULL, NULL, 1, 'Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, 'TANH(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A24', '四舍五入截取', 0, '数字型算法', '算法【四舍五入截取】，数据库内置算法，返回四舍五入到小数点后制定位数后的值。参数为1个或2个，一个参数时截取到小数点后一位，2个参数时第二个参数是截取到小数点后的位数。', 
    NULL, NULL, 0, 'Number,Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A25', '按精度截取', 0, '数字型算法', '算法【按精度截取】，数据库内置算法，返回按照指定精度截取数字后的值。参数为1个或2个，一个参数时截取到小数点后一位，2个参数时第二个参数是截取到小数点后的位数。', 
    NULL, NULL, 0, 'Number,Number', NULL, 
    'Number', 0, 0, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A26', '字符串长度', 0, '字符串算法', '算法【字符串长度】，数据库内置算法，返回给定字符串的长度。参数为1个，必须为字符串型。', 
    NULL, NULL, 1, 'String', NULL, 
    'Integer', 0, 0, 0, NULL, 
    NULL, NULL, NULL, NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A29', '最大日期', 0, '日期型算法', '算法【最大日期】，数据库内置算法，提供单个日期型列的数据的最大日期，参数为1个，必须是日期类型', 
    NULL, NULL, 1, 'Date', NULL, 
    'Date', 1, 1, 0, NULL, 
    NULL, NULL, 'MAX(=1=)', NULL, NULL, 
    NULL);
Insert into CWM_ARITHMETIC
   (ID, NAME, TYPE, CATEGORY, DESCRIPTION, 
    FILE_NAME, METHOD_NAME, PARA_NUMBER, PARA_TYPE, REF_LIB, 
    DATA_TYPE, IS_VALID, ARITH_TYPE, LEAST_NUMBER, CLASS_NAME, 
    CLASS_PACKAGE, FILE_NUMBER, ARITH_METHOD, PID, MAIN_JAR, 
    FILE_LOCATION)
 Values
   ('A30', '最小日期', 0, '日期型算法', '算法【最小日期】，数据库内置算法，提供单个日期型列的数据的最小日期，参数为1个，必须是日期类型', 
    NULL, NULL, 1, 'Date', NULL, 
    'Date', 1, 1, 0, NULL, 
    NULL, NULL, 'MIN(=1=)', NULL, NULL, 
    NULL);
COMMIT;



--*******************************************--
--              类型名称相关                    --
--*******************************************--
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('1', '系统错误', 'LOG', '日志类型', 'sysError');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('2', '修改密码', 'LOG', '日志类型', 'updatePwd');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('3', '新建用户', 'LOG', '日志类型', 'insertInfoUser');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('4', '修改用户', 'LOG', '日志类型', 'updateInfoUser');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('5', '删除用户', 'LOG', '日志类型', 'deleteInfoUser');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('6', '新建角色', 'LOG', '日志类型', 'insertInfoRole');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('7', '删除角色', 'LOG', '日志类型', 'deleteInfoRole');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('8', '修改角色权限', 'LOG', '日志类型', 'updateInfoRole');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('9', '给角色分配用户', 'LOG', '日志类型', 'roleToInfoUser');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('10', '给角色分配功能点', 'LOG', '日志类型', 'roleToInfoFunction');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('11', '给角色分配权限', 'LOG', '日志类型', 'roleToInfoRight');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('12', '新建功能点', 'LOG', '日志类型', 'insertInfoFunction');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('13', '修改功能点', 'LOG', '日志类型', 'updateInfoFunction');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('14', '删除功能点', 'LOG', '日志类型', 'deleteInfoFunction');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('15', '新建部门', 'LOG', '日志类型', 'insertInfoDept');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('16', '修改部门', 'LOG', '日志类型', 'updateInfoDept');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('17', '删除部门', 'LOG', '日志类型', 'deleteInfoDept');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('18', '修改表访问权限', 'LOG', '日志类型', 'updateInfoPermission');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('19', '数据备份', 'LOG', '日志类型', 'dataBackup');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('20', '数据恢复', 'LOG', '日志类型', 'dataRecovery');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('21', '流程定义删除', 'LOG', '日志类型', 'deleteProcessDefinition');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('22', '流程实例删除', 'LOG', '日志类型', 'deleteProcessInstance');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('23', '流程实例强制执行', 'LOG', '日志类型', 'executeProcessInstance');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('24', '流程实例终止', 'LOG', '日志类型', 'terminateProcessInstance');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('25', '用户登录', 'LOG', '日志类型', 'userLogin');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('26', '用户注销', 'LOG', '日志类型', 'userLogout');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('27', '数据修改', 'LOG', '日志类型', 'updateData');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('28', '数据录入', 'LOG', '日志类型', 'insertData');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('29', '数据删除', 'LOG', '日志类型', 'deleteData');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('30', '数据导出', 'LOG', '日志类型', 'exportData');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('31', '单表数据备份', 'LOG', '日志类型', 'singleTableBackup');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('32', '单表数据恢复', 'LOG', '日志类型', 'singleTableRecovery');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('33', '文件下载', 'LOG', '日志类型', 'fileDownload');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('34', '文件上传', 'LOG', '日志类型', 'fileUpload');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('35', '文件删除', 'LOG', '日志类型', 'fileDelete');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('36', '设置代办人员', 'LOG', '日志类型', 'setSubPerson');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('37', '添加算法', 'LOG', '日志类型', 'addAlgorithm');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('38', '删除算法', 'LOG', '日志类型', 'deleteAlgorithm');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('39', '流程启动', 'LOG', '日志类型', 'startProcess');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('40', '流程审批', 'LOG', '日志类型', 'approveProcess');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('41', 'ETL导入', 'LOG', '日志类型', 'importETL');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('42', '数据查看', 'LOG', '日志类型', 'detailData');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('43', '给角色分配算法', 'LOG', '日志类型', 'roleToInfoAlgorithm');
COMMIT;
  --end wubing 修改日志类型   
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('1', '规则1', 'FPR', '文件解析规则', '文件解析规则');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('2', '规则2', 'FPR', '文件解析规则', '文件解析规则');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('3', '规则3', 'FPR', '文件解析规则', '文件解析规则');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('4', '规则4', 'FPR', '文件解析规则', '文件解析规则');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('5', '规则5', 'FPR', '文件解析规则', '文件解析规则');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('1', 'txt', 'FILE', '允许上传的文件类型', 'application/txt');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('2', 'doc', 'FILE', '允许上传的文件类型', 'application/msword');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('3', 'docx', 'FILE', '允许上传的文件类型', 'application/msword');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('4', 'xls', 'FILE', '允许上传的文件类型', 'application/vnd.ms-excel');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('5', 'xlsx', 'FILE', '允许上传的文件类型', 'application/vnd.ms-excel');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('6', 'ppt', 'FILE', '允许上传的文件类型', 'application/vnd.ms-powerpoint');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('7', 'pptx', 'FILE', '允许上传的文件类型', 'application/vnd.ms-powerpoint');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('8', 'xml', 'FILE', '允许上传的文件类型', 'application/xml');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('9', 'pdf', 'FILE', '允许上传的文件类型', 'application/pdf');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('10', 'rar', 'FILE', '允许上传的文件类型', 'application/rar');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('11', 'zip', 'FILE', '允许上传的文件类型', 'application/zip');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('12', 'gif', 'FILE', '允许上传的文件类型', 'image/gif');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('13', 'bmp', 'FILE', '允许上传的文件类型', 'image/bmp');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('14', 'jpg', 'FILE', '允许上传的文件类型', 'image/jpeg');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('15', 'jpeg', 'FILE', '允许上传的文件类型', 'image/jpeg');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('16', 'png', 'FILE', '允许上传的文件类型', 'image/png');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('17', 'avi', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('18', '3gp', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('19', 'rm', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('20', 'rmvb', 'FILE', '允许上传的文件类型', 'application/vnd.rn-realmedia');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('21', 'wmv', 'FILE', '允许上传的文件类型', 'video/x-ms-wmv');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('22', 'mkv', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('23', 'vob', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('24', 'mp4', 'FILE', '允许上传的文件类型', 'video/x-msvideo');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('1', '饼图2D', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('2', '分离型饼图2', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('3', '三维饼图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('4', '饼图3D', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('5', '三维分离型饼图5', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('6', '柱形图2D', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('7', '三维簇状柱形图7', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('8', '柱形图3D', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('9', '三维堆积柱形图9', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('10', '面积图10', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('11', '三维面积图11', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('12', '折线图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('13', '三维折线图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('14', '三维曲面图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('15', '三维柱形图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('16', '雷达图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('17', '散点图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('18', '百分比堆积面积图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('19', '三维百分比堆积面积图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('20', '折线散点图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('21', '曲线图', 'CHART', 'JFreeChart图表类型', 'SHOW');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('22', '时间序列图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('23', '三维柱形圆锥图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('24', '盘高-盘低-收盘图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('25', '开盘-盘高-盘低-收盘图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('26', '曲面图(俯视框架图)', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('27', '气泡图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('28', '簇状条形图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('29', '数据点折线图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('30', '无数据点折线散点图', 'CHART', 'JFreeChart图表类型', 'HIDE');
Insert into CWM_CODETONAME
   (ID, NAME, TYPEID, TYPENAME, REMARK)
 Values
   ('31', '无数据点平滑线散点图', 'CHART', 'JFreeChart图表类型', 'HIDE');
COMMIT;



--*******************************************--
--              系统枚举相关                 --
--*******************************************--
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '4', '技术支持', NULL, 'a', 
    'jszc', 4, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '5', '项目经理', NULL, 'a', 
    'pm', 5, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '6', '技术主任', NULL, 'a', 
    'jszr', 6, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '7', '架构师', NULL, 'a', 
    'jgs', 7, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '8', '销售经理', NULL, 'a', 
    'xs', 8, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '9', '部门经理', NULL, 'a', 
    'bmjl', 9, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '1', '测试工程师', NULL, 'a', 
    'cs', 3, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u1', '1', '男', NULL, NULL, 
    'male', 1, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u1', '0', '女', NULL, NULL, 
    'female', 2, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '2', '网络工程师', NULL, 'a', 
    'wl', 2, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u2', '3', '软件工程师', NULL, 'a', 
    'rj', 1, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u4', '1', '启用', NULL, NULL, 
    'qy', 1, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u3', 'C4CA4238A0B923820DCC509A6F75849B', '机密', NULL, 'a', 
    'a', 1, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u3', 'C81E728D9D4C2F636F067F89CC14862C', '秘密', NULL, NULL, 
    'b', 2, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u3', 'ECCBC87E4B5CE2FE28308FD9F2A7BAF3', '内部', NULL, NULL, 
    'c', 3, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u4', '0', '禁用', NULL, NULL, 
    'jy', 2, 1);
Insert into CWM_ENUM
   (RESTRICTION_ID, VALUE, DISPLAY_VALUE, IMAGE_URL, DESCRIPTION, 
    ID, ORDER_SIGN, IS_OPEN)
 Values
   ('u3', 'A87FF679A2F3E71D9181A67B7542122C', '非密', NULL, NULL, 
    'd', 4, 1);
COMMIT;



--*******************************************--
--              账户策略相关                 --
--*******************************************--
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (1, '密码最小长度', '密码的最小长度，值不能小于6', NULL, NULL, 
    '1', '0', '6');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (2, '密码复杂性要求', '（1）至少有六位字符长；（2）至少包含三种字符的组合：大写字母、小写字母、数字和符号；（3）不要包含用户的用户名；', NULL, NULL, 
    '0', '0', NULL);
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (3, '密码最长使用期限（天）', '超过密码最长使用期限必须修改密码', NULL, NULL, 
    '0', '0', '2');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (4, '密码最短使用期限（天）', '在最短使用期限内不能修改密码，如果“密码最长使用期限”启用，则“密码最短使用期限”必须小于“密码最长使用期限”', NULL, NULL, 
    '0', '0', '1');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (5, '强制密码历史', '系统保存的历史密码个数，如果要启用本策略，需同时启用“密码最短使用期限”', NULL, NULL, 
    '0', '0', '1');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (6, '帐户锁定阈值', '设置几次无效登陆后锁定账户', NULL, NULL, 
    '0', '1', '2');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (7, '帐户锁定时间（分钟）', '帐户锁定的时间长度，如果定义了帐户锁定阈值，则帐户锁定时间必须大于或等于重置时间', NULL, NULL, 
    '0', '1', '30');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (8, '复位帐户锁定计数器（分钟）', '在此后复位帐户锁定计数器，如果定义了帐户锁定阈值，此重置时间必须小于或等于帐户锁定时间', NULL, NULL, 
    '0', '1', '20');
Insert into CWM_SYS_ACCOUNT_STRATEGY
   (ID, STRATEGY_NAME, STRATEGY_NOTE, STRATEGY_VALUE1, STRATEGY_VALUE2, 
    IS_USE, TYPE, STRATEGY_VALUE)
 Values
   (9, '基于时间的安全认证', '只能在规定的时间进行登录', '9:20:09', '17:20:10', 
    '0', '1', '从9:20:09 至17:20:10');
COMMIT;


--*******************************************--
--              部门表的虚拟根节点          --
--*******************************************--
Insert into CWM_SYS_DEPARTMENT
   (ID, PID, NAME, FUNCTION, NOTES, 
    ADD_FLG, DEL_FLG, EDIT_FLG)
 Values
   ('0', '-1', '部门', '所有部门的上级', '此结点为根结点，所有部门都挂在此结点上！', 
    '1', '0', '0');
COMMIT;


--*******************************************--
--              功能点相关		             --
--*******************************************--

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (1, '0', '功能点维护', -1, '/frame/mainsys.jsp', null, '0', '0', '0', 1, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (2, '0', '根功能点 ', 1, '/frame/mainsys.jsp', '根节点', '1', '0', '0', 1, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (3, 'rcp0', 'RCP', 2, '/', 'RCP', '0', '0', '0', 1, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (4, 'rcp1', 'Model Creator', 3, '/', 'Model Creator', '0', '0', '0', 1, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (5, 'rcp2', 'Navigante Tool', 3, '/', 'Navigante Tool', '0', '0', '0', 2, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (6, 'rcp3', 'ETL STUDIO', 3, '/', 'ETL STUDIO', '0', '0', '0', 3, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (7, 'rcp4', 'WORK FLOW', 3, '/', 'WORK FLOW', '0', '0', '0', 4, '111111111', '111111111', 1, '0');

insert into CWM_SYS_FUNCTION (FUNCTIONID, CODE, NAME, PARENTID, URL, NOTES, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
values (8, 'C020101', 'WEB展现', 2, '/frame/mainsys.jsp', 'WEB展现', '1', '0', '1', 2, '111111111', '111111111', 1, '0');


--*******************************************--
--            日志操作类型相关		        --
--*******************************************--

Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (1, '数据查看');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (2, '数据修改');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (3, '数据录入');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (4, '数据删除');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (0, '禁止访问');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (5, '文件上传');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (6, '文件下载');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (7, '文件删除');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (8, '数据备份');
Insert into CWM_SYS_OPERATION
   (ID, NAME)
 Values
   (9, '数据恢复');
COMMIT;

--*******************************************--
--            系统参数相关相关		        --
--*******************************************--
Insert into CWM_SYS_PARAMETER
   (ID, NAME, DATATYPE, VALUE, DESCRIPTION)
 Values
   (1, 'TITLE', 'String', '上海奥蓝托软件技术有限公司', '系统标题');
Insert into CWM_SYS_PARAMETER
   (ID, NAME, DATATYPE, VALUE, DESCRIPTION)
 Values
   (3, 'PageNumber', 'String', '1000', '每页显示最大记录数');
Insert into CWM_SYS_PARAMETER
   (ID, NAME, DATATYPE, VALUE, DESCRIPTION)
 Values
   (4, 'Version', 'String', '6.0.0', '软件的版本');
Insert into CWM_SYS_PARAMETER
   (ID, NAME, DATATYPE, VALUE, DESCRIPTION)
 Values
   (5, 'DATE_FORMAT_PATTERN', 'String', 'YYYY-MM-DD', '日期格式');
Insert into CWM_SYS_PARAMETER
   (ID, NAME, DATATYPE, VALUE, DESCRIPTION)
 Values
   (6, 'SESSION_TIMEOUT', 'String', '30', '用户会话超时时间(分钟)');
COMMIT;

--*******************************************--
--            三元角色相关			        --
--*******************************************--
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (0, '超级管理员', '超级管理员', '0', 'Y', 
    '1');
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (-1, '系统管理员', '责创建用户', '0', 'Y', 
    '1');
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (-2, '安全保密管理员', '安全保密管理员负责分配权限、审查一般用户的操作日志', '0', 'Y', 
    '1');
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (-3, '安全审计员', '负责审查系统管理员和安全保密管理员的操作日志', '0', 'Y', 
    '1');
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (-4, '账户', '负责用户账户管理（用户、角色）', 'A', 'Y', 
    '1');
Insert into CWM_SYS_ROLE
   (ID, NAME, MEMO, TYPE, STATUS, 
    FLG)
 Values
   (-5, '系统', '负责系统配置和维护，负责为模型管理员、权限管理员设置', 'B', 'Y', 
    '1');
COMMIT;

Insert into CWM_SYS_USER
   (ID, USER_NAME, ALL_NAME, PASSWORD, SEX, 
    PHONE, POST, SPECIALTY, GRADE, CREATE_TIME, 
    CREATE_USER, UPDATE_TIME, UPDATE_USER, NOTES, STATE, 
    BIRTHDAY, MOBILE, FLG, DEP_ID, IS_DEL, 
    E_MAIL, PASSWORD_SET_TIME, LOCK_STATE, LOCK_TIME, LOGIN_FAILURES, 
    LAST_FAILURE_TIME)
 Values
   ('-1', 'system', '系统管理员', '54B53072540EEEB8F8E9343E71F28176', NULL, 
    NULL, NULL, NULL, NULL, TO_DATE('12/16/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    'system', NULL, NULL, NULL, '1', 
    NULL, NULL, '1', NULL, '1', 
    NULL, NULL, '0', NULL, '0', 
    NULL);
Insert into CWM_SYS_USER
   (ID, USER_NAME, ALL_NAME, PASSWORD, SEX, 
    PHONE, POST, SPECIALTY, GRADE, CREATE_TIME, 
    CREATE_USER, UPDATE_TIME, UPDATE_USER, NOTES, STATE, 
    BIRTHDAY, MOBILE, FLG, DEP_ID, IS_DEL, 
    E_MAIL, PASSWORD_SET_TIME, LOCK_STATE, LOCK_TIME, LOGIN_FAILURES, 
    LAST_FAILURE_TIME)
 Values
   ('-2', 'right', '安全保密管理员', '7C4F29407893C334A6CB7A87BF045C0D', NULL, 
    NULL, NULL, NULL, NULL, TO_DATE('12/16/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    'system', NULL, NULL, NULL, '1', 
    NULL, NULL, '1', NULL, '1', 
    NULL, NULL, '0', NULL, '1', 
    TO_DATE('12/08/2010 14:26:21', 'MM/DD/YYYY HH24:MI:SS'));
Insert into CWM_SYS_USER
   (ID, USER_NAME, ALL_NAME, PASSWORD, SEX, 
    PHONE, POST, SPECIALTY, GRADE, CREATE_TIME, 
    CREATE_USER, UPDATE_TIME, UPDATE_USER, NOTES, STATE, 
    BIRTHDAY, MOBILE, FLG, DEP_ID, IS_DEL, 
    E_MAIL, PASSWORD_SET_TIME, LOCK_STATE, LOCK_TIME, LOGIN_FAILURES, 
    LAST_FAILURE_TIME)
 Values
   ('-3', 'check', '安全审计员', '0BA4439EE9A46D9D9F14C60F88F45F87', NULL, 
    NULL, NULL, NULL, NULL, TO_DATE('12/16/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    'system', NULL, NULL, NULL, '1', 
    NULL, NULL, '1', NULL, '1', 
    NULL, NULL, '0', NULL, '0', 
    NULL);
COMMIT;

--用户角色表
Insert into CWM_SYS_ROLE_USER
   (ROLE_ID, USER_ID)
 Values
   ('-1', '-1');
Insert into CWM_SYS_ROLE_USER
   (ROLE_ID, USER_ID)
 Values
   ('-2', '-2');
Insert into CWM_SYS_ROLE_USER
   (ROLE_ID, USER_ID)
 Values
   ('-3', '-3');
COMMIT;


--为没有TBOM的功能点创建空的TBOM树
Insert into CWM_TBOM_DIR
(ID )
 Values
   (' ');
   COMMIT;
   

--角色功能点表


Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('1', 'ID', 'ID', '0', '0', 
    '1', '1', NULL, 'number', 'SEQ_CWM_SYS_USER', 
    '1', NULL, NULL, '0', '1', 
    NULL, NULL, '0', '0', 1, 
    '1', '1', NULL, NULL, NULL, 
    NULL, '0', '0', '0');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('2', '用户ID', 'USER_NAME', '1', '0', 
    '1', '0', NULL, 'string', NULL, 
    NULL, 50, 1, '0', '1,5', 
    NULL, NULL, '1', '1', 2, 
    '1', '1', NULL, NULL, NULL, 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('3', '真实姓名', 'ALL_NAME', '1', '0', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 100, 1, '0', '5', 
    NULL, NULL, '1', '1', 3, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('4', '密码', 'PASSWORD', '0', '0', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 16, 6, '0', '4,5', 
    NULL, NULL, '0', '1', 20, 
    '8', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '0');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('5', '性别', 'SEX', '1', '1', 
    '0', '0', 'u1', 'number', NULL, 
    NULL, 2, 1, '0', NULL, 
    '0', NULL, '1', '1', 5, 
    '3', '0', NULL, NULL, NULL, 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('6', '固化标志', 'FLG', '0', '1', 
    '0', '0', NULL, 'number', NULL, 
    NULL, 1, 1, '0', NULL, 
    NULL, NULL, '1', '0', 6, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('7', '	启用标志', 'STATE', '1', '0', 
    '0', '0', 'u4', 'number', NULL, 
    NULL, 1, 1, '0', '5', 
    NULL, NULL, '1', '1', 7, 
    '3', '0', NULL, NULL, NULL, 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('8', '	职务', 'POST', '0', '1', 
    '0', '0', 'u2', 'number', NULL, 
    NULL, 50, 2, '0', NULL, 
    NULL, NULL, '1', '1', 8, 
    '3', '0', NULL, NULL, NULL, 
    NULL, '0', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('9', '办公电话', 'PHONE', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 13, 2, '0', '2', 
    NULL, NULL, '1', '1', 9, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('10', '专业', 'SPECIALTY', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 50, 2, '0', NULL, 
    NULL, NULL, '1', '1', 10, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('12', '创建时间', 'CREATE_TIME', '0', '0', 
    '0', '0', NULL, 'date', NULL, 
    NULL, 10, 2, '0', NULL, 
    NULL, NULL, '1', '0', 11, 
    '4', '1', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('13', '创建操作人员', 'CREATE_USER', '0', '0', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 50, 2, '0', NULL, 
    NULL, NULL, '0', '0', 12, 
    '1', '1', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('14', '	修改时间', 'UPDATE_TIME', '0', '1', 
    '0', '0', NULL, 'date', NULL, 
    NULL, 10, 2, '0', NULL, 
    NULL, NULL, '0', '0', 13, 
    '4', '1', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('11', '	密级', 'GRADE', '1', '1', 
    '0', '0', 'u3', 'number', NULL, 
    NULL, 5, 2, '0', '5', 
    NULL, NULL, '1', '1', 14, 
    '3', '0', NULL, NULL, NULL, 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('15', '	修改操作人员', 'UPDATE_USER', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 50, 2, '0', NULL, 
    NULL, NULL, '0', '0', 15, 
    '1', '1', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('16', '备注', 'NOTES', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 1000, 2, '1', '3', 
    NULL, NULL, '0', '1', 16, 
    '2', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('17', '出生年月日', 'BIRTHDAY', '1', '1', 
    '0', '0', NULL, 'date', NULL, 
    NULL, 10, NULL, '0', NULL, 
    NULL, NULL, '1', '1', 17, 
    '4', '0', NULL, NULL, NULL, 
    NULL, '1', '0', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('18', '手机电话', 'MOBILE', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 13, NULL, '0', '2', 
    NULL, NULL, '1', '1', 18, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '0');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('19', '部门', 'DEP_ID', '1', '1', 
    '0', '0', NULL, 'number', NULL, 
    NULL, 100, 2, '0', NULL, 
    NULL, NULL, '1', '1', 4, 
    '7', '0', 'CWM_SYS_DEPARTMENT', 'ID', 'NAME', 
    NULL, '1', '1', '1');
Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('20', 'E-MAIL', 'E_MAIL', '0', '1', 
    '0', '0', NULL, 'string', NULL, 
    NULL, 50, 1, '0', NULL, 
    NULL, NULL, '1', '1', 19, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '1', '1', '1');

Insert into CWM_SYS_USER_COLUMNS
   (ID, DISPLAY_NAME, S_COLUMN_NAME, IS_FOR_SEARCH, IS_NULLABLE, 
    IS_ONLY, IS_PK, ENMU_ID, COL_TYPE, SEQUENCE_NAME, 
    IS_AUTOINCREMENT, MAX_LENGTH, MIN_LENGTH, IS_WRAP, CHECK_TYPE, 
    IS_MULTI_SELECTED, DEFAULT_VALUE, DISPLAY_SHOW, EDIT_SHOW, SHOT, 
    INPUT_TYPE, IS_READONLY, REF_TABLE, REF_TABLE_COLUMN_ID, REF_TABLE_COLUMN_SHOWNAME, 
    POP_WINDOW_FUNCTION, IS_FOR_INFOSEARCH, IS_DISPALYINFO_SHOW, IS_VIEWINFO_SHOW)
 Values
   ('21', '锁定状态', 'LOCK_STATE', '0', '1', 
    '0', '0', NULL, 'String', NULL, 
    NULL, 1, NULL, '0', NULL, 
    NULL, NULL, '0', '0', 21, 
    '1', '0', NULL, NULL, NULL, 
    NULL, '0', '0', '0');

--JBPM 初始化
Insert into JBPM4_PROPERTY
   (KEY_, VERSION_, VALUE_)
 Values
   ('next.dbid', 0, '1');
   
   COMMIT;

--协同初始化
--2016-07-25
insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (2, '工作组', null, 'PROJECT', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (3, '甘特图', null, 'PROJECT', 2);

--plan
insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (4, '工作组', null, 'PLAN', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (6, '控制流', null, 'PLAN', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (8, '设计数据', null, 'PLAN', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (20, '工作包分解', null, 'PLAN', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (11, '数据流', null, 'PLAN', 1);

--task
insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (5, '工作组', null, 'TASK', 1);


insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (7, '控制流', null, 'TASK', 1);


insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (9, '设计数据', null, 'TASK', 1);


insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (10, '离线数据', null, 'TASK', 2);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (12, '数据流', null, 'TASK', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (13, '任务处理', null, 'TASK', 1);

insert into COLLAB_FUNCTION
  (ID, NAME, PAR_FUNCTION_ID, BELONGED_MODEL, DISPLAY_ORDER)
values
  (14, '工作包分解', null, 'PLAN', 1);

--2016-10-28
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-workGroup' where t.NAME = '工作组';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-gantt' where t.NAME = '甘特图';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-flow' where t.NAME = '控制流';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-jobwbs' where t.NAME = '工作包分解';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-designdata' where t.NAME = '设计数据';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-dataflow' where t.NAME = '数据流';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-offlinedata' where t.NAME = '离线数据';
update COLLAB_FUNCTION t SET t.ICON_CLS = 'icon-taskprocess' where t.NAME = '任务处理';

--new function
