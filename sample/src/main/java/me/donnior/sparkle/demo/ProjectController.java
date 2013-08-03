package me.donnior.sparkle.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.donnior.fava.util.FLists;
import me.donnior.sparkle.Params;
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
    
    public String show(){
        return "projects/show";
    }
    
    public String index2(Params params){
        System.out.println("param account : " + params.get("account"));
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
    
    @Json
    public Map<String, String> jsons(){
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("name", "donny");
        maps.put("age", "10");
        return maps;
    }
    
    @Json
    public List<String> json(){
        return FLists.create("one", "two", "three", "four");
    }
    
    @ResponseBody
    public String responseBody(){
        return "";
    }
    
    
    public HttpStatus httpStatus(){
        return new HttpStatus(HTTPStatusCode.CREATED, "Post created");
    }
    
    
    public String save(){
        return "redirect:projects/index";
    }

}
