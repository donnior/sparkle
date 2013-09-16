package me.donnior.sparkle.demo;

import java.util.List;
import java.util.ArrayList;

import com.google.inject.Singleton;

@Singleton
public class Service {

    private List<User> users = new ArrayList<User>();
    
    public void hello(){
        System.out.println("hello");
    }
    
    public int saveUser(User user) {
        this.users.add(user);
        return this.users.size();
    }
    
    public List<User> listUsers(){
        return this.users;
    }
    
}
