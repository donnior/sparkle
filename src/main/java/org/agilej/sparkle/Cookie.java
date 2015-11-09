package org.agilej.sparkle;


public class Cookie {

    private final String name;
    private String domain = "";
    private String path = "/";
    private String value = "";
    private int maxAge = 0;   //TODO default max-age

    public Cookie(String name){
        this.name = name;
    }

    public Cookie(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String name(){
        return this.name;
    }

    public String domain(){
        return this.domain;
    }

    public String path(){
        return this.path;
    }

    public String value(){
        return this.value;
    }

    public int maxAge(){
        return this.maxAge;
    }

    public Cookie domain(String domain){
        this.domain = domain;
        return this;
    }

    public Cookie path(String path){
        this.path = path;
        return this;
    }

    public Cookie value(String value){
        this.value = value;
        return this;
    }

    public Cookie maxAge(int maxAge){
        this.maxAge = maxAge;
        return this;
    }

}
