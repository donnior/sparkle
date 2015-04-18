package me.donnior.sparkle.core.view;

import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.resolver.ViewRenderResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleViewRenderResolver implements ViewRenderResolver{

    private FList<ViewRender> viewRenders = FLists.newEmptyList();
    
    private final static Logger logger = LoggerFactory.getLogger(SimpleViewRenderResolver.class);
    
    public SimpleViewRenderResolver(List<ViewRender> renders) {
        this.viewRenders.addAll(renders);
    }
    
    @Override
    public ViewRender resolveViewRender(final ActionMethodDefinition adf, final Object result) {
        return this.viewRenders.find(new Predicate<ViewRender>() {
            @Override
            public boolean apply(ViewRender viewRender) {
                boolean isViewRenderSupportResult = viewRender.supportActionMethod(adf, result);
                logger.debug("{} match action result [{}] : {}", 
                        viewRender.getClass().getName(), result.getClass().getName(), isViewRenderSupportResult);

                return isViewRenderSupportResult;
            }
        });
    }

}