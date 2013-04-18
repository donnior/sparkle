<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<style type="text/css">
    body {
      padding-top: 60px;
      padding-bottom: 40px;
    }
</style>
</head>
<body>
    <ul>
        <li><a href='<c:url value="/" />' >Home Page</a> - [/] </li>
        <li><a href='<c:url value="/projects" />' >Projects Index</a> - [/projects] </li>
        <li><a href='<c:url value="/projects?a=1&b=3&c=2" />' >Projects Index With Params</a> - [/projects?a=1&b=3&c=2] </li>
        <li><a href='<c:url value="/users/donnior/projects/1" />' >User's Project</a> - [/users/donnior/projects/1] </li>
        <li><a href='<c:url value="/projects/1234" />' >Project Show</a> - [/projects/1234] </li>
        <li><a href='<c:url value="/project/jsons" />' >Project Json With Map</a> - [/project/jsons] </li>
        <li><a href='<c:url value="/project/json" />' >Project Json With List</a> - [/project/json] </li>
        <li><a href='<c:url value="/user/index" />' >Users Page</a> - [/user/index] </li>
    </ul>
    
    
    <p>/projects/donnior with post</p>
    <form action='<c:url value="/projects/donnior" />' method="post">
        <input type="text" name="f1" />
        <input type="submit" />
    </form>
    
</body>
</html>