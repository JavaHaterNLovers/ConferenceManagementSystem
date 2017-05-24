<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Modifica Utilizator</h3>
			
			<c:set var="updateUserAction"><c:url value='/updateUser/submit' /></c:set>
			<form:form method="POST" action="${updateUserAction}" modelAttribute="user">
			<input type="hidden" nume="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
		    <c:set var="idError">
				<form:errors path="id"/>
			</c:set>
			
			<div class="form-group${not empty idError ? ' has-danger' : ''}">
					<label for="id">Id</label>
                    <form:input required="required" path="id" class="form-control"/>
                    <c:if test="${not empty idError}">
                    	<div class="form-control-feedback">${idError}</div>
                    </c:if>
            </div>
            
            
			<c:set var="numeError">
				<form:errors path="nume"/>
			</c:set>
			
			<div class="form-group${not empty numeError ? ' has-danger' : ''}">
			<label for="nume">Nume</label>
                    <form:input required="required" path="nume" class="form-control"/>
                    <c:if test="${not empty numeError}">
                    	<div class="form-control-feedback">${numeError}</div>
                    </c:if>
            </div>
            
            
            <c:set var="prenumeError">
					<form:errors path="prenume"/>
		    </c:set>
            <div class="form-group${not empty prenumeError ? ' has-danger' : ''}">
            <label for="prenume">Prenume</label>
                    <form:input required="required" path="prenume" class="form-control"/>
                    <c:if test="${not empty prenumeError}">
                    	<div class="form-control-feedback">${prenumeError}</div>
                    </c:if>
            </div>
            
            <c:set var="emailError">
					<form:errors path="email"/>
			</c:set>
            <div class="form-group${not empty emailError ? ' has-danger' : ''}">
            <label for="email">Email</label>
                    <form:input required="required" path="email" class="form-control"/>
                    <c:if test="${not empty emailError}">
                    	<div class="form-control-feedback">${emailError}</div>
                    </c:if>
            </div>
			
			
			<c:set var="passwordError">
				<form:errors path="password"/>
			</c:set>
			
			<div class="form-group${not empty passwordError ? ' has-danger' : ''}">
			<label for="password">Parola</label>
                    <form:input required="required" path="password" class="form-control"/>
                    <c:if test="${not empty passwordError}">
                    	<div class="form-control-feedback">${passwordError}</div>
                    </c:if>
            </div>
			
			<c:set var="createdError">
					<form:errors path="created"/>
			</c:set>
            <div class="form-group${not empty createdError ? ' has-danger' : ''}">
			<label for="created">Data inregistrarii</label>
                    <form:input type="datetime-local" required="required" path="created" class="form-control"/>
                    <c:if test="${not empty createdError}">
                    	<div class="form-control-feedback">${createdError}</div>
                    </c:if>
            </div>
            
            <c:set var="rolError">
					<form:errors path="rol"/>
			</c:set>
            <div class="form-group${not empty rolError ? ' has-danger' : ''}">
            <label for="rol">Rol</label>
						<form:select required="required" path="rol" class="form-control">

					    <form:options items="${enumRol}" />
					</form:select>
					                    <c:if test="${not empty rolError}">
                    	<div class="form-control-feedback">${emailError}</div>
                    </c:if>
            </div>
			
			<button type="submit" class="btn btn-success btn-block">Update</button>
			</form:form>
			
			
			
			
		</div>
	</div> 
	<script>
   		$(document).ready(function(){
   			$('input[type=datetime-local]').val(new Date().toJSON().slice(0,16));

   		});         			
  	</script>
</t:layout>
