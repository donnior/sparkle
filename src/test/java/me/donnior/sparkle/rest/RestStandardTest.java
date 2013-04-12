package me.donnior.sparkle.rest;

import static org.junit.Assert.*;
import me.donnior.sparkle.HTTPMethod;

import org.junit.Test;

public class RestStandardTest {

    @Test
    public void test(){
        assertEquals("index", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.GET));
        assertEquals("save", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.POST));
        assertEquals("update", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.PUT));
        assertEquals("destroy", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.DELETE));
        assertEquals("options", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.OPTIONS));
        assertEquals("head", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.HEAD));
        assertEquals("trace", RestStandard.defaultActionMethodNameForHttpMethod(HTTPMethod.TRACE));
    }
    
}