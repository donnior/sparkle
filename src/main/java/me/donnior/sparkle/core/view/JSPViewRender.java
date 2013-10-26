package me.donnior.sparkle.core.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.FHashMap;
import me.donnior.fava.MConsumer;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodDefinition;

public class JSPViewRender implements ViewRender {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    private String viewPathPrefix = "/WEB-INF/";
    private String viewPathSuffix = ".jsp";

    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws ServletException, IOException {
        final HttpServletRequest request = webRequest.getServletRequest();
        final HttpServletResponse response = webRequest.getServletResponse();
        String viewPath = this.viewPathPrefix + result + viewPathSuffix;

        setRequestAttributeFromControllersExposeValue(controller, request);
        
        /*
         * check jsp page exist or not, can be useful for dev environment, //TODO ENV feature is to be added in the future. 
         */
//        URL url = request.getServletContext().getResource(viewPath);
//        if (url != null) { 
//            System.out.println("resource ok");
//        } else {
//            System.out.println("resource error");
//        }
        
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(viewPath);
        if (rd == null) {
            System.out.println("rd is null");
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
