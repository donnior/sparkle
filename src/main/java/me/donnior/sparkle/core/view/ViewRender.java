package me.donnior.sparkle.core.view;

import java.io.IOException;

import javax.servlet.ServletException;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodDefinition;

public interface ViewRender {

    boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult);
    
    void renderView(Object result, Object controller, WebRequest request) throws IOException;

}
