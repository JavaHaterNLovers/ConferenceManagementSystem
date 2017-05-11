<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout>
	<div class="row">
		<div class="col-6 mx-auto">
			<h3 class="mb-3 text-center">Login</h3>
			
			<c:if test="${not empty errors}">
				<div class="alert alert-danger" role="alert">
					<c:forEach items="${errors}" var="error">
						${error}
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${not empty messages}">
				<div class="alert alert-success" role="alert">
					${messages}
				</div>
			</c:if>

			<form name='loginForm' action="<c:url value='/login' />" method='POST'>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
				<div class="form-group">
					<input required class="form-control" type='text' name='username' placeholder="User">
				</div>
				<div class="form-group">
					<input required class="form-control" type='password' name='password' placeholder="Password">
				</div>
				<button type="submit" class="btn btn-primary btn-block">Login</button>
			</form>
		</div>
	</div>
</t:layout>
