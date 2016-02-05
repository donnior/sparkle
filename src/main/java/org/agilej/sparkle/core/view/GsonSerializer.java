package org.agilej.sparkle.core.view;

import com.google.gson.Gson;

public class GsonSerializer implements JSONSerializer {
    @Override
    public String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
