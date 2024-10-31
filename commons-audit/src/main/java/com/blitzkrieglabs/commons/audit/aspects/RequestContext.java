package com.blitzkrieglabs.commons.audit.aspects;

import java.util.Objects;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class RequestContext {

    private static final String PROP_USER = "user";
    private static final String PROP_SESSION = "session_id";
    private static final String PROP_ROLEID = "role_id";

    private static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    public static void put(String key, Object value) {
        RequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
        }
    }

    public static Object get(String key) {
        RequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

    public static void remove(String key) {
        RequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.removeAttribute(key, RequestAttributes.SCOPE_REQUEST);
        }
    }

    

    public static String getInitiator() {
        Object user = get(PROP_USER);
        return user == null ? "" : user.toString();
    }

    public static void setInitiator(String initiator) {
        put(PROP_USER, initiator);
    }

    public static String getSession() {
        Object session = get(PROP_SESSION);
        return session == null ? "" : session.toString();
    }

    public static void setSession(String session) {
        put(PROP_SESSION, session);
    }
    
    
    
    public static void setRoleId(int id) {
    	put(PROP_ROLEID,id);
    }
    
    public static int getRoleId() {
    	Object role = get(PROP_ROLEID);
    	if(role!=null) return Integer.parseInt(role.toString());
    	return 0;
    }
    
    
    public static String getPath() {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) getRequestAttributes();
		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
		String path = request.getMethod() + request.getRequestURI();
		return path;
    }
}
