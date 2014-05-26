package me.donnior.sparkle.core.view;

import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;

public class ViewRendersResovler {

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
    public List<ViewRender> resovleRegisteredViewRenders(List<Class<? extends ViewRender>> renders){
        return initViewRenders(FLists.create(renders));
    }
 
    private FList<ViewRender> initViewRenders(FList<Class<? extends ViewRender>> renders) {
        FList<ViewRender> viewRenders = renders.collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender)ReflectionUtil.initialize(viewRenderClass);
            }
        });
        
        ensuerDefaultViewRenders(viewRenders);
        registerCustomViewRenders(viewRenders);
        return viewRenders;
    }

    private void ensuerDefaultViewRenders(FList<ViewRender> viewRenders) {
        viewRenders.add(new JSONViewRender());
        viewRenders.add(new TextViewRender());
    }
    
    protected void registerCustomViewRenders(List<ViewRender> viewRenders){
        
    }
    
}
