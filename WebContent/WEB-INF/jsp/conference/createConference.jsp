<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Creare conferinta</h3>
			
			<c:set var="conferenceAction"><c:url value='/createConference/submit' /></c:set>
			<form:form method="POST" action="${conferenceAction}" modelAttribute="conference">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
            	<c:set var="nameError">
					<form:errors path="name"/>
				</c:set>
            	<div class="form-group${not empty nameError ? ' has-danger' : ''}">
                    <form:input required="required" type="name" path="name" class="form-control" placeholder="Name"/>
                    <c:if test="${not empty nameError}">
                    	<div class="form-control-feedback">${nameError}</div>
                    </c:if>
                </div>              
                <button type="submit" class="btn btn-success btn-block">Creaza conferinta</button>
	        </form:form>
		</div>
	</div>
</t:layout>
