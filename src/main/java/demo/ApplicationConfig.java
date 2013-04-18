package demo;

import me.donnior.sparkle.config.Application;
import me.donnior.sparkle.config.Config;
import me.donnior.sparkle.view.JSONViewRender;

public class ApplicationConfig implements Application{

    @Override
    public void config(Config config){
        config.registerViewRenderClass(JSONViewRender.class);
    }
    
}
