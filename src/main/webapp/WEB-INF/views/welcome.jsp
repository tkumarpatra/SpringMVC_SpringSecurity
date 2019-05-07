<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.fsd.cts.model.User"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>

<%
//get parameters from the request

    User user = (User)request.getAttribute("userDetails");
	System.out.print(user.getUsername());
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<form:form id="logoutForm" method="POST"
				action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form:form>

			<h4>
				Welcome ${pageContext.request.userPrincipal.name} | <a
					onclick="document.forms['logoutForm'].submit()">Logout</a>
			</h4>

			<form:form method="POST" modelAttribute="userForm"
				class="form-signin" action="${contextPath}/update">
				<h2 class="form-signin-heading">Update your account</h2>
				<spring:bind path="name">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="name" class="form-control"
							value="<%=user.getName()%>" autofocus="true" />
						<form:errors path="name" />
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="email" class="form-control"
							value="<%=user.getEmail()%>" />
						<form:errors path="email" />
					</div>
				</spring:bind>

				<spring:bind path="username">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="username" class="form-control"
							value="<%=user.getUsername()%>" />
						<form:errors path="username"></form:errors>
					</div>
				</spring:bind>

				<spring:bind path="password">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="password" path="password" placeholder="Password" class="form-control" />
						<form:errors path="password"></form:errors>
					</div>
				</spring:bind>

				<spring:bind path="passwordConfirm">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="password" path="passwordConfirm" placeholder="Confirm your password"
							class="form-control" />
						<form:errors path="passwordConfirm"></form:errors>
					</div>
				</spring:bind>

				<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
			</form:form>


		</c:if>

	</div>
	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
