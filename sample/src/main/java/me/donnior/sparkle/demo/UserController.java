package me.donnior.sparkle.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.donnior.eset.EntityUpdater;
import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.annotation.Out;

@Controller("user")
public class UserController {

    @Out
    private List<User> users;
    
    public String index() {
        this.users = new ArrayList<User>();
        users.add(new User("james", "james@gmail.com"));
        users.add(new User("kevin", "kevin@gmail.com"));
        users.add(new User("michael", "michael@gmail.com"));
        users.add(new User("steven", "steven@gmail.com"));
        
        return "user/index";
    }
    
    public String save(HttpServletRequest request) {
        User user = new User();
        new EntityUpdater().updateAttribute(user, request.getParameterMap());
        System.out.println(user.toString());
        return "redirect:user/index";
    }

}
