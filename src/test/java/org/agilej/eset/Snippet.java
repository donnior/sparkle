package org.agilej.eset;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.agilej.fava.Consumer;
import org.agilej.fava.util.FLists;

public class Snippet {

    public static void main(String[] args) throws IntrospectionException{
        BeanInfo bi = Introspector.getBeanInfo(User.class);  
        PropertyDescriptor[] pd = bi.getPropertyDescriptors();
        FLists.$(pd).each(new Consumer<PropertyDescriptor>() {
            @Override
            public void apply(PropertyDescriptor pd) {
                System.out.println(pd);
            }
        });
        System.out.println("--------");
        FLists.$(bi.getAdditionalBeanInfo()).each(new Consumer<BeanInfo>() {
            @Override
            public void apply(BeanInfo bi) {
                System.out.println(bi);
            }
        });
    }
}

