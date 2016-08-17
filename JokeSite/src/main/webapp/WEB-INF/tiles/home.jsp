<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>




<sec:authorize access="isAuthenticated()">
	<a href=<c:url value="/allMessages "/>>Messages(<span
		id="numberMessages">0</span>)
	</a>
	&nbsp;
</sec:authorize>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<a href=<c:url value="/admin "/>>Edit Offers</a>
</sec:authorize>


<br />
<br />

<table class="normalTable" border=1>
	<tr>
		<th>Title</th>
		<th>Content</th>
		<th>Category</th>
		<th>Rating</th>
		<th>Author</th>
	</tr>

	<c:forEach var="joke" items="${jokeList}">
		<tr>
			<td>${joke.title}</td>
			<td>${joke.content}
			<a class="linkList" href="<c:url value='/downVote?id=${joke.id}'/>"> 
			<img
				src="${pageContext.request.contextPath}/static/images/dislike.png"
				alt="Like" height="20" width="20" align="right"> 
			</a> 
			
			<a class="linkList" href="<c:url value='/upVote?id=${joke.id}'/>"> 
				<img
				src="${pageContext.request.contextPath}/static/images/like.png"
				alt="Like" height="20" width="20" align="right"> 
			</a>

			</td>
			<td>${joke.cathegory}</td>
			<td>${joke.rating}</td>
			<td>${joke.user.username }<a
				href="<c:url value='/message?uid=${joke.user.username}'/>">(Contact)</a></td>
		</tr>
	</c:forEach>

</table>








