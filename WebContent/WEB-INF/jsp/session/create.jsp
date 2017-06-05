<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Creare sesiune ${edition}</h3>
			
			<c:set var="createSessionAction"><c:url value='/createSession/submit/${edition.id}' /></c:set>
			<form:form method="POST" action="${createSessionAction}" modelAttribute="session">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				
            	<c:set var="numeError">
					<form:errors path="name"/>
				</c:set>
            	<div class="form-group${not empty numeError ? ' has-danger' : ''}">
                    <form:input required="required" path="name" class="form-control" placeholder="Nume sesiune"/>
                    <c:if test="${not empty numeError}">
                    	<div class="form-control-feedback">${numeError}</div>
                    </c:if>
                </div>
                
                <c:set var="roomError">
					<form:errors path="room"/>
				</c:set>
            	<div class="form-group${not empty roomError ? ' has-danger' : ''}">
                    <form:input required="required" path="room" class="form-control" placeholder="Camera sesiune"/>
                    <c:if test="${not empty roomError}">
                    	<div class="form-control-feedback">${roomError}</div>
                    </c:if>
                </div>
                
                <c:set var="userError">
					<form:errors path="user"/>
				</c:set>
            	<div class="form-group${not empty userError ? ' has-danger' : ''}">
            		<label>Reprezentant</label>
                    <form:select path="user" items="${users}" itemValue="id" class="form-control js-example-basic-multiple" />
                   	<c:if test="${not empty userError}">
                    	<div class="form-control-feedback">${userError}</div>
                    </c:if>
                </div>
         		
                <button type="submit" class="btn btn-success btn-block">Creeaza</button>
	        </form:form>
		</div>
	</div> 
	<script>
   		$(document).ready(function(){
   			$(".js-example-basic-multiple").select2();
   		});
  	</script>
</t:layout>
