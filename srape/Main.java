import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.donnior.srape.JSONBuilder;
import me.donnior.srape.JSONDefinition;
import me.donnior.srape.JsonObject;
import me.donnior.srape.JsonObjectWriter;
import me.donnior.srape.SrapeEntity;


public class Main {

    public static void main1(String[] args){
        JsonObject jo = new JsonObject();
        
        User user = new User();
        
        Account account = new Account();
        
        jo.expose(user).withName("user");
        jo.expose(account).withNameAndType("user", AccountEntity.class);
        
        JsonObjectWriter writer = new JsonObjectWriter();
        
        System.out.println(writer.write(jo));
        
    }
    
    
    public static void main(String[] args){
        List<String> list = Arrays.asList("one", "tw\"o", "three");
        
        JsonObjectWriter writer = new JsonObjectWriter();
        
        System.out.println(writer.writeArray(list, String.class));
        
        System.out.println(writer.writeArray(Arrays.asList(3,2,1), String.class));
        
    }
    
}


class User{
    String name;
    int age;
}

class Account{
    String login;
    Date loginAt;
}

class AccountEntity extends SrapeEntity{
    
}

class Controller{
 
    String jsonResult(){
        final List<User> users = null;
        final Account account = null;
        final int age = 0;
        return new JSONBuilder(new JSONDefinition(){
            public void define() {
                this.expose(users).withName("users").when(age > 16);
                this.expose(account).withNameAndType("account", AccountEntity.class);
            }
        }).build();
    }
 
    JSONDefinition jsonResult2(){
        final List<User> users = null;
        final Account account = null;
        return new JSONDefinition(){
            public void define() {
               this.expose(users);
            }
           
        };
    }
    
}