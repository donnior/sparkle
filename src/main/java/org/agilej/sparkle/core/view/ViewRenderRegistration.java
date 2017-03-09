package org.agilej.sparkle.core.view;

import java.util.List;

import org.agilej.fava.FList;
import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.mvc.ViewRender;

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
public class ViewRenderRegistration {

    private FList<ViewRender> builtInViewRenders = FLists.newEmptyList();
    private FList<ViewRender> vendorViewRenders = FLists.newEmptyList();
    private FList<ViewRender> appScopedViewRenders = FLists.newEmptyList();
    
    public ViewRenderRegistration() {
        registerBuiltInViewRenders(builtInViewRenders);
    }

    /**
     * get the all and ordered view renders
     *
     * @return
     */
    public FList<ViewRender> getAllOrderedViewRenders(){
        return FLists.$(this.appScopedViewRenders).plus(this.builtInViewRenders).plus(this.vendorViewRenders);
    }

    private void registerBuiltInViewRenders(FList<ViewRender> viewRenders) {
        viewRenders.add(new JSONTextViewRender());
        viewRenders.add(new JSONModelViewRender());
        viewRenders.add(new JSONViewRender());
        viewRenders.add(new TextViewRender());
    }

    /**
     * for Sparkle's runtime vendor to register it's view renders, for example mvc jsp view
     * @param viewRenders
     */
    public void registerVendorViewRenders(List<ViewRender> viewRenders){
        this.vendorViewRenders.addAll(viewRenders);
    }

    /**
     * for Sparkle's runtime vendor to register it's view renders, for example mvc jsp view
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
        FList<ViewRender> viewRenders = FLists.create(appScopedViewRenders)
                                              .collect(new Function<Class<? extends ViewRender>, ViewRender>() {
            @Override
            public ViewRender apply(Class<? extends ViewRender> viewRenderClass) {
                return (ViewRender) ReflectionUtil.initialize(viewRenderClass);
            }
        });
        this.appScopedViewRenders.addAll(viewRenders);
    }
}
