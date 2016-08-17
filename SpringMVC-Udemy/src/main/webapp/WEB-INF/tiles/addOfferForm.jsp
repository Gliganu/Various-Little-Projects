<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	function onDeleteClick(event) {

		var doDelete = confirm("Are you sure you want to delete this offer?");

		if (doDelete == false) {
			event.preventDefault();
		}

	}

	function onReady() {
		$("#delete").click(onDeleteClick);
	}

	$(document).ready(onReady);
</script>


<c:url var="CREATE_OFFER_URL" value="/createOffer"></c:url>

<form:form action="${CREATE_OFFER_URL}" method="post"
	commandName="offer">
	<form:input type="hidden" name="id" path="id" />
	<table>
		<tr>
			<td>Offer:</td>
			<td><form:textarea name="text" path="text" rows="10" cols="50" /></td>
			<td><form:errors path="text" cssClass="error" /></td>
		</tr>
		<tr>
			<td><input value="Save advert" type="submit" /></td>
		</tr>

		<c:if test="${offer.id != 0}">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input class="delete" id="delete" name="delete"
					value="Delete advert" type="submit" /></td>
			</tr>

		</c:if>
	</table>
</form:form>

