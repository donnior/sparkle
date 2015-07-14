package me.donnior.eset;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import me.donnior.eset.type.TypedAccessibleAttributeFactory;
import org.agilej.fava.Consumer;
import org.agilej.fava.Function;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;

public class EntityUpdater {

    final TypedAccessibleAttributeFactory factory = new TypedAccessibleAttributeFactory();
    
    public void updateAttribute(final Object target, final Map<String, Object> params) {
        List<AccessibleAttribute> attrs = getAccessableAttributes(target.getClass());
        FLists.create(attrs).each(new Consumer<AccessibleAttribute>() {
            @Override
            public void apply(AccessibleAttribute accessableAttribute) {
                accessableAttribute.update(target, params);
            }
        });
    }

    private List<AccessibleAttribute> getAccessableAttributes(final Class<? extends Object> clz) {
        Field[] fields = clz.getDeclaredFields();
        return FLists.create(fields).findAll(new Predicate<Field>() {
            @Override
            public boolean apply(Field field) {
                return field.isAnnotationPresent(Accessible.class);
            }
        }).collect(new Function<Field, AccessibleAttribute>() {
            @Override
            public AccessibleAttribute apply(Field field) {
                return factory.accessableAttributeFor(field);
            }
        });
    }

}
