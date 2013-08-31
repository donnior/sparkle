package me.donnior.sparkle.core.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.core.resolver.ActionMethodDefinition;

public interface ViewRender {

    boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult);
    
    void renderView(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
