package me.donnior.srape;


public abstract class AbstractFieldExposerModule implements FieldExposerModule{

    private FieldExposer exposer;
    
    public abstract void config();
    
    @Override
    public void config(FieldExposer exposer) {
        this.exposer = exposer;
        try {
            this.config();
        } finally {
            this.exposer = null;
        }
        
    }

    protected ScopedFieldBuilder expose(Object field){
        return this.exposer.expose(field);
    }
    
}
