<%--
  Created by IntelliJ IDEA.
  User: mrbla
  Date: 10.10.2023
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>General Stats</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        table {
            width: 100%;
        }
        th, td {
            text-align: center;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .titan-image {
            width: 50px;
            height: auto;
        }
        .badge-image {
            width: 50px;
            height: auto;
        }
        .return-button {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center mb-4">Player List</h2>
    <!-- Tabela z graczami -->
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th>Badge</th>
            <th>Name</th>
            <th>MMR</th>
            <th>Server</th>
            <th>Last 10</th>
            <th>Most played Titan</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="gamer" items="${gamers}">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${gamer.mmr > 620.1}">
                            <img src="../resources/images/badges/Apex_Predator_Badge.png" alt="Apex Predator" title="Predator" class="badge-image">
                        </c:when>
                        <c:when test="${gamer.mmr > 610.1 && gamer.mmr < 620.1}">
                            <img src="../resources/images/badges/Masters_Badge.png" alt="Masters" title="Masters" class="badge-image">
                        </c:when>
                        <c:when test="${gamer.mmr > 600.1 && gamer.mmr < 610.1}">
                            <img src="../resources/images/badges/Diamond_Badge.png" alt="Diamond" title="Diamond" class="badge-image">
                        </c:when>
                        <c:when test="${gamer.mmr > 590.1 && gamer.mmr < 600.1}">
                            <img src="../resources/images/badges/Platinum_Badge.png" alt="Platinum" title="Platinum" class="badge-image">
                        </c:when>
                        <c:when test="${gamer.mmr > 580.1 && gamer.mmr < 590.1}">
                            <img src="../resources/images/badges/Gold_Badge.png" alt="Gold" title="Gold" class="badge-image">
                        </c:when>
                        <c:when test="${gamer.mmr > 570.1 && gamer.mmr < 580.1}">
                            <img src="../resources/images/badges/Silver_Badge.png" alt="Silver" title="Silver" class="badge-image">
                        </c:when>
                        <c:otherwise>
                            <img src="../resources/images/badges/Bronze_Badge.png" alt="Bronze" title="Bronze" class="badge-image">
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${gamer.name}" /></td>
                <td><c:out value="${gamer.mmr}" /></td>
                <td><c:out value="${gamer.server}" /></td>
                <td><c:out value="${gamer.lastTen}" /></td>
                <td>
                    <c:set var="titanImageName" value="${gamerTitans[gamer.id]}" />
                    <c:choose>
                        <c:when test="${not empty titanImageName}">
                            <img src="../resources/images/titans/${titanImageName}.png" alt="${titanImageName}" class="titan-image">
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><a href="../nemesis/${gamer.id}" class="btn btn-primary">Nemesis</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- Przycisk powrotu -->
    <button onClick="javascript:location.href='../pickTeams'" class="btn btn-secondary return-button">Return</button>
</div>
</body>
</html>
