<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
   <head>
      <title>Результаты поиска</title>
   </head>
   <body>

      <h2>Результаты поиска:</h2>
		<c:forEach var="output" items="${output}">
			<c:set var = "input" value = "${fn:replace(output.getAbsolutePath(), '\\\\', '/')}" />
			<a href="/reportFetch?filePath=${input}&fileName=${output.getName()}">${output.getAbsolutePath()}</a><br>
		</c:forEach>
		<form:form action="http://localhost:8080/TextAnalizer-0.4/classifier" method="GET">
			<input type="submit" value = "Вернуться к классификации"/>
		</form:form>		
		<form:form action="http://localhost:8080/TextAnalizer-0.4/search" method="GET">
			<input type="submit" value = "Вернуться к поиску"/>
		</form:form>		
   </body>
</html>