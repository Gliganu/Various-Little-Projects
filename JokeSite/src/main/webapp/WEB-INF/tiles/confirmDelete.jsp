<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h2>You have successfully deleted your account! </h2>

<a  href="<c:url value='j_spring_security_logout'/> "> Click here to finalize process </a>
