<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/resources/js/disableSelected.js"></script>
</head>
<body>
<h1>Pick gamers</h1>
<form method="post">
    <c:forEach var="i" begin="1" end="10">
        <select name="gamers">
            <option value="empty" disabled selected></option>
            <c:forEach items="${gamers}" var="gamer" varStatus="status">
                <option name="gamers" value="${gamer.id}">${gamer.name}</option>
            </c:forEach>
        </select>
    </c:forEach>
    <select name="server">
        <option value="empty" disabled selected></option>
        <c:forEach items="${servers}" var="server" varStatus="status">
            <option name="servers" value="${server}">${server}</option>
        </c:forEach>
    </select>
    <input type="checkbox" name="teamsReady" value="true"/>
    <input type="submit" value="Submit"/>

</form>
<button onClick="javascript:location.href='stats/general'">Stats</button>
<button onClick="javascript:location.href='stats/maps'">MapStats</button>
<button onClick="javascript:location.href='stats/kills'">KillsStats</button>
<button onClick="javascript:location.href='stats/caps'">CapsStats</button>
<button onClick="javascript:location.href='stats/titans'">TitanStats</button>
</body>
</html>