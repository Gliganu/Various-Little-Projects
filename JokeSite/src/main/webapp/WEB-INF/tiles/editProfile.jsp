<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h2>Edit your profile ( ${currentUsername } )</h2>

<ul>
	<li><a  href="<c:url value='/addNewJoke'/> "> Add a new Joke</a></li>
	<li><a  href="<c:url value='/seeMyJokes'/> "> See my jokes</a></li>
	<li><a  href="<c:url value='/seeMyRanking'/> "> See my ranking</a></li>

</ul>

<h2>Change Account Info</h2>

<ul>
	<li><a  href="<c:url value='/changePassword'/> "> Change password</a></li>
	<li><a  href="<c:url value='j_spring_security_logout'/> "> Log Out</a></li>
</ul>


<h2>Delete Account</h2>

<ul>
	<li><a  href="<c:url value='/deleteAccount'/> "> Delete Account</a></li>
</ul>