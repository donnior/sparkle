package me.donnior.sparkle.core.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.core.ActionMethodDefinition;

public class JSPViewRender implements ViewRender {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    private String viewPathPrefix = "/WEB-INF/";
    private String viewPathSuffix = ".jsp";

    @Override
    public void renderView(Object result, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String viewPath = this.viewPathPrefix + result + viewPathSuffix;

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

    private boolean useInclude(HttpServletRequest request, HttpServletResponse response) {
        return isIncludeRequest(request) || response.isCommitted();
    }

    private boolean isIncludeRequest(HttpServletRequest request) {
        return (request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE) != null);
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return true;
    }

}
