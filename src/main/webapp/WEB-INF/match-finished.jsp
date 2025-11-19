<%--
  Created by IntelliJ IDEA.
  User: Evseenkov
  Date: 19.11.2025
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Матч завершен</title>
</head>
<body>
<h2>Матч завершён!</h2>

<h3>Победитель: ${matchScore.currentMatch.winner.name}</h3>

<table border="1" cellpadding="10">
  <tr>
    <th>Игрок</th>
    <th>Сеты</th>
  </tr>
  <tr>
    <td>${matchScore.currentMatch.player1.name}</td>
    <td>${matchScore.set1}</td>
  </tr>
  <tr>
    <td>${matchScore.currentMatch.player2.name}</td>
    <td>${matchScore.set2}</td>
  </tr>
</table>

<a href="/matches">Перейти к списку сыгранных матчей</a>
</body>
</html>
