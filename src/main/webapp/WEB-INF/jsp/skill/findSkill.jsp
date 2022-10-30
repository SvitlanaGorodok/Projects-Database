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
        <form action = "/skill" method = "get">
            Skill id:  <br/>
            </br>
            <input type="number" name="skillid" min = "1">
            <input type = "submit" name="operation" value = "Find by id"> </br>
            </br>
            Skill area:  <br/>
            </br>
            <input type="text" name="skillarea">
            <input type = "submit" name="operation" value = "Find by area"> </br>
            </br>
            <input type = "submit" name="operation" value = "Show all">
            <input type = "reset" value = "Reset form">
            </br>
            </br>
            <table style="text-align: center" border="1" width="30%" height="30%">
                <thead>
                    <c:if test="${not empty skills}">
                        <tr>
                            <td>Id</td>
                            <td>Area</td>
                            <td>Level</td>
                        </tr>
                    </c:if>
                </thead>
                <tbody>
                    <c:forEach var = "skill" items="${skills}">
                        <tr>
                            <td>
                                <c:out value="${skill.id}"/>
                            </td>
                            <td>
                                <c:out value="${skill.area}"/>
                            </td>
                            <td>
                                <c:out value="${skill.level}"/>
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