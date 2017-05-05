package org.agilej.sparkle.core.view;

import org.agilej.sparkle.JSONText;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ViewRender;

import java.io.IOException;

public class JSONTextViewRender implements ViewRender {
    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        return actionMethod.getReturnType().equals(JSONText.class);
    }

    @Override
    public void renderView(Object result, Object controller, WebRequest request) throws IOException {
        WebResponse response = request.getWebResponse();
        response.setContentType("application/json; charset=UTF-8");
        response.write(((JSONText)result).getContent());
    }
}
