package me.donnior.sparkle.demo;

import java.util.List;

import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.annotation.ResponseBody;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.view.result.HttpStatus;

@Controller("projects")
public class ProjectController {
    
    public String index(@Param("a") String a, @Param("b") Integer b, @Param("c") String[] c){
        return "projects/index";
    }
    
    
    public String index2(){
        return "projects/index2";
    }
    
    public String normalView(){
        return "";
    }
    
    public String redirectView(){
        return "redirect:users";
    }
    
    @Json
    public List<Object> jsonView(){
        
        return null;
    }
    
    @ResponseBody
    public String responseBody(){
        return "";
    }
    
    
    public HttpStatus httpStatus(){
        return new HttpStatus(HTTPStatusCode.CREATED, "Post created");
    }
    
    
    public String save(){
        return "projects/index";
    }

}
