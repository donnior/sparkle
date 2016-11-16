package org.agilej.sparkle.core.view;

import java.util.List;

import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.core.action.ActionMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@link ViewRenderResolver} which will iterate all registered {@link ViewRender} and find the first one
 * can process the result.
 */
public class SimpleViewRenderResolver implements ViewRenderResolver{

    private FList<ViewRender> viewRenders = FLists.newEmptyList();
    
    private final static Logger logger = LoggerFactory.getLogger(SimpleViewRenderResolver.class);
    
    public SimpleViewRenderResolver(List<ViewRender> renders) {
        this.viewRenders.addAll(renders);
        logger.debug("Registered view renders : {}",
                this.viewRenders.map(e -> e.getClass().getSimpleName()));
    }
    
    @Override
    public ViewRender resolveViewRender(final ActionMethod actionMethod, final Object result) {
        return this.viewRenders.find(new Predicate<ViewRender> () {
            @Override
            public boolean apply(ViewRender viewRender) {
                boolean isViewRenderSupportResult = viewRender.supportActionMethod(actionMethod, result);
                if (isViewRenderSupportResult && logger.isDebugEnabled()) {
                    logger.debug("Found view render {} for result : {}",
                            viewRender.getClass().getSimpleName(),  result.getClass().getSimpleName());
                }
                return isViewRenderSupportResult;
            }
        });
    }

}
