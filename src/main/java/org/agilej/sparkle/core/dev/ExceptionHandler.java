package org.agilej.sparkle.core.dev;

import com.google.common.base.Throwables;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler extends DevelopmentErrorHandler{

    private final Throwable ex;

    public ExceptionHandler(Throwable ex) {
        this.ex = ex;
    }

    @Override
    protected String doHandle(WebRequest webRequest) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        this.ex.printStackTrace(pw);

        return "<div><h2>Exception Detail</h2>" +
                 "<div class='code code_block'>" +
                  "<pre><code>" + sw.toString() + "</code></pre>" +
                 "</div>" +
                "</div>";
    }
}
