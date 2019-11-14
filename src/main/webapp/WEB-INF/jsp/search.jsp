<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Поиск</title>
   </head>
   <body>

      <h2>Поиск</h2>
		<div>
			<form:form method = "POST" action = "/TextAnalizer-0.4/searchResult" accept-charset="UTF-8">
			<div><form:label path = "input">Введите текстовые данные для поиска по статьям:</form:label></div>
			<form:textarea path = "input" name="myTextBox" cols="50" rows="5"/>
			<br>
			<input type = "submit" value = "Отправить"/>			 
			</form:form>
			<form:form action="http://localhost:8080/TextAnalizer-0.4/classifier" method="GET">
				<input type="submit" value = "Вернуться к классификации"/>
			</form:form>	
		<div>
   </body>
</html>