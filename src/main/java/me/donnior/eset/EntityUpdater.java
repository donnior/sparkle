package me.donnior.eset;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import me.donnior.fava.Consumer;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;

public class EntityUpdater {

    public void updateAttribute(final Object user, final Map<String, String[]> params) {
        List<AccessableAttribute> attrs = getAccessableAttributes(user.getClass());
        FLists.create(attrs).each(new Consumer<AccessableAttribute>() {
            @Override
            public void apply(AccessableAttribute accessableAttribute) {
                accessableAttribute.update(user, params);
            }
        });
    }

    private List<AccessableAttribute> getAccessableAttributes(final Class<? extends Object> clz) {
        Field[] fields = clz.getDeclaredFields();
        return FLists.create(fields).findAll(new Predicate<Field>() {
            @Override
            public boolean apply(Field field) {
                return field.isAnnotationPresent(Accessable.class);
            }
        }).collect(new Function<Field, AccessableAttribute>() {
            @Override
            public AccessableAttribute apply(Field field) {
                return new AccessableAttribute(field, clz);
            }
        });
    }

}
