package me.donnior.srape.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.donnior.srape.AbstractFieldExposerModule;
import me.donnior.srape.Environment;
import me.donnior.srape.Environments;
import me.donnior.srape.FieldExposerModule;
import me.donnior.srape.JSONBuilder;
import me.donnior.srape.mapping.DetailedUserEntity;
import me.donnior.srape.mapping.PostEntity;
import me.donnior.srape.mapping.SummaryUserEntity;
import me.donnior.srape.model.Post;
import me.donnior.srape.model.Profile;
import me.donnior.srape.model.User;

import org.junit.Test;


public class SrapeEntityTest {

    @Test
    public void test_srape_entity_value_1() {
        final User user = domainUser();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isAdmin", true);
        final Environment env = Environments.envFromMap(map);
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(user).withNameAndType("user", SummaryUserEntity.class).plusEnv(env);
            }
        };

        System.out.println(build(module));
    }

    @Test
    public void test_srape_entity_value_2() {
        final User user = domainUser();

        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(user).withNameAndType("user", DetailedUserEntity.class);
            }
        };

        System.out.println(build(module));
    }
    
    @Test
    public void test_srape_entity_value_3() {
        final User user = domainUser();

        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(user.posts).withType(PostEntity.class);
            }
        };

        System.out.println(build(module));
    }
    
    @Test
    public void test_srape_with_map_value(){
        final User user = domainUser();
        
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        map.put("num", 1);
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(map).withNameAndType("res", PostEntity.class);
            }
        };

        System.out.println(build(module));
    }
    

    private User domainUser() {
        User user = new User();
        user.name = "donny";
        user.age = 30;
        
        Profile p = new Profile();
        p.address = "Beijing China";
        user.profile = p;
        
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post("post 1"));
        posts.add(new Post("post 2"));
        posts.add(new Post("post 3"));
        user.posts = posts;
        return user;
    }

    private String build(FieldExposerModule module) {
        return new JSONBuilder(module).build();
    }

}