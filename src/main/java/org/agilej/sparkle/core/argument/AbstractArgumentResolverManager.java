package org.agilej.sparkle.core.argument;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;
import org.agilej.sparkle.exception.SparkleException;

public abstract class AbstractArgumentResolverManager implements ArgumentResolverManager {

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    @Override
    public Object resolve(final ActionMethodParameter parameter, WebRequest request) {
        FList<ArgumentResolver> list = FLists.create(argumentResolvers);
        ArgumentResolver matchedArgumentResolver = list.find(new Predicate<ArgumentResolver>() {
            public boolean apply(ArgumentResolver e) {
                return e.support(parameter);
            }
        });
        if (matchedArgumentResolver == null) {
            throw new SparkleException("Can't find proper argument resolver for " + parameter);
        }
        return matchedArgumentResolver.resolve(parameter, request);
    }

    public List<ArgumentResolver> registeredResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }
    
    protected void registerArgumentResolver(ArgumentResolver argumentResolver){
        this.argumentResolvers.add(argumentResolver);
    }

}
