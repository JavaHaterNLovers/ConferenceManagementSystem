<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
		
	<div class="row">
		<div class="col-5 mx-auto">
			<c:if test="${not valid}">
				<div class="alert alert-danger" role="alert">
				  <strong>Oh snap!</strong> Acces interzis!
				</div>
			</c:if>
			<c:if test="${ valid}">			
				<h3 class="mb-3 text-center">Propunere: ${proposal.name}</h3>
	        	<hr>
	        	<h5 class="mb-2 text-center">Alegere evaluatori</h5>
	        	<c:set var="chooseReviewers"><c:url value='/chooseReviewers/submit/${proposal.id}' /></c:set>
				<form:form method="POST" action="${chooseReviewers}" commandName="reviewers">
	        		<div class="list-group">
						<c:forEach var="listValue" items="${proposalStatusWithoutRevieweri}">							
							  <div class="list-group-item list-group-item-action flex-column align-items-start">
							    <div class="d-flex w-100 justify-content-between">							      
							      	<div class="form-check has-success">
									  <label class="form-check-label">
									    <form:checkbox class="form-check-input" id="checkboxSuccess" value="${listValue.id}" path="idProposalsStatus" />
									  	<h5 class="mb-1">${listValue.user}</h5>
									  </label>
									 </div>							      
							      <small class="text-muted">${listValue.status}</small>
							    </div>
							    <p class="mb-1">${listValue.comment}</p>
							    <small class="text-muted">
								    Creata:
								    <fmt:formatDate value="${listValue.created.time}"
			        				type="both" pattern="d/M/y H:m" /> Modificata:
			        				<fmt:formatDate value="${listValue.modified.time}"
			        				type="both" pattern="d/M/y H:m" />
		        				</small>
							  </div>															
						</c:forEach>	
					</div>				
					<button type="submit" class="btn btn-success btn-block">Trimite</button>
				</form:form>
			</c:if>
		</div>
	</div> 
</t:layout>
