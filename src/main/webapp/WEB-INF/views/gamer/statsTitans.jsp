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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../resources/css/statsPages.css">
</head>
<body>
<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th>Gamer Name</th>
        <c:forEach items="${titanOrder}" var="titan">

            <th colspan="3" class="position-relative">
                <img src="../resources/images/titans/${titan}.png" alt="${titan}" title="${titan}" class="titan-image">
            </th>
        </c:forEach>
    </tr>
    <tr>
        <th><button onClick="javascript:location.href='../pickTeams'" class="btn btn-secondary return-button">Return</button></th>
        <c:forEach items="${titanOrder}" var="titan">
            <th>Wins</th>
            <th>Losses</th>
            <th>WinRate</th>
        </c:forEach>
    </tr>
    </thead>
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
<button onClick="javascript:location.href='../pickTeams'">Return</button>
</body>
</html>
