package me.donnior.srape.mapping;

import me.donnior.srape.Environment;
import me.donnior.srape.FieldExposer;
import me.donnior.srape.SrapeEntity;
import me.donnior.srape.model.Post;

public class PostEntity implements SrapeEntity<Post>{

    @Override
    public void config(Post post, FieldExposer exposer, Environment env) {
        exposer.expose(post.title).withName("title");
    }

}
