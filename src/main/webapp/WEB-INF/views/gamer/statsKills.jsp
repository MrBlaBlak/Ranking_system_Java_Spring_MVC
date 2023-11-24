<%--
  Created by IntelliJ IDEA.
  User: mrbla
  Date: 12.10.2023
  Time: 07:47
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
    <c:forEach items="${mapOrder}" var="map">
      <th>${map} Games</th>
      <th>${map} Best</th>
      <th>${map} Average</th>
    </c:forEach>
  </tr>
  <c:forEach items="${gamerStatsList}" var="gamerStats">
    <tr>
      <td>${gamerStats.gamerName}</td>
      <c:forEach items="${mapOrder}" var="map">
        <td>${gamerStats.mapTotalGames[map]}</td>
        <td>${gamerStats.mapBestKills[map]}</td>
        <td>${gamerStats.mapAverageKills[map]}</td>
      </c:forEach>
    </tr>
  </c:forEach>
</table>
<button onClick="javascript:location.href='/pickTeams'">Return</button>
</body>
</html>
