<%--
  Created by IntelliJ IDEA.
  User: Evseenkov
  Date: 19.11.2025
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Счет матча</title>
</head>
<body>
<c:if test="${matchScore == null}">
  <p>MatchScore is null!</p>
</c:if>
<h2>Матч: ${matchScore.currentMatch.player1.name} vs ${matchScore.currentMatch.player2.name}</h2>

<table>
  <tr>
    <th>Игрок</th>
    <th>Очки</th>
    <th>Геймы</th>
    <th>Сеты</th>
  </tr>
  <tr>
    <td>${matchScore.currentMatch.player1.name}</td>
    <td>${matchScore.score1}</td>
    <td>${matchScore.games1}</td>
    <td>${matchScore.set1}</td>
  </tr>
  <tr>
    <td>${matchScore.currentMatch.player2.name}</td>
    <td>${matchScore.score2}</td>
    <td>${matchScore.games2}</td>
    <td>${matchScore.set2}</td>
  </tr>
</table>

<form action="match-score" method="post">
  <input type="hidden" name="uuid" value="${uuid}">
  <button name="player" value="1">Игрок 1: выиграл очко</button>
  <button name="player" value="2">Игрок 2: выиграл очко</button>
</form>
</body>
</html>
