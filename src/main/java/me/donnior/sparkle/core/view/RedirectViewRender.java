package me.donnior.sparkle.core.view;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodDefinition;

public class RedirectViewRender implements ViewRender {
    
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        HttpServletResponse response = webRequest.getOriginalResponse();
        
        String path = ((String)result).substring(REDIRECT_PREFIX.length()).trim();
        if(!path.startsWith("http")){
            path = contextPathAppendedPath(path, webRequest);
        }
        String encodedRedirectURL = response.encodeRedirectURL(path);
        
        response.setStatus(301);
        response.setHeader("Location", encodedRedirectURL);
    }

    private String contextPathAppendedPath(String path, WebRequest request) {
        String contextPath = request.getContextPath();
        return path.startsWith("/") ? contextPath+path : contextPath + "/" + path;
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return actionMethodResult instanceof String && ((String)actionMethodResult).startsWith(REDIRECT_PREFIX);
    }
    
   
}
