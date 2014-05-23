package me.donnior.srape.mapping;

import me.donnior.srape.Environment;
import me.donnior.srape.FieldExposer;
import me.donnior.srape.SrapeEntity;
import me.donnior.srape.model.User;

public class DetailedUserEntity implements SrapeEntity<User>{

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        exposer.expose(user.age).withName("age");
        exposer.expose(user.profile).withNameAndType("profile", ProfileEntity.class);
        exposer.expose(user.posts).withNameAndType("posts", PostEntity.class);
    }

}
