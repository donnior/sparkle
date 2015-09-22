package me.donnior.sparkle.core.dev;


import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import me.donnior.sparkle.WebRequest;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class DevelopmentErrorHandler {

    public void handle(WebRequest webRequest){
        String template = contentFromResource("err_template/main.html");

        try {
            String out = new SimpleTemplateEngine().render(template, valueMap(webRequest));
            webRequest.getWebResponse().write(out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Map<String, String> valueMap(WebRequest webRequest){
        Map<String, String> map = new HashMap<>();
        map.put("style", contentFromResource("err_template/style.css"));
        map.put("content", doHandle(webRequest));

        return map;
    }

    private String contentFromResource(String resource){
        URL url = Resources.getResource(resource);
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String doHandle(WebRequest webRequest);

}
