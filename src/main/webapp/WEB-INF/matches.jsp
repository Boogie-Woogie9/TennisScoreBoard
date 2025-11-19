<%--
  Created by IntelliJ IDEA.
  User: Evseenkov
  Date: 19.11.2025
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Match" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.MatchesPage" %>

<%
  var model = (MatchesPage) request.getAttribute("model");
  List<Match> matches = model.matches();
%>
<html>
<head>
    <title>Список матчей</title>
</head>
<body>
<h1>Finished Matches</h1>

<!-- Форма фильтра -->
<form method="get" action="/matches">
  <input type="text" name="filter_by_player_name"
         placeholder="Filter by player"
         value="<%= model.filter() == null ? "" : model.filter() %>">
  <button type="submit">Search</button>
</form>

<br/>

<!-- Таблица -->
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Player 1</th>
    <th>Player 2</th>
    <th>Winner</th>
    <th>Score</th>
  </tr>

  <% for (Match m : matches) { %>
  <tr>
    <td><%= m.getId() %></td>
    <td><%= m.getPlayer1().getName() %></td>
    <td><%= m.getPlayer2().getName() %></td>
    <td><%= m.getWinner() != null ? m.getWinner().getName() : "" %></td>
    <%--здесь можно еще как-то вывести счет матча--%>
  </tr>
  <% } %>
</table>

<br/>

<!-- Пагинация -->
<div>
  Pages:
  <% for (int p = 1; p <= model.totalPages(); p++) { %>
  <% if (p == model.page()) { %>
  <b><%= p %></b>
  <% } else { %>
  <a href="/matches?page=<%= p %><%= model.filter() != null ? "&filter_by_player_name=" + model.filter() : "" %>">
    <%= p %>
  </a>
  <% } %>
  <% } %>
</div>
</body>
</html>
