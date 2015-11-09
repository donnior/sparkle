package org.agilej.sparkle.route;


import org.agilej.sparkle.WebRequest;
import org.agilej.jsonty.JSONModel;

public interface RequestProcessFunction {

    JSONModel process(WebRequest request);

}
