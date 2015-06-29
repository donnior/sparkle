package me.donnior.sparkle.core.view;

import java.io.IOException;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;
import me.donnior.sparkle.annotation.Text;
import me.donnior.sparkle.core.ActionMethod;

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
