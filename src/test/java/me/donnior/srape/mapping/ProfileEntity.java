package me.donnior.srape.mapping;

import me.donnior.srape.FieldExposer;
import me.donnior.srape.SrapeEntity;
import me.donnior.srape.model.Profile;

public class ProfileEntity implements SrapeEntity<Profile>{

    @Override
    public void config(Profile profile, FieldExposer exposer) {
        exposer.expose(profile.address).withName("address");
    }

}
