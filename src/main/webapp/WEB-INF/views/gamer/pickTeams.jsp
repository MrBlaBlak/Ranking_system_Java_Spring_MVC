<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="resources/js/disableSelected.js"></script>
    <script src="resources/js/validatePickTeams.js"></script>
</head>
<body>
<h1>Pick gamers</h1>

<form:form method="post" modelAttribute="gamersDTO" onsubmit="return validateForm()">
    <c:forEach var="i" begin="1" end="10">
        <select name="gamersList" id="player${i}">
            <option value="empty" disabled selected="true">-- Player${i} --</option>
            <c:forEach items="${gamers}" var="gamer">
                <option value="${gamer.id}">${gamer.name}</option>
            </c:forEach>
        </select>
    </c:forEach>
    <form:select path="server">
        <option value="empty" disabled selected="true">-- Server --</option>
        <c:forEach items="${servers}" var="server" varStatus="status">
            <option value="${server}">${server}</option>
        </c:forEach>
    </form:select>
    <label for="teamsReady">Pick Manually</label>
    <form:checkbox path="teamsReady" id="teamsReady"/>
    <input type="submit" value="Submit"/>
</form:form>

<button onClick="javascript:location.href='stats/general'">Stats</button>
<button onClick="javascript:location.href='stats/maps'">MapStats</button>
<button onClick="javascript:location.href='stats/kills'">KillsStats</button>
<button onClick="javascript:location.href='stats/caps'">CapsStats</button>
<button onClick="javascript:location.href='stats/titans'">TitanStats</button>

</body>
</html>