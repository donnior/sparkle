package me.donnior.sparkle;


//TODO should be introduced?
public interface WebResponse {
    
    <T> T getOriginalResponse();

    void setStatus(int notFound);
    
}
