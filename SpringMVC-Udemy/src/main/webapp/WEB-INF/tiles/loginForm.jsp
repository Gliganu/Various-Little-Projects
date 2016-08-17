<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script type="text/javascript">
$(document).ready(function(){
	document.f.j_username.focus();
});


</script>
	<h3>Login with Username and Password, Belitule</h3>

	<c:url var="LOGIN_URL" value="/j_spring_security_check"></c:url>
	
	<c:if test="${param.error != null }">
		<p class="error"> Login Failed ! Username or Password was incorrect! </p>
	</c:if>

	<form name='f' action="${LOGIN_URL}" method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td>Remember me:</td>
				<td><input type='checkbox' name='_spring_security_remember_me' checked="checked"/></td>
			</tr>


			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
	
<br />

	<a href=<c:url value="/newAccount"/>>Click here to create new
	account</a>
