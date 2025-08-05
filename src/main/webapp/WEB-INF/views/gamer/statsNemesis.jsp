<%--
  Created by IntelliJ IDEA.
  User: mrbla
  Date: 11.11.2023
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Nemesis Stats</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .stats-column {
            width: 50%;
            float: left;
        }
        .return-button {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<c:if test="${not empty nemesisStatsList}">
    <div class="container">
        <div class="stats-column">
            <h2>${name} Nemesis Stats</h2>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Wins</th>
                    <th>Losses</th>
                    <th>Win Percentage</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="nemesisStats" items="${nemesisStatsList}">
                    <tr>
                        <td>${nemesisStats.name}</td>
                        <td>${nemesisStats.wins}</td>
                        <td>${nemesisStats.losses}</td>
                        <td>${nemesisStats.winPercentage}%</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${not empty wingmanStatsList}">
    <div class="container">
        <div class="stats-column">
            <h2>${name} Wingman Stats</h2>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Wins</th>
                    <th>Losses</th>
                    <th>Win Percentage</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="wingmanStats" items="${wingmanStatsList}">
                    <tr>
                        <td>${wingmanStats.name}</td>
                        <td>${wingmanStats.wins}</td>
                        <td>${wingmanStats.losses}</td>
                        <td>${wingmanStats.winPercentage}%</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
<div class="container">
    <button onClick="javascript:location.href='../stats/general'" class="btn btn-secondary return-button">Return
    </button>
</div>

</body>
</html>
