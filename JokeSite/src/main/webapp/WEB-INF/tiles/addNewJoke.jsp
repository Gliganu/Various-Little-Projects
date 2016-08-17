<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url var="CREATE_JOKE_URL" value="/createJoke"></c:url>

<form:form action="${CREATE_JOKE_URL}" method="post"
	commandName="joke">
	<form:input type="hidden" name="id" path="id" />
	<table>
		<tr>
			<td>Title:</td>
			<td><form:input type="text" name="title" path="title"/></td>
			<td><form:errors path="title" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td>Content:</td>
			<td><form:textarea name="content" path="content" rows="10" cols="50" /></td>
			<td><form:errors path="content" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td>Category:</td>
			<td><form:input type="text" name="cathegory" path="cathegory"/></td>
			<td><form:errors path="cathegory" cssClass="error" /></td>
		</tr>
		
		<tr><td></td><tr>
			<td><input value="Save joke" type="submit" /></td>
		</tr>

	</table>
</form:form>

