package com.orient.sysman.bussiness;

import com.orient.sysmodel.domain.sys.CwmSysAccountStrategyEntity;
import com.orient.sysmodel.domain.user.PasswordHistory;
import com.orient.sysmodel.domain.user.PasswordHistoryDAO;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.sys.IAccountStrategyService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账户策略管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class AccountStrategyBusiness extends BaseHibernateBusiness<CwmSysAccountStrategyEntity> {

    @Autowired
    IAccountStrategyService accountStrategyService;

    @Autowired
    PasswordHistoryDAO PasswordHistoryDAO;

    @Autowired
    UserService UserService;

    @Override
    public IAccountStrategyService getBaseService() {
        return accountStrategyService;
    }

    public void saveStatus(Long[] strategyIds, String status) {
        for (Long strategyId : strategyIds) {
            CwmSysAccountStrategyEntity accountStrategyEntity = accountStrategyService.getById(strategyId);
            accountStrategyEntity.setIsUse(status);
            accountStrategyService.update(accountStrategyEntity);
        }
    }

    public void saveValue(Long strategyId, String value1, String value2) {
        CwmSysAccountStrategyEntity accountStrategyEntity = accountStrategyService.getById(strategyId);
        accountStrategyEntity.setStrategyValue1(value1);
        accountStrategyEntity.setStrategyValue2(value2);
        if (strategyId.equals(9l)) {
            accountStrategyEntity.setStrategyValue("从" + value1 + "至" + value2);
        } else {
            accountStrategyEntity.setStrategyValue(value1);
        }
        accountStrategyService.update(accountStrategyEntity);
    }

    /**
     *
     * @param password
     * @return 检验密码是否符合“密码最小长度”策略，策略没有启用或满足策略返回true
     */
    public boolean meetMinPasswordLen(String password) {
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "密码最小长度");
        if (accountStrategy != null) {
            if (accountStrategy.getIsUse().equalsIgnoreCase("1")) {
                if (password.length() < Integer.valueOf(accountStrategy.getStrategyValue())) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param userName
     * @param password
     * @return 验密码是否符合“密码复杂性要求”策略，策略没有启用或满足策略返回true
     */
    public boolean meetPasswordComplexity(String userName, String password) {
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "密码复杂性要求");

        if (accountStrategy != null) {
            if (accountStrategy.getIsUse().equalsIgnoreCase("1")) {
                //至少有六位字符长
                if (password.length() < 6) {
                    flag = false;
                }
                //至少包含三种字符的组合：大写字母、小写字母、数字和符号
                else if (this.checkPasswordComplexity(password) == false) {
                    flag = false;
                }
                //不要包含用户的用户名
                else if (password.indexOf(userName) >= 0) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean checkPasswordComplexity(String str) {
        String[] regArray = new String[4];
        regArray[0] = "\\d";
        regArray[1] = "[a-z]";
        regArray[2] = "[A-Z]";
        regArray[3] = "\\W|\\_";

        int count = 0;

        for (int i = 0; i < 4; i++) {
            //if(regArray[i].test(str)){
            Pattern p = Pattern.compile(regArray[i]);

            Matcher m = p.matcher(str);
            if (m.find()) {
                count = count + 1;
            }
        }

        if (count >= 3) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param user
     * @return 检验密码是否符合“密码最长使用期限（天）”策略，策略没有启用或满足策略返回true
     */
    public boolean meetMaxPasswordAge(User user) {
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "密码最长使用期限（天）");

        if (accountStrategy != null) {
            if (accountStrategy.getIsUse().equalsIgnoreCase("1")) {
                Date now = new Date();
                //如果当前时间和密码设定时间之间超过设定的使用期限，则返回false
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
                try {
                    java.util.Date begin = df.parse(user.getPasswordSetTime().toString());
                    long between = (System.currentTimeMillis() - begin.getTime()) / 1000;//除以1000是为了转换成秒

                    if (between >= Integer.valueOf(accountStrategy.getStrategyValue().toString()) * 24 * 3600) {
                        flag = false;
                    }
                } catch (ParseException pe) {
                    pe.printStackTrace();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        return flag;
    }

    /**
     *
     * @param user
     * @return 检验密码是否符合“密码最短使用期限（天）”策略，策略没有启用或满足策略返回true
     */
    public boolean meetMinPasswordAge(User user) {
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "密码最短使用期限（天）");

        if (accountStrategy != null) {
            if (accountStrategy.getIsUse().equalsIgnoreCase("1")) {
                Date now = new Date();
                //如果当前时间和密码设定时间之间没有超过设定的使用期限，则返回false
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
                //SimpleDateFormat dfs = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                try {
                    java.util.Date begin = df.parse(user.getPasswordSetTime().toString());
                    long between = (System.currentTimeMillis() - begin.getTime()) / 1000;//除以1000是为了转换成秒

                    if (between <= Integer.valueOf(accountStrategy.getStrategyValue().toString()) * 24 * 3600) {
                        flag = false;
                    }
                } catch (ParseException pe) {
                    System.out.println(pe.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        return flag;
    }

    /**
     *
     * @param user
     * @param newPassword
     * @return 检验密码是否符合“强制密码历史”策略，策略没有启用或满足策略返回true ('强制密码历史'需在'密码最短使用期限（天）'也启用的时候才起作用)
     */
    public boolean meetEnforcePasswordHistory(User user, String newPassword) {
        boolean flag = true;
        CwmSysAccountStrategyEntity minPasswordAgeStrategy = accountStrategyService.getByProperties("strategyName", "密码最短使用期限（天）");

        if (minPasswordAgeStrategy != null) {
            if (minPasswordAgeStrategy.getIsUse().equalsIgnoreCase("1")) {
                CwmSysAccountStrategyEntity passwordStrategy = accountStrategyService.getByProperties("strategyName", "强制密码历史");
                if (passwordStrategy != null) {
                    if (passwordStrategy.getIsUse().equalsIgnoreCase("1")) {
                        List<PasswordHistory> historylist = PasswordHistoryDAO.findByProperty("user.id", user.getId());
                        if (historylist != null) {
                            int historySize = historylist.size();
                            if (historylist.size() > Integer.valueOf(passwordStrategy.getStrategyValue())) {
                                historySize = Integer.valueOf(passwordStrategy.getStrategyValue());
                            }
                            //在“强制历史记录”中查找，新密码是否和历史表中的相同，相同，则返回false
                            for (int j = 0; j < historySize; j++) {
                                PasswordHistory passwordHistory = historylist.get(j);
                                if (passwordHistory.getPassword().equalsIgnoreCase(newPassword)) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }


        }
        return flag;
    }

    /**
     *
     * @param user
     * @return 判断帐户是否锁定，“帐户锁定阈值”没有启用或帐户没有锁定则返回true
     */
    public boolean meetAccountLock(User user) {
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "帐户锁定阈值");
        if (accountStrategy != null) {
            if (accountStrategy.getIsUse().equalsIgnoreCase("1")) {
                //如果“最近一次登录失败的时间”为空值，则将登录失败计算器归0
                if (user.getLastFailureTime() == null || user.getLastFailureTime().toString() == "") {
                    if (!user.getLoginFailures().equalsIgnoreCase("0")) {
                        // 更新登录失败次数
                        User updateUser = UserService.findById(user.getId());
                        updateUser.setLoginFailures("0");
                        UserService.updateUser(user);
                    }
                } else {
                    //如果“当前时间”减去“最近一次登录失败的时间”已超过“复位锁定计数器的时间”，则将登录失败计算器归0
                    CwmSysAccountStrategyEntity resetTimeStrategy = accountStrategyService.getByProperties("strategyName", "复位帐户锁定计数器（分钟）");
                    if (resetTimeStrategy != null) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
                        try {
                            java.util.Date begin = df.parse(user.getLastFailureTime().toString());
                            long between = (System.currentTimeMillis() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                            long minute1 = between / 60;

                            //如果“当前时间”减去“最近一次登录失败的时间”已超过“复位锁定计数器的时间”，则将登录失败计算器归0
                            if (minute1 >= Integer.valueOf(resetTimeStrategy.getStrategyValue())) {
                                // 更新登录失败次数
                                User updateUser = UserService.findById(user.getId());
                                updateUser.setLoginFailures("0");
                                updateUser.setLastFailureTime(null);
                                UserService.updateUser(updateUser);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //如果“锁定时间”为空值，则将用户帐号解锁
                if (user.getLockTime() == null || user.getLockTime().toString() == "") {
                    if (!user.getLockState().equalsIgnoreCase("0")) {
                        // 更新帐号锁定状态
                        User updateUser = UserService.findById(user.getId());
                        updateUser.setLockState("0");
                        UserService.updateUser(updateUser);
                    }
                } else {
                    //如果“当前时间”减去“锁定时间”已超过“帐户锁定时间”，则将用户帐号解锁
                    CwmSysAccountStrategyEntity lockTimeStrategy = accountStrategyService.getByProperties("strategyName", "帐户锁定时间（分钟）");
                    if (lockTimeStrategy != null) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
                        try {
                            java.util.Date begin = df.parse(user.getLockTime().toString());
                            long between = (System.currentTimeMillis() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                            long minute1 = between / 60;
                            //如果“当前时间”减去“锁定时间”已超过“帐户锁定时间”，则将用户帐号解锁
                            if (between >= Integer.valueOf(lockTimeStrategy.getStrategyValue().toString()) * 60) {
                                // 更新帐号锁定状态
                                User updateUser = UserService.findById(user.getId());
                                updateUser.setLockState("0");
                                updateUser.setLockTime(null);
                                UserService.updateUser(updateUser);
                            } else {
                                flag = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     *
     * @return 检验是否符合“基于时间的安全认证”策略，策略没有启用或满足策略返回true
     */
    public boolean meetTimebasedCertification(){
        boolean flag = true;
        CwmSysAccountStrategyEntity accountStrategy = accountStrategyService.getByProperties("strategyName", "基于时间的安全认证");
        if(accountStrategy != null){
            if(accountStrategy.getIsUse().equalsIgnoreCase("1")){
                //如果当前时间在“基于时间的安全认证”设定的时间范围外，则返回false
                Date now = new Date();
                SimpleDateFormat dfs = new SimpleDateFormat("hh:mm:ss");
                String systemTime = dfs.format(now).toString();
                String begin = accountStrategy.getStrategyValue1().toString();
                String end = accountStrategy.getStrategyValue2().toString();
                String[] beginArray = begin.split(":");
                String[] endArray = end.split(":");
                //比较小时
                if(now.getHours()<Integer.valueOf(beginArray[0]) || now.getHours()>Integer.valueOf(endArray[0])){
                    flag = false;
                }
                else if(now.getHours()==Integer.valueOf(beginArray[0])){
                    if(now.getMinutes()<Integer.valueOf(beginArray[1]) ){
                        flag = false;
                    }
                    else if(now.getMinutes()==Integer.valueOf(beginArray[1])){
                        if(now.getSeconds()<Integer.valueOf(beginArray[2])){
                            flag = false;
                        }
                    }
                }
                else if(now.getHours()==Integer.valueOf(endArray[0])){
                    if(now.getMinutes()>Integer.valueOf(endArray[1]) ){
                        flag = false;
                    }
                    else if(now.getMinutes()==Integer.valueOf(endArray[1])){
                        if(now.getSeconds()>Integer.valueOf(endArray[2])){
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
