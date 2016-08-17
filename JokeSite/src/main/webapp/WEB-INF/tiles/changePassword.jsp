<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	
	<h3>Change Password</h3>
	
	<c:url var="CHANGE_PASSWORD_URL" value="/doChangePassword"></c:url>

	
	<form:form id="details" action="${CHANGE_PASSWORD_URL}" method="post"
		commandName="user">
		<table>
			<tr>
				<td>New Password:</td>
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
				<td><input value="Change password" type="submit" /></td>
			</tr>
		</table>
	</form:form>

