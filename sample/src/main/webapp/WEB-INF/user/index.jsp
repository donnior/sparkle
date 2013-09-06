<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <link rel="shortcut icon" href="#" />
  </head>
  <body>
    <h2>This is user page</h2>
    <ul>
      <c:forEach items="${users}" var="user">
        <li>
          ${user.name} - ${user.mail}
        </li>  
      </c:forEach>
    </ul>
    
  </body>
</html>
