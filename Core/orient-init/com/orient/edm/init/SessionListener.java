package com.orient.edm.init;

import com.orient.edm.asyncbean.EDM_UserContainer_Async;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class SessionListener implements HttpSessionListener {

    public static HashSet sessions;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();
        sessions = (HashSet) context.getAttribute("sessions");
        if (sessions == null) {
            sessions = new HashSet();
            context.setAttribute("sessions", sessions);
        }

        // 新建的session添加到sessions中
        sessions.add(session);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        sessions.remove(event.getSession());

    }

    public static Map<String, String> getLoginUser() {
        Map<String, String> map = new HashMap<String, String>();
        Iterator it = sessions.iterator();
        while (it.hasNext()) {
            HttpSession session = (HttpSession) it.next();
            EDM_UserContainer_Async userContainer = (EDM_UserContainer_Async) session.getAttribute("userInfo");
            if (userContainer != null) {
                map.put(userContainer.getUserId(), userContainer.getDisplayName());
            }
        }
        return map;
    }

    public static String getUsername(String userid) {
        Iterator it = sessions.iterator();
        String userName = "";
        while (it.hasNext()) {
            HttpSession session = (HttpSession) it.next();
            EDM_UserContainer_Async userContainer = (EDM_UserContainer_Async) session.getAttribute("userInfo");
            if (userContainer != null) {
                if (userContainer.getUserId().equalsIgnoreCase(userid)) {
                    userName = userContainer.getDisplayName();
                    break;
                }
            }

        }
        return userName;
    }

    public static boolean containtUser(String userid) {
        Iterator it = sessions.iterator();
        boolean hasUser = false;
        while (it.hasNext()) {
            HttpSession session = (HttpSession) it.next();
            EDM_UserContainer_Async userContainer = (EDM_UserContainer_Async) session.getAttribute("userInfo");
            if (userContainer.getUserId().equalsIgnoreCase(userid)) {
                hasUser = true;
                break;
            }
        }
        return hasUser;
    }

}
