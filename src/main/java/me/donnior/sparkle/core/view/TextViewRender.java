package me.donnior.sparkle.core.view;

import java.io.IOException;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;
import me.donnior.sparkle.annotation.Text;
import me.donnior.sparkle.core.ActionMethodDefinition;

public class TextViewRender implements ViewRender {
    
    @Override
    public void renderView(Object result, Object controller, WebRequest webRequest) throws IOException {
        WebResponse response = webRequest.getWebResponse();
        
        response.setContentType("application/text; charset=UTF-8");
        response.write(result.toString());
    }

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf, Object actionMethodResult) {
        return adf.hasAnnotation(Text.class);
    }

}
