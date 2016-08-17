<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


	<h3>Authorized users only!</h3>

	<table>
		<tr>
			<td>Username</td>
			<td>Email</td>
			<td>Role</td>
			<td>Enabled</td>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.username}</td>  
				<td>${user.email}</td>
				<td>${user.authority}</td>
				<td>${user.enabled}</td>
			</tr>
		</c:forEach>

	</table>

