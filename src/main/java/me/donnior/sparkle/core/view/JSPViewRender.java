package me.donnior.sparkle.core.view;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.Consumer;
import me.donnior.fava.FHashMap;
import me.donnior.fava.FMap;
import me.donnior.fava.MConsumer;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.annotation.Out;
import me.donnior.sparkle.core.ActionMethodDefinition;

public class JSPViewRender implements ViewRender {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    private String viewPathPrefix = "/WEB-INF/";
    private String viewPathSuffix = ".jsp";

    @Override
    public void renderView(Object result, Object controller, final HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String viewPath = this.viewPathPrefix + result + viewPathSuffix;

        setRequestAttributeFromControllersExposeValue(controller, request);
        
        RequestDispatcher rd = request.getRequestDispatcher(viewPath);
        if (rd == null) {
            throw new ServletException("Could not get RequestDispatcher for ["
                    + viewPath
                    + "]: check that this file exists within your WAR");
        }

        // If already included or response already committed, perform include,
        // else forward.
        if (useInclude(request, response)) {
            rd.include(request, response);
        }

        else {
            rd.forward(request, response);
        }

    }

    private void setRequestAttributeFromControllersExposeValue(Object controller, final HttpServletRequest request) {
        Map<String, Object> valueExposeToRequestAttribute = new ViewVariablesExposer().getValueMap(controller);
        
        new FHashMap<String, Object>(valueExposeToRequestAttribute).each(new MConsumer<String, Object>() {
            @Override
            public void apply(String key, Object value) {
                request.setAttribute(key, value);
            }
        });
    }
    
    
    

    private boolean useInclude(HttpServletRequest request, HttpServletResponse response) {
        return isIncludeRequest(request) || response.isCommitted();
    }

    private boolean isIncludeRequest(HttpServletRequest request) {
        return (request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE) != null);
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return void.class.equals(adf.getReturnType()) || String.class.equals(adf.getReturnType());
    }

}
