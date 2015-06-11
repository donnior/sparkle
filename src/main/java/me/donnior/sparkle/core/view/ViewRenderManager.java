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
    private FList<ViewRender> appScopedViewRenders = FLists.newEmptyList();
    
    public ViewRenderManager() {
        ensureDefaultViewRenders(allRegisteredViewRenders);
//        registerCustomViewRenders(allRegisteredViewRenders);
    }

    public FList<ViewRender> getAllOrderedViewRenders(){
        return this.allRegisteredViewRenders;
    }

    public void setupViewRenders(){
//        ensureDefaultViewRenders(this.allRegisteredViewRenders);
        registerCustomViewRenders(this.allRegisteredViewRenders);
        this.allRegisteredViewRenders.addAll(this.appScopedViewRenders);
    }

    public void registerAppScopedViewRender(List<Class<? extends ViewRender>> appScopedViewRenders){
        FList<ViewRender> viewRenders = FLists.create(appScopedViewRenders).collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender) ReflectionUtil.initialize(viewRenderClass);
            }
        });
        this.appScopedViewRenders.addAll(viewRenders);
    }

    /**
     * Resolve all view renders with the given customized view renders and built-in view renders. 
     * 
     * The Sparkle framework can handle three types view renders: 
     * <li> User defined view renders in their application (third-party view renders)
     * <li> Provider defined view renders (such as sparkle-servlet provide view renders for jsp pages)
     * <li> Sparkle's built-in view renders, like JSON and text view renders;
     *
     * Note. this method will be deprecated, clients should call registerAppScopedViewRenders() and
     * getAllOrderedViewRenders() these two steps.
     *
     * @param renders
     * @return
     */
    @Deprecated
    public List<ViewRender> resolveRegisteredViewRenders(List<Class<? extends ViewRender>> renders){
        FList<ViewRender> viewRenders = FLists.create(renders).collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender) ReflectionUtil.initialize(viewRenderClass);
            }
        });
        this.appScopedViewRenders.addAll(viewRenders);
        this.allRegisteredViewRenders.addAll(this.appScopedViewRenders);
        this.registerCustomViewRenders(this.allRegisteredViewRenders);
        return this.allRegisteredViewRenders;
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
