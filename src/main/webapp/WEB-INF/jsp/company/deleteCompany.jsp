<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:import url="${contextPath}/WEB-INF/jsp/navigationbar.jsp"/>
        <form action="/company" method = "post">
            Customer id:
            <input type="number" name="companyid" min="1"> </br>
            </br>
            <input type = "submit" name="operation" value = "Delete">
            <input type = "reset" value = "Reset form">
            </br>
            </br>
            <c:if test="${not empty msg}">
                ${msg}
            </c:if>
        </form>
    </body>
</html>