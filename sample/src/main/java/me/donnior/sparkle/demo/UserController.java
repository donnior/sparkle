package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;

@Controller("user")
public class UserController{

  public String index(){
    return "user/index";
  }

}
