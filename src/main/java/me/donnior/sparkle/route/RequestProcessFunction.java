package me.donnior.sparkle.route;


import me.donnior.sparkle.WebRequest;
import org.agilej.jsonty.JSONModel;

public interface RequestProcessFunction {

    JSONModel process(WebRequest request);

}
