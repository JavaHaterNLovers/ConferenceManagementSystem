<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-6 mx-auto">
			<h3 class="mb-3 text-center">Register</h3>
			
			<c:set var="registerAction"><c:url value='/register/submit' /></c:set>
			<form:form method="POST" action="${registerAction}" modelAttribute="user">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
            	<c:set var="emailError">
					<form:errors path="email"/>
				</c:set>
            	<div class="form-group${not empty emailError ? ' has-danger' : ''}">
                    <form:input type="email" path="email" class="form-control" placeholder="Email"/>
                    <c:if test="${not empty emailError}">
                    	<div class="form-control-feedback">${emailError}</div>
                    </c:if>
                </div>
                
                <c:set var="passwordError">
					<form:errors path="password"/>
				</c:set>
            	<div class="form-group${not empty passwordError ? ' has-danger' : ''}">
                    <form:input type="password" path="password" class="form-control" placeholder="Parola"/>
                    <c:if test="${not empty passwordError}">
                    	<div class="form-control-feedback">${passwordError}</div>
                    </c:if>
                </div>
                
                <c:set var="passwordConfError">
					<form:errors path="passwordConfirmed" />
				</c:set>
            	<div class="form-group${not empty passwordConfError ? ' has-danger' : ''}">
                    <form:input type="password" path="confirmPassword" class="form-control" placeholder="Confirma Parola"/>
                    <c:if test="${not empty passwordConfError}">
                    	<div class="form-control-feedback">${passwordConfError}</div>
                    </c:if>
                </div>
                
                <c:set var="numeError">
					<form:errors path="nume"/>
				</c:set>
            	<div class="form-group${not empty numeError ? ' has-danger' : ''}">
                    <form:input path="nume" class="form-control" placeholder="Nume"/>
                    <c:if test="${not empty numeError}">
                    	<div class="form-control-feedback">${numeError}</div>
                    </c:if>
                </div>
                
                <c:set var="prenumeError">
					<form:errors path="prenume"/>
				</c:set>
            	<div class="form-group${not empty prenumeError ? ' has-danger' : ''}">
                    <form:input path="prenume" class="form-control" placeholder="Prenume"/>
                    <c:if test="${not empty prenumeError}">
                    	<div class="form-control-feedback">${prenumeError}</div>
                    </c:if>
                </div>
                
                <button type="submit" class="btn btn-success btn-block">Register</button>
	        </form:form>
		</div>
	</div>
</t:layout>
