import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.donnior.fava.Consumer;
import me.donnior.srape.AbstractFieldExposerModule;
import me.donnior.srape.FieldBuilderImpl;
import me.donnior.srape.FieldExposer;
import me.donnior.srape.FieldExposerModule;
import me.donnior.srape.JSONBuilder;
import me.donnior.srape.FieldExposerImpl;
import me.donnior.srape.JsonObject;
import me.donnior.srape.JsonObjectWriter;
import me.donnior.srape.SrapeEntity;


public class Main {
    
    public static void main2(String[] args){
        List<String> list = Arrays.asList("one", "tw\"o", "three");
        
        JsonObjectWriter writer = new JsonObjectWriter();
        
        System.out.println(writer.writeArray(list, String.class));
        
        System.out.println(writer.writeArray(Arrays.asList(3,2,1), String.class));
        
    }
    
    public static void main(String[] args){
        JSONBuilder builder = new Controller().jsonResult();
        System.out.println(builder.fieldsExposeDefinitionCount());
        builder.getFieldsExposeDefinition().each(new Consumer<FieldBuilderImpl>() {
            
            @Override
            public void apply(FieldBuilderImpl expose) {
                String name = expose.getName();
                Class type = expose.getEntityClass();
                boolean conditionMatched = expose.conditionMatched();
                System.out.println(name);
                System.out.println(type);
                System.out.println(conditionMatched);
            }
        });
        
        builder = new Controller().jsonResult2();
        System.out.println(builder.fieldsExposeDefinitionCount());
        builder.getFieldsExposeDefinition().each(new Consumer<FieldBuilderImpl>() {
            
            @Override
            public void apply(FieldBuilderImpl expose) {
                String name = expose.getName();
                Class type = expose.getEntityClass();
                boolean conditionMatched = expose.conditionMatched();
                System.out.println(name);
                System.out.println(type);
                System.out.println(conditionMatched);
            }
        });
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
 
    JSONBuilder jsonResult(){
        final List<User> users = null;
        final Account account = null;
        final int age = 20;
        return new JSONBuilder(new FieldExposerModule() {
            
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(users).withName("users").when(age > 76);
                exposer.expose(account).withNameAndType("account", AccountEntity.class);
            }
        });
    }
    
    JSONBuilder jsonResult2(){
        final List<User> users = null;
        final Account account = null;
        final int age = 20;
        return new JSONBuilder(new AbstractFieldExposerModule() {
            
            @Override
            public void config() {
                expose(users).withName("users").when(age > 76);
                expose(account).withNameAndType("account", AccountEntity.class);      
            }
        });
    }
 
    FieldExposerImpl jsonResult3(){
        final List<User> users = null;
        final Account account = null;
        return new FieldExposerImpl(){
            public void define() {
               this.expose(users);
            }
           
        };
    }
    
}