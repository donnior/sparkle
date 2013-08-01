package me.donnior.srape;

public interface ScopedFieldBuilder extends ConditionalFieldBuilder {

    
    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends SrapeEntity> entityClass);

    public ConditionalFieldBuilder withName(String string);
    

}
