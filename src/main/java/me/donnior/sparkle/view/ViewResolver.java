package me.donnior.sparkle.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

    void resovleView(String result, HttpServletRequest request, HttpServletResponse response);

}
