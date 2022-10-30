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
        <form action = "/developer" method = "get">
            Developer id:  <br/>
            </br>
            <input type="number" name="devid" min = "1">
            <input type = "submit" name="operation" value = "Find by id"> </br>
            </br>
            Developer name:  <br/>
            </br>
            <input type="text" name="devname">
            <input type = "submit" name="operation" value = "Find by name"> </br>
            </br>
            <input type = "submit" name="operation" value = "Show all">
            <input type = "reset" value = "Reset form">
            </br>
            </br>
            <table style="text-align: center" border="1" width="30%" height="30%">
                <thead>
                    <c:if test="${not empty developers}">
                        <tr>
                            <td>Id</td>
                            <td>First name</td>
                            <td>Last name</td>
                            <td>Age</td>
                            <td>Salary</td>
                        </tr>
                    </c:if>
                </thead>
                <tbody>
                    <c:forEach var = "developer" items="${developers}">
                        <tr>
                            <td>
                                <c:out value="${developer.id}"/>
                            </td>
                            <td>
                                <c:out value="${developer.firstName}"/>
                            </td>
                            <td>
                                <c:out value="${developer.lastName}"/>
                            </td>
                            <td>
                                <c:out value="${developer.age}"/>
                            </td>
                            <td>
                                <c:out value="${developer.salary}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty msg}">
                ${msg}
            </c:if>
        </form>
    </body>
</html>