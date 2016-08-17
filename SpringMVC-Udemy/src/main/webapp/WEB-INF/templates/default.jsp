<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute>
</title>


<c:url var="CSS_URL" value="/static/css/main.css"></c:url>
<link href="${CSS_URL}" rel="stylesheet" type="text/css" />

<c:url var="JAVASCRIPT_URL" value="/static/script/jquery.js"></c:url>
<script type="text/javascript" src="${JAVASCRIPT_URL}"></script>

<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>
<body>

	<div class="header">
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>

	<tiles:insertAttribute name="toolbar"></tiles:insertAttribute>

	<div class="content">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>

	<div class="footer">
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</div>

</body>
</html>