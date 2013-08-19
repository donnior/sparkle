package me.donnior.srape;

public class DetailedUserEntity implements SrapeEntity<User>{

    @Override
    public void config(User user, FieldExposer exposer) {
        exposer.expose(user.name).withName("name");
        exposer.expose(user.age).withName("age");
        exposer.expose(user.profile).withNameAndType("profile", ProfileEntity.class);
        exposer.expose(user.posts).withNameAndType("posts", PostEntity.class);
    }

}
