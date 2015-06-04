package me.donnior.sparkle.core.view;

import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;

/**
 * Holder for all supported ViewRenders with different priority.
 * 
 *  <li> Sparkle's built-in view renders have highest priority, 
 *  including {@link JSONViewRender} and {@link TextViewRender}
 *  
 *  <li> Application's customized view renders have the second priority.
 *  
 *  <li> The Sparkle's runtime provider's view renders have the lowest priority, 
 *  such as Servlet's Jsp page
 *
 */
public class ViewRenderManager {

    private FList<ViewRender> allRegisteredViewRenders = FLists.newEmptyList();
    
    public ViewRenderManager() {
        ensureDefaultViewRenders(allRegisteredViewRenders);
//        registerCustomViewRenders(allRegisteredViewRenders);
    }
    
    /**
     * Resolve all view renders with the given customized view renders and built-in view renders. 
     * 
     * The Sparkle framework can handle three types view renders: 
     * <li> User defined view renders in their application (third-party view renders)
     * <li> Provider defined view renders (such as sparkle-servlet provide view renders for jsp pages)
     * <li> Sparkle's built-in view renders, like JSON and text view renders;
     * 
     * @param renders
     * @return
     */
    public List<ViewRender> resolveRegisteredViewRenders(List<Class<? extends ViewRender>> renders){
        return initViewRenders(FLists.create(renders));
    }
 
    private FList<ViewRender> initViewRenders(FList<Class<? extends ViewRender>> renders) {
        
        FList<ViewRender> viewRenders = renders.collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender)ReflectionUtil.initialize(viewRenderClass);
            }
        });
        
//        ensureDefaultViewRenders(viewRenders);
        allRegisteredViewRenders.addAll(viewRenders);
        registerCustomViewRenders(allRegisteredViewRenders);
        
        return allRegisteredViewRenders;
    }

    private void ensureDefaultViewRenders(FList<ViewRender> viewRenders) {
        viewRenders.add(new JSONViewRender());
        viewRenders.add(new TextViewRender());
    }
    
    protected void registerCustomViewRenders(List<ViewRender> viewRenders){
        
    }
    
}
