package me.donnior.sparkle.core.route.condition;

import com.google.common.base.Preconditions;
import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import me.donnior.sparkle.WebRequest;

import com.google.common.base.Joiner;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class AbstractCondition {

    protected String[] params;
    private FList<ConditionItem> conditionItems = FLists.newEmptyList();

    public AbstractCondition(String[] params) {
        checkArgument(params != null, "the params argument could not be null");

        this.params = params;
        parseParams(params);
    }
    
    private void parseParams(String[] params){
        for(String param : params){
            this.conditionItems.add(ConditionItemFactory.createItem(param));
        }
    }

    public boolean match(final WebRequest request) {
        return this.conditionItems.all(new Predicate<ConditionItem>() {
            @Override
            public boolean apply(ConditionItem item) {
                String real = getConditionValueFromRequest(request, item.getName());
                return item.match(real);
            }
        });
    }

    @Override
    public String toString() {
        return "\""+Joiner.on(",").join(this.params)+"\"";
    }

    public abstract String getConditionValueFromRequest(WebRequest request, String key);
    
}