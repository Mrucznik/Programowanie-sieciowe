<%--
  Created by IntelliJ IDEA.
  User: Mrucznik
  Date: 01.06.2018
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<div>
    <form action="servlet" method="post">
        <label for="x">X: </label><input id="x" type="text" name="x">
        <label for="y">Y: </label><input id="y" type="text" name="y">
        <input type="submit" value="Submit">
    </form>
</div>
    <h4><a href="servlet">Click here to go to the serverlet</a></h4>
</body>
</html>
