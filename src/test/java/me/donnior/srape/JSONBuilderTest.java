package me.donnior.srape;
import static me.donnior.srape.StringUtil.jsonPair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class JSONBuilderTest {
    
    @Test
    public void test(){
        List<String> list = Arrays.asList("one", "tw\"o", "three");
        
        JsonObjectWriter writer = new JsonObjectWriter();
        
        assertEquals("[\"one\",\"tw\\\"o\",\"three\"]", writer.writeArray(list, String.class));
        
        assertEquals("[3,2,1]", writer.writeArray(Arrays.asList(3,2,1), String.class));
        
    }
    
    @Test
    public void test_integration(){
        JSONBuilder builder = new Controller().jsonResult();
        
        System.out.println(builder.build());
        
        assertEquals(2, builder.fieldsExposeDefinitionCount());
        
        FieldBuilderImpl impl = builder.getFieldsExposeDefinition().first();
        assertEquals("users", impl.getName());
        assertFalse(impl.conditionMatched());
        assertNull(impl.getEntityClass());
        
        impl = builder.getFieldsExposeDefinition().at(1);
        assertEquals("account", impl.getName());
        assertTrue(impl.conditionMatched());
        assertTrue(impl.getEntityClass().equals(AccountEntity.class));
        
        
    }
    
    @Test
    public void test_build(){
        JSONBuilder builder = new JSONBuilder(new FieldExposerModule() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose("donny").withName("user").when(2 > 1);
                exposer.expose("passwd").withName("password").when(1 > 2);
                exposer.expose(23).withName("age");
            }
        });
        String expected = "{" + jsonPair("user", "donny", true) + "," + jsonPair("age", 23, false)+ "}";
        assertEquals(expected, builder.build());
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

class AccountEntity extends SrapeEntity<Account>{
    
    @Override
    public void config(Account account, FieldExposer exposer) {
        exposer.expose(account.login).withName("users").when(1 > 76);
        exposer.expose(account.loginAt).withNameAndType("account", AccountEntity.class);
    }
    
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
 
}