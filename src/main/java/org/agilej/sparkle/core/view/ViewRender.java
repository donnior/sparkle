package org.agilej.sparkle.core.view;

import java.io.IOException;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.ActionMethod;

/**
 *  ViewRender takes charge of rendering response as views. Such as a page or a json response. 
 *  When you use Sparkle there will be three kinds view renders you can use: Built-in, vender provided and 
 *  developer created.
 *  
 *  <br /><br />
 *  
 *  <b>The Built-in view renders:</b>
 *  
 *  <li> JSONViewRender </li>
 *  <li> TextViewRender </li>
 *  
 *  <br /><br />
 *  
 *  <b>The Vender-provided view renders:</b><br /><br />
 *  
 *  Because Sparkle is designed to be running under several environments such as netty server or servlet container,
 *  these feature comes with the vender-provided view render, for example under servlet container you can use the 
 *  JSP view render to render a jsp page. 
 *  
 *  <br /><br />
 *  <li> JSPViewRender </li>
 *  
 *  <br /><br />
 *  
 *  <b>Developer created view renders:</b><br /><br />
 *  
 *  Sparkle allow developer to create third-part view renders, for example you can write a view render to render 
 *  a pdf file.
 *    
 *
 */
public interface ViewRender {

    boolean supportActionMethod(ActionMethod actionMethod, Object actionMethodResult);
    
    void renderView(Object result, Object controller, WebRequest request) throws IOException;

}
