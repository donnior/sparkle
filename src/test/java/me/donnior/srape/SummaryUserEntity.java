package me.donnior.srape;

public class SummaryUserEntity implements SrapeEntity<User>{

    @Override
    public void config(User user, FieldExposer exposer) {
        exposer.expose(user.name).withName("name");
    }

}
