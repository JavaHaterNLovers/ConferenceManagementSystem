<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout>
	<div class="row">
		<div class="col-6 mx-auto">
			<h3 class="mb-3 text-center">Login</h3>
			
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					${error}
				</div>
			</c:if>
			<c:if test="${not empty message}">
				<div class="alert alert-success" role="alert">
					${message}
				</div>
			</c:if>

			<form name='loginForm' action="<c:url value='/login' />" method='POST'>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
				<div class="form-group">
					<input required class="form-control" type='text' name='username' placeholder="Email">
				</div>
				<div class="form-group">
					<input required class="form-control" type='password' name='password' placeholder="Password">
				</div>
				
				<div class="row justify-content-between">
					<div class="col-6">
						<button type="submit" class="btn btn-success btn-block">Login</button>
					</div>
					<div class="col-4">
						<a href="<c:url value='/register' />" class="btn btn-primary btn-block">Register</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</t:layout>
