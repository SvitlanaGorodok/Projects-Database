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
        <form action = "/project" method = "get">
            Project id:  <br/>
            </br>
            <input type="number" name="projectid" min = "1">
            <input type = "submit" name="operation" value = "Find by id"> </br>
            </br>
            Project name:  <br/>
            </br>
            <input type="text" name="projectname">
            <input type = "submit" name="operation" value = "Find by name"> </br>
            </br>
            <input type = "submit" name="operation" value = "Show all">
            <input type = "reset" value = "Reset form">
            </br>
            </br>
            <table style="text-align: center" border="1" width="40%" height="40%">
                <thead>
                    <c:if test="${not empty projects}">
                        <tr>
                            <td>Id</td>
                            <td>Name</td>
                            <td>Description</td>
                            <td>Start date</td>
                        </tr>
                    </c:if>
                </thead>
                <tbody>
                    <c:forEach var = "project" items="${projects}">
                        <tr>
                            <td>
                                <c:out value="${project.id}"/>
                            </td>
                            <td>
                                <c:out value="${project.name}"/>
                            </td>
                            <td>
                                <c:out value="${project.description}"/>
                            </td>
                            <td>
                                <c:out value="${project.startDate}"/>
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