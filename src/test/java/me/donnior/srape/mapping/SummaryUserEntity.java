package me.donnior.srape.mapping;

import me.donnior.srape.Environment;
import me.donnior.srape.FieldExposer;
import me.donnior.srape.SrapeEntity;
import me.donnior.srape.model.User;

public class SummaryUserEntity implements SrapeEntity<User>{

    @Override
    public void config(User user, FieldExposer exposer, Environment env) {
        exposer.expose(user.name).withName("name");
        Boolean isAdmin = env.get("isAdmin");
        exposer.expose(isAdmin).withName("isAdmin").when(isAdmin);
    }

}
