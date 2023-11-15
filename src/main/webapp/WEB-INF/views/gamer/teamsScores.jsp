<%--
  Created by IntelliJ IDEA.
  User: Michał
  Date: 07.10.2023
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #formContainer {
            display: flex;
        }

        #formContent {
            flex: 1;
        }

        #imageDropArea {
            border: 2px dashed #ccc;
            padding: 20px;
            text-align: center;
            cursor: pointer;
            margin-left: 20px;
            width: 200px; /* Adjust the width as needed */
        }

        #previewImage {
            max-width: 100%;
            max-height: 500px;
            margin-top: 20px;
        }
    </style>

</head>
<body>
<script src="${pageContext.request.contextPath}/resources/js/randomData.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/enableSDWinner.js"></script>
<div id="formContainer">
    <div id="formContent">
        <form method="post" action="${pageContext.request.contextPath}/updateScores">
            <table>
                <tr>
                    <td>Team1</td>
                </tr>

                <c:forEach items="${team1}" var="gamer">
                    <tr>
                        <td><c:out value="${gamer.id}"/></td>
                        <td><c:out value="${gamer.name}"/></td>
                        <td><c:out value="${gamer.mmr}"/></td>
                        <td><input type="number" name="team1eliminacjeId" placeholder="Ilość eliminacji"
                                   required="required"/></td>
                        <td><input type="number" name="team1flagiId" placeholder="Ilość zdobytych flag" min="0" max="6"
                                   required="required"/></td>
                        <td>
                            <select name="team1tytanId" required="required">
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

                <tr>
                    <td>Team2</td>
                </tr>
                <c:forEach items="${team2}" var="gamer">
                    <tr>
                        <td><c:out value="${gamer.id}"/></td>
                        <td><c:out value="${gamer.name}"/></td>
                        <td><c:out value="${gamer.mmr}"/></td>
                        <td><input type="number" name="team2eliminacjeId" placeholder="Ilość eliminacji"
                                   required="required"/></td>
                        <td><input type="number" name="team2flagiId" placeholder="Ilość zdobytych flag" min="0" max="6"
                                   required="required"/></td>
                        <td>
                            <select name="team2tytanId" required="required">
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
                <tr>
                    <td>
                        <select name="map" required="required">
                            <option value="boomtown">boomtown</option>
                            <option value="exo">exo</option>
                            <option value="eden">eden</option>
                            <option value="drydock">drydock</option>
                            <option value="angel">angel</option>
                            <option value="colony">colony</option>
                            <option value="glitch">glitch</option>
                        </select>
                    </td>
                </tr>
                <td><input type="checkbox" id="suddenDeath" name="suddendeath" value="true"/> Sudden Death</td>
                <td><input type="radio" name="teamSDWinner" id="team1WinRadio" value="team1" disabled/> Team1 Win</td>
                <td><input type="radio" name="teamSDWinner" id="team2WinRadio" value="team2" disabled/> Team2 Win</td>
                <td><input type="hidden" name="server" value="${server}"/></td>

                </tr>
            </table>
            <input type="submit" value="Submit"/>
        </form>

        <button onClick="javascript:location.href='/'">Return</button>
    </div>
    <div id="imageDropArea" ondrop="drop(event)" ondragover="allowDrop(event)" onpaste="paste(event)">
    Drop image here or click to select
</div>

    <!-- Image preview area -->
    <img id="previewImage" src="#" alt="Preview" style="display: none;">
</div>
<script>
    function allowDrop(event) {
        event.preventDefault();
        document.getElementById("imageDropArea").style.border = "2px dashed #aaa";
    }

    function drop(event) {
        event.preventDefault();
        document.getElementById("imageDropArea").style.border = "2px dashed #ccc";

        handleImage(event.dataTransfer.files[0]);
    }

    function paste(event) {
        var items = (event.clipboardData || event.originalEvent.clipboardData).items;

        for (let index in items) {
            let item = items[index];
            if (item.kind === 'file') {
                let blob = item.getAsFile();
                handleImage(blob);
            }
        }
    }

    function handleImage(file) {
        let reader = new FileReader();

        reader.onload = function (e) {
            document.getElementById("previewImage").src = e.target.result;
            document.getElementById("previewImage").style.display = "block";
        };

        reader.readAsDataURL(file);
    }
</script>
</body>
</html>
