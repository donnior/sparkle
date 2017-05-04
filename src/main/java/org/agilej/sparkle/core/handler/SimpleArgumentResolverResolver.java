package org.agilej.sparkle.core.handler;

import com.google.common.collect.Lists;
import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.mvc.ArgumentResolver;

import java.util.Collections;
import java.util.List;

/**
 * simple ArgumentResolverResolver which just iterate given handler resolver list and find the first match one
 *
 */
public class SimpleArgumentResolverResolver implements ArgumentResolverResolver {

    private List<ArgumentResolver> argumentResolvers = Lists.newArrayList();

    public  SimpleArgumentResolverResolver(ArgumentResolverRegistration registration) {
        this(registration.getAllOrderedArgumentResolvers());
    }

    public SimpleArgumentResolverResolver(List<ArgumentResolver> argumentResolvers) {
        this.registerArgumentResolvers(argumentResolvers);
    }

    public SimpleArgumentResolverResolver() {

    }

    @Override
    public ArgumentResolver resolve(final ActionMethodArgument parameter) {
        FList<ArgumentResolver> list = FLists.create(argumentResolvers);
        ArgumentResolver matchedArgumentResolver = list.find(new Predicate<ArgumentResolver>() {
            public boolean apply(ArgumentResolver e) {
                return e.support(parameter);
            }
        });
        if (matchedArgumentResolver == null) {
            throw new SparkleException("Can't find proper handler resolver for " + parameter);
        }
        return matchedArgumentResolver;
    }

    public List<ArgumentResolver> registeredResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }

    protected void registerArgumentResolver(ArgumentResolver argumentResolver){
        this.argumentResolvers.add(argumentResolver);
    }

    public void registerArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        for (ArgumentResolver argumentResolver : argumentResolvers) {
            this.registerArgumentResolver(argumentResolver);
        }
    }

}
