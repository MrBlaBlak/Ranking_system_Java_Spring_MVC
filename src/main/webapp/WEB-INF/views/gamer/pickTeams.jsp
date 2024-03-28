<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="resources/js/disableSelected.js"></script>
    <script src="resources/js/validatePickTeams.js"></script>
    <script src="resources/js/selectRandomPlayersAndServer.js"></script>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
        }
        .btn-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="btn-group d-flex justify-content-center" role="group">
        <button type="button" class="btn btn-secondary mr-2" onClick="javascript:location.href='stats/general'">Stats</button>
        <button type="button" class="btn btn-secondary mr-2" onClick="javascript:location.href='stats/maps'">MapStats</button>
        <button type="button" class="btn btn-secondary mr-2" onClick="javascript:location.href='stats/kills'">KillsStats</button>
        <button type="button" class="btn btn-secondary mr-2" onClick="javascript:location.href='stats/caps'">CapsStats</button>
        <button type="button" class="btn btn-secondary mr-2" onClick="javascript:location.href='stats/titans'">TitanStats</button>
        <button type="button" class="btn btn-primary mr-2" onclick="randomlySelectPlayersAndServer()">SelectRandomPlayers</button>
    </div>
    <h1 class="text-center mb-4">Pick Gamers</h1>
    <form:form method="post" modelAttribute="gamersDTO" onsubmit="return validateForm()">
        <div class="row">
            <div class="col-md-6">
                <c:forEach var="i" begin="1" end="5">
                    <select name="gamersList" id="player${i}" class="form-control mb-3">
                        <option value="empty" disabled selected="true">-- Player${i} --</option>
                        <c:forEach items="${gamers}" var="gamer">
                            <option value="${gamer.id}">${gamer.name}</option>
                        </c:forEach>
                    </select>
                </c:forEach>
            </div>
            <div class="col-md-6">
                <c:forEach var="i" begin="6" end="10">
                    <select name="gamersList" id="player${i}" class="form-control mb-3">
                        <option value="empty" disabled selected="true">-- Player${i} --</option>
                        <c:forEach items="${gamers}" var="gamer">
                            <option value="${gamer.id}">${gamer.name}</option>
                        </c:forEach>
                    </select>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <form:select path="server" class="form-control mb-3">
                <option value="empty" disabled selected="true">-- Server --</option>
                <c:forEach items="${servers}" var="server" varStatus="status">
                    <option value="${server}">${server}</option>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-group">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="teamsReady" path="teamsReady">
                <label class="form-check-label" for="teamsReady">Pick Manually</label>
            </div>
        </div>
        <input type="submit" value="Submit" class="btn btn-primary mb-3">
    </form:form>

</div>
</body>
</html>