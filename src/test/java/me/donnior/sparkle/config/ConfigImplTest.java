package me.donnior.sparkle.config;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.servlet.ConfigImpl;

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
        
      assertEquals(0, config.getViewRenders().size());
      
      config.registerViewRenderClass(null);
      assertEquals(0, config.getViewRenders().size());
      
      config.registerViewRenderClass(DemoViewRenderForConfig.class);
      assertEquals(1, config.getViewRenders().size());
      
      config.registerViewRenderClass(DemoViewRenderForConfig.class);
      assertEquals(1, config.getViewRenders().size());
    }

    
}
class DemoViewRenderForConfig implements ViewRender{

    @Override
    public boolean supportActionMethod(ActionMethodDefinition adf,
            Object actionMethodResult) {
        return false;
    }

    @Override
    public void renderView(Object result, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }
    
}
