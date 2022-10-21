package com.penta.ps.license.license.session;

import com.penta.ps.license.license.entity.UserInfo;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionUser == null) {
            return false;
        }

        return true;
    }

    public static UserInfo getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }

        return (UserInfo)session.getAttribute(USER_SESSION_KEY);
    }
}
