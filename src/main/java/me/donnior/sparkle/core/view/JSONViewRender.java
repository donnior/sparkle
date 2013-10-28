package me.donnior.sparkle.core.view;

import java.io.IOException;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;
import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.srape.FieldExposerModule;
import me.donnior.srape.JSONBuilder;

import com.google.gson.Gson;

public class JSONViewRender implements ViewRender {
    
    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        
        response.setContentType("application/json; charset=UTF-8");
        
        if(result instanceof FieldExposerModule){
            response.write(new JSONBuilder((FieldExposerModule)result).build());
        } else {
            response.write(new Gson().toJson(result));
        }
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return adf.hasAnnotation(Json.class) || actionMethodResult instanceof FieldExposerModule;
    }

}
