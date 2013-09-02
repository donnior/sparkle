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
     * @param renders
     * @return
     */
    public List<? extends ViewRender> resovleRegisteredViewRenders(List<Class<? extends ViewRender>> renders){
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
        return viewRenders;
    }

    private void ensuerDefaultViewRenders(FList<ViewRender> viewRenders) {
        viewRenders.add(new JSONViewRender());
        viewRenders.add(new RedirectViewRender());
        viewRenders.add(new JSPViewRender());
    }
    
}
