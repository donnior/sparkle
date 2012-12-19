package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;

@Controller("home")
public class HomeController {
    
    public String index(){
        return "index";
    }

}
