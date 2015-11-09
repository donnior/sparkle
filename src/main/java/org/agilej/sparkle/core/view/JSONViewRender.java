package org.agilej.sparkle.core.view;

import java.io.IOException;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.annotation.Json;
import org.agilej.sparkle.core.ActionMethod;


import com.google.gson.Gson;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;

public class JSONViewRender implements ViewRender {
    
    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        
        response.setContentType("application/json; charset=UTF-8");
        
        if(result instanceof JSONModel){
            // new JSONBuilder((JSONModel)result).build(response.getWriter());
            // response.getWriter().flush();
            response.write(new JSONBuilder((JSONModel)result).build());
        } else {
            response.write(new Gson().toJson(result));
        }
    }

    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        //TODO how deal with functional route
        return actionMethodResult instanceof JSONModel || actionMethod.hasAnnotation(Json.class);
    }

}