package me.donnior.srape;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;

import com.google.common.base.Joiner;

public class JSONBuilder {

    private FieldExposerImpl jsonDefinition = new FieldExposerImpl();
    
    
    public JSONBuilder(FieldExposerModule module) {
        module.config(this.jsonDefinition);
    }

    public String build() {
        final StringBuilder sb = new StringBuilder();
        boolean isArrayData = this.fieldsExposeDefinitionCount() == 1 &&
                this.getFieldsExposeDefinition().at(0).isPureArrayData();

        if(!isArrayData){
            sb.append("{");
        }
        
        FList<FieldBuilderImpl> fieldBuildersNeedExpose = this.getFieldsExposeDefinition().select(new Predicate<FieldBuilderImpl>() {
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
        
        if(!isArrayData){
            sb.append("}");
        }
        return sb.toString();
    }
    
    public int fieldsExposeDefinitionCount(){
        return this.jsonDefinition.fieldsExposeDefinition().size();
    }

    public FList<FieldBuilderImpl> getFieldsExposeDefinition() {
        return this.jsonDefinition.fieldsExposeDefinition();
    }
}
