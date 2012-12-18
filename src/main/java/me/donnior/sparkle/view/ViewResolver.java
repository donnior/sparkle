package me.donnior.sparkle.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

    void resovleView(String result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
