package me.donnior.srape;

import com.google.common.base.Joiner;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;

public class FieldsExpositionHolder implements FieldExposer{

    private FList<FieldBuilderImpl> fieldsDefinition = FLists.newEmptyList();
    
    public ScopedFieldBuilder expose(Object value){
        FieldBuilderImpl sjb = new FieldBuilderImpl(value);
        this.fieldsDefinition.add(sjb);
        return sjb;
    }
    
    public FList<FieldBuilderImpl> fieldsExposeDefinition(){
        return this.fieldsDefinition;
    }

    public String build() {
        final StringBuilder sb = new StringBuilder();
        
        boolean isAPureArrayDefinition = isAPureArrayDefinition();
        if(!isAPureArrayDefinition){
            sb.append("{");
        }
        
        FList<FieldBuilderImpl> fieldBuildersNeedExpose = this.fieldsExposeDefinition().select(new Predicate<FieldBuilderImpl>() {
            @Override
            public boolean apply(FieldBuilderImpl fieldBuilder) {
                return fieldBuilder.conditionMatched();
            }
        });

        FList<String> fieldStrings = fieldBuildersNeedExpose.map(new Function<FieldBuilderImpl, String>() {
            @Override
            public String apply(FieldBuilderImpl fieldBuilder) {
                return fieldBuilder.toJson();
            }
        });
        
        sb.append(Joiner.on(",").join(fieldStrings));
        
        if(!isAPureArrayDefinition){
            sb.append("}");
        }
        return sb.toString();
    }
    
    private boolean isAPureArrayDefinition() {
        return this.fieldsExposeDefinition().size() == 1 &&
                this.fieldsExposeDefinition().at(0).isPureIterableValue();
    }
   
}
