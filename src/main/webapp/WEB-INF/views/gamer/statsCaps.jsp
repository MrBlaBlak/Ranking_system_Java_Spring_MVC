<%--
  Created by IntelliJ IDEA.
  User: mrbla
  Date: 14.11.2023
  Time: 14:57
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
        <c:forEach items="${mapOrder}" var="map">

            <th colspan="3" class="position-relative">
                <img src="../resources/images/maps/${map}.webp" alt="${map}" title="${map}" class="map-image">
            </th>
        </c:forEach>
    </tr>
    <tr>
        <th><button onClick="javascript:location.href='../pickTeams'" class="btn btn-secondary return-button">Return</button></th>
        <c:forEach items="${mapOrder}" var="map">

            <th>Games</th>
            <th>Best</th>
            <th>Average</th>
        </c:forEach>
    </tr>
    </thead>
    <c:forEach items="${gamerStatsList}" var="gamerStats">
        <tr>
            <td>${gamerStats.gamerName}</td>
            <c:forEach items="${mapOrder}" var="map">
                <td>${gamerStats.mapTotalGames[map]}</td>
                <td>${gamerStats.mapBestCaps[map]}</td>
                <td>${gamerStats.mapAverageCaps[map]}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
<button onClick="javascript:location.href='../pickTeams'">Return</button>
</body>
</html>
