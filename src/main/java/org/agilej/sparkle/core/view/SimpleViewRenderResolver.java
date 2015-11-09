package org.agilej.sparkle.core.view;

import java.util.List;

import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.core.ActionMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleViewRenderResolver implements ViewRenderResolver{

    private FList<ViewRender> viewRenders = FLists.newEmptyList();
    
    private final static Logger logger = LoggerFactory.getLogger(SimpleViewRenderResolver.class);
    
    public SimpleViewRenderResolver(List<ViewRender> renders) {
        this.viewRenders.addAll(renders);
        logger.debug("ViewRenderResolver created with all ordered view renders : {}",
                this.viewRenders.map(e -> e.getClass().getSimpleName()));
    }
    
    @Override
    public ViewRender resolveViewRender(final ActionMethod actionMethod, final Object result) {
        return this.viewRenders.find(new Predicate<ViewRender> () {
            @Override
            public boolean apply(ViewRender viewRender) {
                boolean isViewRenderSupportResult = viewRender.supportActionMethod(actionMethod, result);
                logger.debug("{} match action result [{}] : {}", 
                        viewRender.getClass().getSimpleName(), result.getClass().getName(),
                        isViewRenderSupportResult ? "succeed" : "failed");
                return isViewRenderSupportResult;
            }
        });
    }

}
