package org.agilej.sparkle.core.view;

import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ViewRender;

import java.io.IOException;

/**
 * View render for render json result, support the handler method
 * 's return type is {@link JSONModel}. If the return type is {@link JSONModel},
 * this view render will use Jsonty to generate the result.
 */
public class JSONModelViewRender implements ViewRender {

    private JSONBuilder jsonBuilder;

    public JSONModelViewRender() {
        this.jsonBuilder = new JSONBuilder();
    }

    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        response.setContentType("application/json; charset=UTF-8");
        
        response.write(jsonBuilder.build((JSONModel)result));
    }

    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        return actionMethodResult instanceof JSONModel;
    }

}
