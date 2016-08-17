
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<p>

<div id="topMenuDiv">
			<ul>
				<li><a class="linkList" href= <c:url value="/"/> >All Jokes </a></li>
				<li><a class="linkList" href= <c:url value="/searchJokesByDate"/> >Newest Jokes </a></li>
				<li><a class="linkList" href= <c:url value="/searchJokesByRating"/> >Jokes By Rating </a></li>
				<li><a class="linkList" href= <c:url value="/allCategories"/> >Jokes By Category </a></li>
				<li><a class="linkList" href= <c:url value="/funniestUsers"/> >Funniest Users </a></li>
			</ul>
</div>
	
 
<sec:authorize access="!isAuthenticated()"> 
	<a class="login" href=<c:url value="/login"/>>Log in</a>
	<br />
	<br />
</sec:authorize>


<sec:authorize access="isAuthenticated()">
	<a class="login" href=<c:url value="/editProfile"/>>Edit profile</a>
	<br />
	<br />
</sec:authorize>

