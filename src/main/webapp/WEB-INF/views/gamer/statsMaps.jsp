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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        thead th {
            width: 33.33%;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            text-align: center;
            padding: 3px 0px !important;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            vertical-align: middle !important;
        }

        .map-image {
            width: 100%;
            height: auto;
        }

    </style>
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

                <th>Wins</th>
                <th>Losses</th>
                <th>Win%</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${gamerStatsList}" var="gamerStats">
            <tr>
                <td>${gamerStats.gamerName}</td>
                <c:forEach items="${mapOrder}" var="map">

                    <td>${gamerStats.mapWins[map]}</td>
                    <td>${gamerStats.mapLosses[map]}</td>
                    <td>${gamerStats.mapWinPercent[map]}%</td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
