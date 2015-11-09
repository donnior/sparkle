package org.agilej.sparkle.core.view;

import java.io.IOException;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.annotation.Text;
import org.agilej.sparkle.core.ActionMethod;

public class TextViewRender implements ViewRender {
    
    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        
        response.setContentType("text/plain; charset=UTF-8");
        response.write(result.toString());
    }

    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        return actionMethod.hasAnnotation(Text.class);
    }

}
