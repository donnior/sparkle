package org.agilej.sparkle.core.view;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ViewRender;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractTemplateEngineViewRender implements ViewRender {

    private String prefix = "";
    private String suffix = "";
    private String contentType;

    @Override
    public boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult) {
        return String.class.equals(actionMethodResult.getClass());
    }

    @Override
    public void renderView(Object result, Object controller, WebRequest request) throws IOException {
        Map templateArgs = null;
        // if result is modelAndView , use the model map otherwise extract model from controller

        templateArgs = new ViewVariablesExposer().getValueMap(controller);

        this.renderTemplate(result.toString(), templateArgs, request);
    }

    protected abstract void renderTemplate(String templateName, Map args, WebRequest request);

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix =  (suffix != null ? suffix : "");
    }

    public String getSuffix() {
        return suffix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
