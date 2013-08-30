package me.donnior.sparkle.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.internal.ActionMethodDefinition;

public class RedirectViewRender implements ViewRender {
    
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public void renderView(Object result, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = ((String)result).substring(REDIRECT_PREFIX.length()).trim();
        if(!path.startsWith("http")){
            path = contextPathAppendedPath(path, request);
        }
        String encodedRedirectURL = response.encodeRedirectURL(path);
        
        response.setStatus(301);
        response.setHeader("Location", encodedRedirectURL);
    }

    private String contextPathAppendedPath(String path, HttpServletRequest request) {
        String contextPath = request.getServletContext().getContextPath();
        return path.startsWith("/") ? contextPath+path : contextPath + "/" + path;
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return actionMethodResult instanceof String && ((String)actionMethodResult).startsWith(REDIRECT_PREFIX);
    }
    
   
}
