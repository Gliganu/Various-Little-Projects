<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<h2>Send Message</h2>


<form:form method="post" commandName="message">
	<input type="hidden" name="flowExecutionKey" value="${flowExecutionKey}" />	
	<input type="hidden" name="_eventId" value="send" />	

	<table>
		<tr>
			<td>Your name:</td>
			<td><form:input name="name" path="name" type="text" value="${fromUser.name }" /></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Your email:</td>
			<td><form:input name="email" path="email" type="text"  value="${fromUser.email}"/></td>
			<td><form:errors path="email" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Subject:</td>
			<td><form:input name="subject" path="subject" type="text" /></td>
			<td><form:errors path="subject" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Your Message:</td>
			<td><form:textarea name="content" path="content" row="30" /></td>
			<td><form:errors path="content" cssClass="error" /></td>
		</tr>
		<tr>
			<td><input value="Send" type="submit" /></td>
		</tr>
	</table>
</form:form>

