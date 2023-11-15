<%--
  Created by IntelliJ IDEA.
  User: mrbla
  Date: 11.11.2023
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Stats Maps</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Gamer Name</th>
        <c:forEach items="${titanOrder}" var="titan">
            <th>${titan} Wins</th>
            <th>${titan} Losses</th>
            <th>${titan} Win%</th>
        </c:forEach>
    </tr>
    <c:forEach items="${gamerStatsList}" var="gamerStats">
        <tr>
            <td>${gamerStats.gamerName}</td>
            <c:forEach items="${titanOrder}" var="titan">
                <td>${gamerStats.titanWins[titan]}</td>
                <td>${gamerStats.titanLosses[titan]}</td>
                <td>${gamerStats.titanWinPercent[titan]}%</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
<button onClick="javascript:location.href='/'">Return</button>
</body>
</html>
