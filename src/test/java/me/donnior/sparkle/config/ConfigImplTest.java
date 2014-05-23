package me.donnior.sparkle.config;

import static org.junit.Assert.assertEquals;

import java.io.IOException;


import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.engine.ConfigImpl;

import org.junit.Before;
import org.junit.Test;

public class ConfigImplTest {

    private ConfigImpl config;
    
    @Before
    public void setup(){
        this.config = new ConfigImpl();
    }
    
    @Test
    public void testRegisterControllerPackage(){
        
        assertEquals(0, config.getControllerPackages().length);
        
        config.registerControllerPackages((String)null);
        assertEquals(0, config.getControllerPackages().length);
        
        config.registerControllerPackages("");
        assertEquals(1, config.getControllerPackages().length);
        
        
        config.registerControllerPackages("package.one");
        assertEquals(2, config.getControllerPackages().length);
        
        config.registerControllerPackages("package.two", "package.three");
        assertEquals(4, config.getControllerPackages().length);
    }
    

    @Test
    public void testRegisterViewRenders(){
        
      assertEquals(0, config.getCustomizedViewRenders().size());
      
      config.registerViewRenderClass(null);
      assertEquals(0, config.getCustomizedViewRenders().size());
      
      config.registerViewRenderClass(DemoViewRenderForConfig.class);
      assertEquals(1, config.getCustomizedViewRenders().size());
      
      config.registerViewRenderClass(DemoViewRenderForConfig.class);
      assertEquals(1, config.getCustomizedViewRenders().size());
    }

    
}
class DemoViewRenderForConfig implements ViewRender{

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf,
            Object actionMethodResult) {
        return false;
    }

    @Override
    public void renderView(Object result, Object controller, WebRequest request) throws IOException {
    }
    
}
