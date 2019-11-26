<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Классификация текста</title>
   </head>
   <body>

      <h2>Классификация</h2>
		<div>
			<form:form method = "POST" action = "/textMeaning" accept-charset="UTF-8">
			<div><form:label path = "input">Введите текст:</form:label></div>
			<form:textarea path = "input" name="myTextBox" cols="170" rows="30"/>
			<br>
			<input type = "submit" value = "Отправить"/>	
			</form:form>
			<form:form action="http://localhost:8080/search" method="GET">
				<input type="submit" value = "Вернуться к поиску"/>
			</form:form>					
		<div>
   </body>
</html>