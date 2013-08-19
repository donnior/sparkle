package me.donnior.srape;

public class PostEntity implements SrapeEntity<Post>{

    @Override
    public void config(Post post, FieldExposer exposer) {
        exposer.expose(post.title).withName("title");
    }

}
