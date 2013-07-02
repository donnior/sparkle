package me.donnior.srape;

public class ScopedJsonBuilder extends ConditionalBuilder {

    public ConditionalBuilder withNameAndType(String string, Class<? extends SrapeEntity> class1) {
        return this;
    }

    public ConditionalBuilder withName(String string) {
        return this;        
    }

}
