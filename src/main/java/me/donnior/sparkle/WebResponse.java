package me.donnior.sparkle;


//TODO should be introduced?
public interface WebResponse {
    
    <T> T getOriginalResponse();

    void setStatus(int notFound);

    void write(String build);
    
    void setHeader(String name, String value);
    
    void setContentType(String type);
}
