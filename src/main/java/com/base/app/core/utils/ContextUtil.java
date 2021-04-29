package com.base.app.core.utils;

import com.base.app.core.context.AppContextManager;

import javax.servlet.ServletContext;

public class ContextUtil {
    private static ServletContext _servletContext;

    public ContextUtil() {
    }

    public static String getBaseApiPath() {
        return getContext() + "/api";
    }

    public static String getContext() {
        try {
            if (_servletContext == null) {
                _servletContext = (ServletContext) AppContextManager.getBean(ServletContext.class);
            }

            return _servletContext.getContextPath().equals("/") ? "" : _servletContext.getContextPath();
        } catch (Exception var1) {
            return "/";
        }
    }

    public static String getPagePath(String path) {
        return getContext() + path;
    }

    public static String getCookieContextPath() {
        return getContext().equals("") ? "/" : getContext();
    }
}
