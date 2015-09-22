package me.donnior.sparkle.core.view;

import java.util.List;

import org.agilej.fava.FList;
import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;

/**
 * Holder for all supported ViewRenders with different priority.
 *
 *  <li> Application's customized view renders have the highest priority
 *
 *  <li> Sparkle's built-in view renders have the second priority, including {@link JSONViewRender} and {@link TextViewRender}
 *
 *  <li> Sparkle vendor's view renders have the lowest priority, *  such as Servlet's Jsp
 *
 *
 */
public class ViewRenderManager {

    private FList<ViewRender> allRegisteredViewRenders = FLists.newEmptyList();
    private FList<ViewRender> vendorViewRenders = FLists.newEmptyList();
    private FList<ViewRender> appScopedViewRenders = FLists.newEmptyList();
    
    public ViewRenderManager() {
        registerBuiltInViewRenders(allRegisteredViewRenders);
//        registerVendorViewRenders(allRegisteredViewRenders);
    }


    public void setupViewRenders(){

    }

    /**
     * get the all and ordered view renders
     *
     * @return
     */
    public FList<ViewRender> getAllOrderedViewRenders(){
        return FLists.$(this.appScopedViewRenders).plus(this.allRegisteredViewRenders).plus(this.vendorViewRenders);
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
    private List<ViewRender> resolveRegisteredViewRenders(List<Class<? extends ViewRender>> renders){
        FList<ViewRender> viewRenders = FLists.create(renders).collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender) ReflectionUtil.initialize(viewRenderClass);
            }
        });
        this.appScopedViewRenders.addAll(viewRenders);
        this.allRegisteredViewRenders.addAll(this.appScopedViewRenders);
        this.registerVendorViewRenders(this.allRegisteredViewRenders);
        return this.allRegisteredViewRenders;
    }

    private void registerBuiltInViewRenders(FList<ViewRender> viewRenders) {
        viewRenders.add(new JSONViewRender());
        viewRenders.add(new TextViewRender());
    }

    /**
     * for Sparkle's runtime vendor to register it's view renders, for example servlet jsp view
     * @param viewRenders
     */
    public void registerVendorViewRenders(List<ViewRender> viewRenders){
        this.vendorViewRenders.addAll(viewRenders);
    }

    /**
     * for Sparkle's runtime vendor to register it's view renders, for example servlet jsp view
     * @param viewRender
     *
     * @see #registerVendorViewRenders(List)
     */
    public void registerVendorViewRender(ViewRender viewRender){
        this.vendorViewRenders.add(viewRender);
    }


    /**
     *
     * register the application scoped view renders to sparkle
     *
     */
    public void registerAppScopedViewRender(List<Class<? extends ViewRender>> appScopedViewRenders){
        FList<ViewRender> viewRenders = FLists.create(appScopedViewRenders).collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender) ReflectionUtil.initialize(viewRenderClass);
            }
        });
        this.appScopedViewRenders.addAll(viewRenders);
    }
}
