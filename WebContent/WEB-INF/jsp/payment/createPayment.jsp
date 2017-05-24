<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Creare plata</h3>
			
			<c:set var="paymentAction"><c:url value='/createPayment/submit' /></c:set>
			<form:form method="POST" action="${paymentAction}" modelAttribute="payment">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
            	<c:set var="cardNumberError">
					<form:errors path="cardNumber"/>
				</c:set>
            	<div class="form-group${not empty cardNumberError ? ' has-danger' : ''}">
                    <form:input required="required" type="number" path="cardNumber" class="form-control" placeholder="cardNumber" maxlength="16"/>
                    <c:if test="${not empty cardNumberError}">
                    	<div class="form-control-feedback">${cardNumberError}</div>
                    </c:if>
                </div>              
                <button type="submit" class="btn btn-success btn-block">Creeaza plata</button>
	        </form:form>
		</div>
	</div>
</t:layout>
