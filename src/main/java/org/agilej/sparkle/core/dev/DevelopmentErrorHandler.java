package org.agilej.sparkle.core.dev;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.agilej.sparkle.WebRequest;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class DevelopmentErrorHandler {

    public void handle(WebRequest webRequest){
        String template = contentFromResource("err_template/main.html");

        Map<String, String> values = valueMap(webRequest);
        plusTemplateValue(values);

        String out = renderTemplate(template, values);
        webRequest.getWebResponse().write(out);
    }

    protected String renderTemplate(String template, Map<String, String> values){
        try {
            return new SimpleTemplateEngine().render(template, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void plusTemplateValue(Map<String, String> values) {

    }

    private Map<String, String> valueMap(WebRequest webRequest){
        Map<String, String> map = new HashMap<>();
        map.put("style", contentFromResource("err_template/style.css"));
        map.put("request_path", webRequest.getPath());
        map.put("request_method", webRequest.getMethod());
        map.put("request_params", null);
        map.put("request_headers", null);
        map.put("request_extra", new CookiesFormatter(webRequest.cookies()).format());

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
