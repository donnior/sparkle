package me.donnior.srape;

public interface SrapeEntity<E> {

    public void config(E object, FieldExposer exposer, Environment env);
    
}
