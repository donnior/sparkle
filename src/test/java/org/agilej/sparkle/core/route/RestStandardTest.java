package org.agilej.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import org.agilej.sparkle.HTTPMethod;

import org.junit.Test;

public class RestStandardTest extends RestStandard{

    @Test
    public void test(){
        assertEquals("index", defaultActionMethodNameForHttpMethod(HTTPMethod.GET));
        assertEquals("save", defaultActionMethodNameForHttpMethod(HTTPMethod.POST));
        assertEquals("update", defaultActionMethodNameForHttpMethod(HTTPMethod.PUT));
        assertEquals("destroy", defaultActionMethodNameForHttpMethod(HTTPMethod.DELETE));
        assertEquals("options", defaultActionMethodNameForHttpMethod(HTTPMethod.OPTIONS));
        assertEquals("head", defaultActionMethodNameForHttpMethod(HTTPMethod.HEAD));
        assertEquals("trace", defaultActionMethodNameForHttpMethod(HTTPMethod.TRACE));
    }
    
}
