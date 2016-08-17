<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<ol>
	<c:forEach var="category" items="${categorySet}">
		<li><a
			href="<c:url value='/searchJokesByCategory?category=${category}'/>">${category}</a></li>
	</c:forEach>
</ol>



