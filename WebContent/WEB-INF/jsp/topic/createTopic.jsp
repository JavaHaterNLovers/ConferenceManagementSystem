<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Creare topic</h3>
			
			<c:set var="createTopicAction"><c:url value='/createTopic/submit' /></c:set>
			<form:form method="POST" action="${createTopicAction}" modelAttribute="topic">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				
            	<c:set var="numeError">
					<form:errors path="name"/>
				</c:set>
            	<div class="form-group${not empty numeError ? ' has-danger' : ''}">
                    <form:input required="required" path="name" class="form-control" placeholder="Nume topic"/>
                    <c:if test="${not empty numeError}">
                    	<div class="form-control-feedback">${numeError}</div>
                    </c:if>
                </div>    
         		
                <button type="submit" class="btn btn-success btn-block">Creeaza</button>
	        </form:form>
		</div>
	</div> 
	<script>
   		$(document).ready(function(){
   			$('input[type=datetime-local]').val(new Date().toJSON().slice(0,16));

   		});         			
  	</script>
</t:layout>
