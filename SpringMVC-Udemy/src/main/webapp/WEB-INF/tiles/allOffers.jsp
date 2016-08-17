<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<table border=1 width=100%>
<tr><th>Name</th><th>Email</th><th>Offer</th></tr>

<tr>
<c:forEach var="offer" items="${offerList}">
	<td>${offer.user.name} </td>
	<td>${offer.user.email} </td> 
	<td>${offer.text} </td>
</c:forEach>
</tr>
</table>
