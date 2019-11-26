<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Результаты классификации</title>
   </head>
   <body>

      <h2>Классификация текста</h2>
      <table>
         <tr>
            <td>Результаты классификации:</td>
            <td>${output}</td>
         </tr>
         <tr>
            <td>Исходный текст:</td>
            <td>${input}</td>
         </tr>		 
      </table> 
	<form:form action="http://localhost:8080/classifier" method="GET">
		<input type="submit" value = "Вернуться к классификации"/>
	</form:form>		
	<form:form action="http://localhost:8080/search" method="GET">
		<input type="submit" value = "Вернуться к поиску"/>
	</form:form>			  
   </body>
</html>