package me.donnior.sparkle.core.resolver;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.exception.SparkleException;

import com.google.common.collect.Lists;


public class DefaultParamResolversManager implements ParamResolversManager {

    List<ArgumentResolver> argumentResolvers = Lists.newArrayList();
    
    public DefaultParamResolversManager() {
        this.argumentResolvers.add(new HttpRequestArgumentResolver());
        this.argumentResolvers.add(new HttpResponseArgumentResolver());
        this.argumentResolvers.add(new ParamInstanceArgumentResolver());
        this.argumentResolvers.add(new SimpleArgumentResolver());
    }
    
    @Override
    public Object resolve(final ActionParamDefinition actionParamDefinition, HttpServletRequest request) {
        FList<ArgumentResolver> list = FLists.create(argumentResolvers);
        ArgumentResolver matchedArgumentResolver = list.find(new Predicate<ArgumentResolver>() {
            public boolean apply(ArgumentResolver e) {
                return e.support(actionParamDefinition);
            }
        });
        if(matchedArgumentResolver == null){
            throw new SparkleException("can't find proper argument resolver for "+actionParamDefinition);
        }
        return matchedArgumentResolver.resovle(actionParamDefinition, request);
    }

     @Override
    public List<ArgumentResolver> registeredResolvers() {
      return Collections.unmodifiableList(this.argumentResolvers);
    }
    
}
