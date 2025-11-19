<%--
  Created by IntelliJ IDEA.
  User: Evseenkov
  Date: 19.11.2025
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Новый матч</title>
</head>
<body>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form action="new-match" method="post">
        Игрок 1: <input type="text" name="player1"><br>
        Игрок 2: <input type="text" name="player2"><br>
        <button type="submit">Начать матч</button>
    </form>
</body>
</html>
