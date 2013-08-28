package me.donnior.sparkle.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.srape.FieldExposerModule;
import me.donnior.srape.JSONBuilder;

import com.google.gson.Gson;

public class JSONViewRender implements ViewRender {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    @Override
    public void renderView(Object result, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if(result instanceof FieldExposerModule){
            response.getWriter().write(new JSONBuilder((FieldExposerModule)result).build());
        } else {
            response.getWriter().write(new Gson().toJson(result));
        }
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return adf.hasAnnotation(Json.class) || actionMethodResult instanceof FieldExposerModule;
    }

}
