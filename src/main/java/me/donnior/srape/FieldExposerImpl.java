package me.donnior.srape;

import me.donnior.fava.FList;
import me.donnior.fava.util.FLists;

public class FieldExposerImpl implements FieldExposer{

    private FList<FieldBuilderImpl> fieldsDefinition = FLists.newEmptyList();
    
    public ScopedFieldBuilder expose(Object value){
        FieldBuilderImpl sjb = new FieldBuilderImpl(value);
        this.fieldsDefinition.add(sjb);
        return sjb;
    }
    

    public FList<FieldBuilderImpl> fieldsExposeDefinition(){
        return this.fieldsDefinition;
    }
    
}
