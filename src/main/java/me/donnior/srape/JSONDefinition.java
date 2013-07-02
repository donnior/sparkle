package me.donnior.srape;

import me.donnior.fava.FList;
import me.donnior.fava.util.FLists;

public abstract class JSONDefinition {

    private FList<ScopedJsonBuilder> fieldsDefinition = FLists.newEmptyList();
    
    public ScopedJsonBuilder expose(Object user){
        ScopedJsonBuilder sjb = new ScopedJsonBuilder();
        this.fieldsDefinition.add(sjb);
        return sjb;
    }
    
    public abstract void define();

}
