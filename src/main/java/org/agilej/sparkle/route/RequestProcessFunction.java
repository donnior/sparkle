package org.agilej.sparkle.route;


import org.agilej.sparkle.WebRequest;
import org.agilej.jsonty.JSONModel;

//TODO not used, should delete
@Deprecated
public interface RequestProcessFunction {

    JSONModel process(WebRequest request);

}
