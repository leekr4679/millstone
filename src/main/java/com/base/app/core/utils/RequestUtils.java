package com.base.app.core.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class RequestUtils {
    private HttpServletRequest request;

    private RequestUtils(HttpServletRequest request) {
        this.request = request;
    }

    public static RequestUtils of(HttpServletRequest request) {
        RequestUtils requestHelper = new RequestUtils(request);
        return requestHelper;
    }

    public static RequestUtils of(ServletRequest request) {
        return of((HttpServletRequest)request);
    }

    public String getString(String key) {
        return this.request.getParameter(key);
    }

    public String[] getStringArray(String key) {
        String[] targets = this.getString(key).split(",");
        return targets;
    }

    public String getString(String key, String defaultValue) {
        String value = this.getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    public int getInt(String key) {
        try {
            return Integer.parseInt(this.getString(key));
        } catch (Exception var3) {
            return 0;
        }
    }

    public int getInt(String key, int defaultValue) {
        String value = this.getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : this.getInt(key);
    }

    public long getLong(String key) {
        try {
            return Long.parseLong(this.getString(key));
        } catch (Exception var3) {
            return 0L;
        }
    }

    public long getLong(String key, long defaultValue) {
        String value = this.getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : this.getLong(key);
    }

    public void setAttribute(String key, Object value) {
        this.request.setAttribute(key, value);
    }

    public String getStringAttribute(String key) {
        return this.getStringAttribute(key, "");
    }

    public Object getAttribute(String key) {
        return this.request.getAttribute(key);
    }

    public String getStringAttribute(String key, String defaultValue) {
        Object value = this.request.getAttribute(key);
        return value == null ? defaultValue : value.toString();
    }

    public <T> T getAttributeObject(String key, Class<T> clazz) {
        Object object = this.getAttribute(key);
        return object != null ? clazz.cast(object) : null;
    }

    public void setSessionAttribute(String key, Object value) {
        this.request.getSession().setAttribute(key, value);
    }

    public Object getSessionAttributeObject(String key) {
        return this.request.getSession().getAttribute(key);
    }

    public <T> T getSessionAttributeObject(String key, Class<T> clazz) {
        Object object = this.getSessionAttributeObject(key);
        return object != null ? clazz.cast(object) : null;
    }

    public String getSessionAttributeString(String key) {
        return this.getSessionAttributeString(key, "");
    }

    public String getSessionAttributeString(String key, String defaultValue) {
        Object value = this.getSessionAttributeObject(key);
        return value == null ? defaultValue : value.toString();
    }

    public boolean hasSessionAttribute(String key) {
        return this.getSessionAttributeObject(key) != null;
    }

    public boolean hasParameter(String key) {
        return this.request.getParameterMap() != null ? this.request.getParameterMap().containsKey(key) : false;
    }

    public Map<String, String> getRequestHeaderMap() {
        Map<String, String> convertedHeaderMap = new HashMap();
        Enumeration headerMap = this.request.getHeaderNames();

        while(headerMap.hasMoreElements()) {
            String name = (String)headerMap.nextElement();
            String value = this.request.getHeader(name);
            convertedHeaderMap.put(name, value);
        }

        return convertedHeaderMap;
    }

    public Map<String, String> getRequestBodyParameterMap() {
        Map<String, String> convertedParameterMap = new HashMap();
        Map<String, String[]> parameterMap = this.request.getParameterMap();
        if (parameterMap.size() > 0) {
            Iterator var3 = parameterMap.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                String[] values = (String[])parameterMap.get(key);
                StringJoiner valueString = new StringJoiner(",");
                String[] var7 = values;
                int var8 = values.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String value = var7[var9];
                    valueString.add(value);
                }

                convertedParameterMap.put(key, valueString.toString());
            }
        }

        return convertedParameterMap;
    }

    public String getRequestBody() {
        try {
            return IOUtils.toString(this.request.getInputStream(), "UTF-8");
        } catch (IOException var2) {
            return "";
        }
    }

    public String getRequestBodyJson(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");
        if (contentType != null && contentType.contains("application/json")) {
            String body = this.getRequestBody();
            if (StringUtils.isNotEmpty(body)) {
                return body.startsWith("{") && body.endsWith("}") ? JsonUtils.toPrettyJson(JsonUtils.fromJson(this.getRequestBody())) : body;
            } else {
                return "";
            }
        } else {
            Map<String, String> requestBodyParameterMap = this.getRequestBodyParameterMap();
            return requestBodyParameterMap.size() > 0 ? JsonUtils.toPrettyJson(requestBodyParameterMap) : "";
        }
    }

    public String getRequestUri() {
        return this.request.getRequestURI();
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static Locale getLocale(HttpServletRequest request) {
        RequestUtils requestUtils = of(request);
        String language = requestUtils.getString("language");
        Locale locale;
        if (StringUtils.isNotEmpty(language)) {
            locale = new Locale(language);
        } else {
            String localeCookie = CookieUtils.getCookieValue(request, "language");
            if (StringUtils.isNotEmpty(localeCookie)) {
                locale = new Locale(localeCookie);
            } else {
                String acceptLanguage = request.getHeader("Accept-Language");
                String acceptCharset = request.getHeader("Accept-Charset");
                locale = new Locale("ko");
            }
        }

        return locale;
    }
}
