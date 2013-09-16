<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="r" uri="r-tag"%>

<r:stylesheet name="/static/pure-min.css"></r:stylesheet>
<r:stylesheet name="/static/app.css"></r:stylesheet>

<r:layout title="Sparkle Sample">

  <table class="pure-table pure-table-bordered">
    <thead><tr><th>Link</th><th>URI</th></tr></thead>
    <tbody>
        <tr><td><a href='<c:url value="/" />' >Home Page</a></td><td>/</td></tr>

        <tr><td><a href='<c:url value="/projects" />' >Projects Index</a></td><td>/projects</td></tr>

        <tr><td><r:a href="/projects">Projects Index</r:a></td><td>/projects</td></tr>

        <tr><td><a href='<c:url value="/projects?a=1&b=3&c=2" />' >Projects Index With Params</a></td><td>/projects?a=1&b=3&c=2</td></tr>
        
        <tr><td><a href='<c:url value="/users/donnior/projects/1" />' >User's Project</a></td><td>/users/donnior/projects/1</td></tr>

        <tr><td><a href='<c:url value="/projects/1234" />' >Project Show</a></td><td>/projects/1234</td></tr>
        
        <tr><td><a href='<c:url value="/project/jsons" />' >Project Json With Map</a></td><td>/project/jsons</td></tr>
        
        <tr><td><a href='<c:url value="/project/json" />' >Project Json With List</a></td><td>/project/json</td></tr>
        
        <tr><td><a href='<c:url value="/project/text" />' >Project Text</a></td><td>/project/text</td></tr>
        

        <tr><td><a href='<c:url value="/user/index" />' >Users Page</a></td><td>/user/index</td></tr>

    </tbody>
  </table>
    
    <form class="pure-form" action='<c:url value="/projects/donnior" />' method="post">
    <fieldset>
        <legend>/projects/donnior with post</legend>

        <input type="text" name="f1" placeholder="Anything" />
        <button type="submit" class="pure-button notice">Sign in</button>
    </fieldset>
    </form>


    <form class="pure-form" action='<c:url value="/users" />' method="post">
    <fieldset>
        <legend>new users</legend>

        <input type="text" name="name" placeholder="name" />
        <input type="text" name="mail" placeholder="mail" />
        <input type="text" name="age" placeholder="age" />
        <button type="submit" class="pure-button notice">New User</button>
    </fieldset>
    </form>

</r:layout>
