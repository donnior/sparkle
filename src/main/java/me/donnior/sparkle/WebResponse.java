package me.donnior.sparkle;

import java.io.Writer;

//TODO should be introduced?
public interface WebResponse {
    
    <T> T getOriginalResponse();

    void setStatus(int statusCode);

    void write(String build);
    
    void setHeader(String name, String value);
    
    void setContentType(String type);

    Writer getWriter();
}
