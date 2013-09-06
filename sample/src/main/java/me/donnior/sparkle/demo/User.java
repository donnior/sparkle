package me.donnior.sparkle.demo;

public class User {

    private String name;
    private String mail;
    
    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }
    
    public String getMail() {
        return mail;
    }
    
    public String getName() {
        return name;
    }
    
}
