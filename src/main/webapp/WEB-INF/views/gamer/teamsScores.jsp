<%--
  Created by IntelliJ IDEA.
  User: Michał
  Date: 07.10.2023
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/teamsScores-styles.css">
    <script src="resources/js/randomData.js"></script>
    <script src="resources/js/enableSDWinner.js"></script>
    <script src="resources/js/pasteImage.js"></script>
</head>
<body>
<%--below script to fill scores with random data--%>

<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <form:form method="post" action="${pageContext.request.contextPath}/updateScores"
                       modelAttribute="gamersMatchStatsDTO">
                <div class="form-group">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Team_1</th>
                            <th>MMR</th>
                            <th>Handicap</th>
                            <th>Eliminations</th>
                            <th>Flags</th>
                            <th>Titan</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${team1}" var="gamer" varStatus="i">
                            <tr>
                                <td><c:out value="${gamer.name}"/></td>
                                <td><c:out value="${gamer.mmr}"/></td>
                                <td><c:out value="${-gamer.serverHandicap(server)}"/></td>
                                <td><input type="number" name="team1elims" class="form-control"
                                           placeholder="Elims count"
                                           required="required"/></td>
                                <td><input type="number" name="team1flags" class="form-control"
                                           placeholder="Flags count" min="0" max="6"
                                           required="required"/></td>
                                <td>
                                    <select name="team1titans" class="form-control" required="required">
                                        <option value="empty" disabled selected="true">-- Titan --</option>
                                        <option value="ion">Ion</option>
                                        <option value="tone">Tone</option>
                                        <option value="monarch">Monarch</option>
                                        <option value="northstar">Northstar</option>
                                        <option value="ronin">Ronin</option>
                                        <option value="legion">Legion</option>
                                        <option value="scorch">Scorch</option>
                                    </select>
                                </td>
                                <td><input type="hidden" name="team1gamersId" value="${gamer.id}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Team_2</th>
                            <th>MMR</th>
                            <th>Handicap</th>
                            <th>Eliminations</th>
                            <th>Flags</th>
                            <th>Titan</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${team2}" var="gamer" varStatus="i">
                            <tr>
                                <td><c:out value="${gamer.name}"/></td>
                                <td><c:out value="${gamer.mmr}"/></td>
                                <td><c:out value="${-gamer.serverHandicap(server)}"/></td>
                                <td><input type="number" name="team2elims" class="form-control"
                                           placeholder="Elims count"
                                           required="required"/></td>
                                <td><input type="number" name="team2flags" class="form-control"
                                           placeholder="Flags count" min="0" max="6"
                                           required="required"/></td>
                                <td>
                                    <select name="team2titans" class="form-control" required="required">
                                        <option value="empty" disabled selected="true">-- Titan --</option>
                                        <option value="ion">Ion</option>
                                        <option value="tone">Tone</option>
                                        <option value="monarch">Monarch</option>
                                        <option value="northstar">Northstar</option>
                                        <option value="ronin">Ronin</option>
                                        <option value="legion">Legion</option>
                                        <option value="scorch">Scorch</option>
                                    </select>
                                </td>
                                <td><input type="hidden" name="team2gamersId" value="${gamer.id}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Map</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <form:select id="map" path="mapPlayed" class="form-control" required="required">
                                    <option value="empty" disabled selected="true">-- Map --</option>
                                    <option value="boomtown">boomtown</option>
                                    <option value="exo">exo</option>
                                    <option value="eden">eden</option>
                                    <option value="drydock">drydock</option>
                                    <option value="angel">angel</option>
                                    <option value="colony">colony</option>
                                    <option value="glitch">glitch</option>
                                </form:select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Sudden Death</th>
                            <th>Team 1 Win</th>
                            <th>Team 2 Win</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><form:checkbox id="suddenDeath" path="suddenDeath"/> Sudden Death</td>
                            <td><form:radiobutton path="suddenDeathWhoWon" id="team1WinRadio" value="team1"
                                                  disabled="true"/>
                                Team1 Win
                            </td>
                            <td><form:radiobutton path="suddenDeathWhoWon" id="team2WinRadio" value="team2"
                                                  disabled="true"/>
                                Team2 Win
                            </td>
                            <td><form:input type="hidden" path="server" class="form-control" value="${server}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <input type="submit" class="btn btn-primary" value="Submit"/>
                <button class="btn btn-secondary" onClick="javascript:location.href='pickTeams'">Return</button>
                <button type="button" class="btn btn-primary" onclick="getRandomData()">SetRandomMatchResult</button>
            </form:form>
        </div>
        <div class="col-lg-4">
            <div id="imageDropArea" onpaste="paste(event)" class="mt-4">
                You can paste image with scores here for easier reading
            </div>
            <img id="previewImage" src="#" alt="Preview" style="display: none; padding-top: 10px;">
        </div>
    </div>
</div>

</body>
</html>