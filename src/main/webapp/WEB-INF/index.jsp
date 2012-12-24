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
    <a href='<c:url value="/" />' >Home Page</a> <br />
    <a href='<c:url value="/projects" />' >Projects Page</a>  <br />
    <a href='<c:url value="/user/index" />' >Users Page</a>  <br />
    
    
    <br />
    
    <a href='<c:url value="/accounts/donnior" />' >account donnior Page</a>  <br />
    <a href='<c:url value="/accounts/javeer" />' >account javeer Page</a>  <br />
</body>
</html>