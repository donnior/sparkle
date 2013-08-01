<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="r" uri="r-tag"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<r:yieldTitle></r:yieldTitle>
<r:yieldStylesheets></r:yieldStylesheets>
<r:yieldJavascripts />
</head>
<body>
   <%--  <div id="body">
        <div id="sidebar">

        </div>

        <div id="main">
            <div class="content" >
                
            </div>
        </div>
    </div> --%>
    <r:yieldBody></r:yieldBody>
</body>
</html>