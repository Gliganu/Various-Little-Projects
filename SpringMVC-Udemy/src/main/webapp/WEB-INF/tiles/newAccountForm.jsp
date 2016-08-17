<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	
	<c:url var="CREATE_ACCOUNT_URL" value="/createAccount"></c:url>

	
	
	<form:form id="details" action="${CREATE_ACCOUNT_URL}" method="post"
		commandName="user">
		<table>
			<tr>
				<td>Username:</td>
				<td><form:input name="username" path="username" type="text" /></td>
				<td><form:errors path="username" cssClass="error" /></td>

			</tr>
			<tr>
				<td>Name:</td>
				<td><form:input name="name" path="name" type="text" /></td>
				<td><form:errors path="name" cssClass="error" /></td>

			</tr>
			<tr>
				<td>Email:</td>
				<td><form:input name="email" path="email" type="text" /></td>
				<td><form:errors path="email" cssClass="error" /></td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><form:input id="password" name="password" path="password"
						type="password" /></td>
				<td><form:errors path="password" cssClass="error" /></td>
			</tr>

			<tr>
				<td>Confirm Password:</td>
				<td><input id="confirmpass" name="confirmpass" type="password" /></td>
				<td><p id="matchpass"></p></td>
				<td></td>
			</tr>


			<tr>
				<td><input value="Create new user" type="submit" /></td>
			</tr>
		</table>
	</form:form>

