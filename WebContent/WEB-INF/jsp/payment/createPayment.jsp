<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Plateste</h3>
			
			<c:set var="submitAction"><c:url value='/createPayment/submit/${edition}' /></c:set>
			<form:form method="POST" action="${submitAction}" modelAttribute="payment">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                
                <c:set var="cardNumberError">
					<form:errors path="cardNumberValid" />
				</c:set>
            	<div class="form-group${not empty cardNumberError ? ' has-danger' : ''}">
                    <form:input required="required" type="text" path="cardNumber" class="form-control" placeholder="Numar Card"/>
                    <c:if test="${not empty cardNumberError}">
                    	<div class="form-control-feedback">${cardNumberError}</div>
                    </c:if>
                </div>
                
                <div class="form-group">
                    <input required type="text" class="form-control" placeholder="Nume Detinator"/>
                </div>
                
                <div class="form-group row p-0 m-0 mb-3">
                	<input type="date" required class="col form-control mr-3" placeholder="Data Expirare"/>
                	<input required type="number" min="100" max="999" class="col-4 form-control" placeholder="CVV">
                </div>
                
                <button type="submit" class="btn btn-success btn-block">Plateste</button>
	        </form:form>
		</div>
	</div>
	
	<script>
   		$(document).ready(function(){
   			$('input[type=date]').val(new Date().toJSON().slice(0,10));

   		});         			
  	</script>
</t:layout>
