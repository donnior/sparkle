package me.donnior.srape;

public class ProfileEntity implements SrapeEntity<Profile>{

    @Override
    public void config(Profile profile, FieldExposer exposer) {
        exposer.expose(profile.address).withName("address");
    }

}
