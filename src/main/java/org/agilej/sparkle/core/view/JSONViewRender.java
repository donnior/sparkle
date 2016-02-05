package org.agilej.sparkle.core.view;

import java.io.IOException;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.annotation.Json;
import org.agilej.sparkle.core.ActionMethod;


import com.google.gson.Gson;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;

/**
 * View render for render json result, support the action method annotated with {@link Json} or the return type is
 * {@link JSONModel}. If the return type is {@link JSONModel}, this view render will use Jsonty to generate the result,
 * if the return type is not {@link JSONModel}, this view render will use google Gson to generate the result.
 */
public class JSONViewRender implements ViewRender {

    private JSONSerializer serializer;

    public JSONViewRender() {
        this(new GsonSerializer());
    }

    public JSONViewRender(JSONSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        
        response.setContentType("application/json; charset=UTF-8");
        
        if(result instanceof JSONModel){
            // new JSONBuilder((JSONModel)result).build(response.getWriter());
            // response.getWriter().flush();
            response.write(new JSONBuilder((JSONModel)result).build());
        } else {
            response.write(this.serializer.toJson(result));
        }
    }

    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        return actionMethodResult instanceof JSONModel || actionMethod.hasAnnotation(Json.class);
    }

}
