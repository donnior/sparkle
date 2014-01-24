package me.donnior.sparkle.core.resolver;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;
import me.donnior.sparkle.exception.SparkleException;

public abstract class AbstractParamResolversManager implements ParamResolversManager {

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    @Override
    public Object resolve(final ActionMethodParamDefinition actionParamDefinition, WebRequest request) {
        FList<ArgumentResolver> list = FLists.create(argumentResolvers);
        ArgumentResolver matchedArgumentResolver = list.find(new Predicate<ArgumentResolver>() {
            public boolean apply(ArgumentResolver e) {
                return e.support(actionParamDefinition);
            }
        });
        if (matchedArgumentResolver == null) {
            throw new SparkleException("can't find proper argument resolver for " + actionParamDefinition);
        }
        return matchedArgumentResolver.resovle(actionParamDefinition, request);
    }

    @Override
    public List<ArgumentResolver> registeredResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }
    
    protected void registerArgumentResolver(ArgumentResolver argumentResolver){
        this.argumentResolvers.add(argumentResolver);
    }

}
