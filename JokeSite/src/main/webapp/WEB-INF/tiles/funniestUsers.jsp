<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table class="normalTable" border=1 >
	<tr>
		<th>Username</th>
		<th>Rating</th>
		<th>Number of jokes</th>
		<th>See all his Jokes</th>
	</tr>

	<c:forEach var="user" items="${userList}">
		<tr>
			<td>${user.username} </td>
			<td>${user.rating}</td>
			<td>${jokesMap[user]}</td>
			<td><a
				href="<c:url value='/searchJokesByUser?uid=${user.username}'/>">${user.username}'s Jokes</a></td>
		</tr>
	</c:forEach>

</table>

