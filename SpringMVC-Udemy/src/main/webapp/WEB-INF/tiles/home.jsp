<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:choose>
	<c:when test="${hasOffer}">
		<a href=<c:url value="/addOffer"/>> Edit/Delete your current offer
		</a>
	</c:when>

	<c:otherwise>
		<a href=<c:url value="/addOffer"/>>Click here to add new offer </a>
	</c:otherwise>

</c:choose>

&nbsp;


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<a href=<c:url value="/admin "/>>Admin Page</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
	<a href=<c:url value="/allMessages "/>>Messages(<span
		id="numberMessages">0</span>)
	</a>
</sec:authorize>

<br/><br/>

<table border=1 width=100%>
	<tr>
		<th>Name</th>
		<th>Contact</th>
		<th>Offer</th>
	</tr>

	<c:forEach var="offer" items="${offerList}">
		<tr>
			<td>${offer.user.name}</td>
			<td><a href="<c:url value='/message?uid=${offer.username}'/>">Contact</a></td>
			<td>${offer.text}</td>
		</tr>
	</c:forEach>

</table>


<script type="text/javascript">
	function updateMessageLink(data) {
		$("#numberMessages").text(data.number);		
	}

	function onLoad() {
		updatePage();		
		//window.setInterval(updatePage, 5000);
	}
	
	function updatePage(){
		$.getJSON("<c:url value='/getMessages'/>", updateMessageLink);
	}

	$(document).ready(onLoad());
</script>







