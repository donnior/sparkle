package me.donnior.sparkle.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPViewResolver implements ViewResolver {

    private String viewPathPrefix = "/WEB-INF/";
    private String viewPathSuffix = ".jsp";
    

    @Override
    public void resovleView(String result, HttpServletRequest request,
            HttpServletResponse response) {
        String viewPath = this.viewPathPrefix + result + viewPathSuffix;   
        try {
            request.getRequestDispatcher(viewPath).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

}
