<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Propunere</h3>
			
			<c:set var="createProposalAction"><c:url value='/createProposal/submit/${id}' /></c:set>
			<form:form method="POST" action="${createProposalAction}" modelAttribute="proposal">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				
            	<c:set var="nameError">
					<form:errors path="name"/>
				</c:set>
            	<div class="form-group${not empty nameError ? ' has-danger' : ''}">
                    <form:input required="required" path="name" class="form-control" placeholder="Numele propuneri"/>
                    <c:if test="${not empty nameError}">
                    	<div class="form-control-feedback">${nameError}</div>
                    </c:if>
                </div>
                
                 <c:set var="descriptionError">
					<form:errors path="description"/>
				</c:set>
            	<div class="form-group${not empty descriptionError ? ' has-danger' : ''}">
                    <form:input required="required" path="description" class="form-control" placeholder="Descrierea"/>
                    <c:if test="${not empty descriptionError}">
                    	<div class="form-control-feedback">${descriptionError}</div>
                    </c:if>
                </div>
                
                <c:set var="topicsError">
					<form:errors path="topics"/>
				</c:set>
            	<div class="form-group${not empty topicsError ? ' has-danger' : ''}">
            		<label>Subiecte</label>
                    <form:select multiple = "multiple" path = "topics" class="form-control js-example-basic-multiple">
                    	<form:options items="${listAllTopics}" itemValue = "id" itemLabel = "name"/>
                    </form:select>
                     <c:if test="${not empty topicsError}">
                    	<div class="form-control-feedback">${topicsError}</div>
                    </c:if>
                </div>
                
                <div class="form-group">
                    <form:input path="keywords" class="form-control" placeholder="Cuvinte cheie"/>
                </div>
                
                <div class="form-group">
                    <form:input path="file" class="form-control" placeholder="Incarcare fisier" type="file"/>
                </div>
         		
                <button type="submit" class="btn btn-success btn-block">Creeaza</button>
	        </form:form>
		</div>
	</div> 
	<script>
   			$(".js-example-basic-multiple").select2();
  	</script>
</t:layout>
