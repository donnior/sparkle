package me.donnior.sparkle.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPViewResolver implements ViewResolver {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    private String viewPathPrefix = "/WEB-INF/";
    private String viewPathSuffix = ".jsp";

    @Override
    public void resovleView(String result, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String viewPath = this.viewPathPrefix + result + viewPathSuffix;
        // System.out.println("got view path : " +viewPath);

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
            // if (logger.isDebugEnabled()) {
            // logger.debug("Included resource [" + viewPath +
            // "] in InternalResourceView '" + getBeanName() + "'");
            // }
        }

        else {
            // exposeForwardRequestAttributes(request);
            rd.forward(request, response);
            // if (logger.isDebugEnabled()) {
            // logger.debug("Forwarded to resource [" + getUrl() +
            // "] in InternalResourceView '" + getBeanName() + "'");
            // }
        }

//        try {
//            request.getRequestDispatcher(viewPath).include(request, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private boolean useInclude(HttpServletRequest request,
            HttpServletResponse response) {
        return (false || isIncludeRequest(request) || response.isCommitted());
    }

    private boolean isIncludeRequest(HttpServletRequest request) {
        return (request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE) != null);
    }
    
    

}
