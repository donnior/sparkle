package org.agilej.sparkle.view.result;

public class HttpStatus{

    private int code;
    private String message;

    public HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }

    
}
