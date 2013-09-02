package me.donnior.srape;

import me.donnior.fava.FList;

/**
 * 
 * Build JSON with one {@link FieldExposerModule}, this class is not thread safe!
 */
public class JSONBuilder {

    private FieldsExpositionHolder jsonDefinition = new FieldsExpositionHolder();
    
    
    public JSONBuilder(FieldExposerModule module) {
        module.config(this.jsonDefinition);
    }

    public String build() {
        return this.jsonDefinition.build();
//        final StringBuilder sb = new StringBuilder();
//        
//        boolean isAPureArrayDefinition = isAPureArrayDefinition();
//        if(!isAPureArrayDefinition){
//            sb.append("{");
//        }
//        
//        FList<FieldBuilderImpl> fieldBuildersNeedExpose = this.getFieldsExposeDefinition().select(new Predicate<FieldBuilderImpl>() {
//            @Override
//            public boolean apply(FieldBuilderImpl fieldBuilder) {
//                return fieldBuilder.conditionMatched();
//            }
//        });
//
//        FList<String> fieldStrings = fieldBuildersNeedExpose.map(new Function<FieldBuilderImpl, String>() {
//            @Override
//            public String apply(FieldBuilderImpl fieldBuilder) {
//                return fieldBuilder.toJson();
//            }
//        });
//        
//        sb.append(Joiner.on(",").join(fieldStrings));
//        
//        if(!isAPureArrayDefinition){
//            sb.append("}");
//        }
//        return sb.toString();
    }

    private boolean isAPureArrayDefinition() {
        return this.fieldsExposeDefinitionCount() == 1 &&
                this.getFieldsExposeDefinition().at(0).isPureIterableValue();
    }
    
    public int fieldsExposeDefinitionCount(){
        return this.jsonDefinition.fieldsExposeDefinition().size();
    }

    public FList<FieldBuilderImpl> getFieldsExposeDefinition() {
        return this.jsonDefinition.fieldsExposeDefinition();
    }
}
