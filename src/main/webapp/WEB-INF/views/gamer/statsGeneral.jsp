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
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>MMR</th>
        <th>Server</th>
        <th>Last 10</th>
        <th>Most played Titan</th>
    </tr>
    <c:forEach var="gamer" items="${gamers}">
        <tr>
            <td><c:out value="${gamer.name}" /></td>
            <td><c:out value="${gamer.mmr}" /></td>
            <td><c:out value="${gamer.server}" /></td>
            <td><c:out value="${gamer.lastTen}" /></td>
            <td>
                <c:if test="${gamerTitans[gamer.id] != null}">
                    <c:out value="${gamerTitans[gamer.id]}" />
                </c:if>
                <c:if test="${gamerTitans[gamer.id] == null}">
                    -
                </c:if>
            </td>
            <td><button onClick="javascript:location.href='../nemesis/${gamer.id}'">Nemesis</button></td>
        </tr>
    </c:forEach>
</table>
<button onClick="javascript:location.href='../pickTeams'">Return</button>
</body>
</html>
