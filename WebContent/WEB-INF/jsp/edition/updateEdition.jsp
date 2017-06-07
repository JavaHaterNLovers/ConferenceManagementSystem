<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Modificare editie</h3>
			
			<c:set var="updateEditionAction"><c:url value='/updateEdition/submit/${edition.id}' /></c:set>
			<form:form method="POST" action="${updateEditionAction}" modelAttribute="edition">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

				
            	<c:set var="numeError">
					<form:errors path="name"/>
				</c:set>
            	<div class="form-group${not empty numeError ? ' has-danger' : ''}">
                    <form:input required="required" path="name" class="form-control" placeholder="Numele editiei"/>
                    <c:if test="${not empty numeError}">
                    	<div class="form-control-feedback">${numeError}</div>
                    </c:if>
                </div>
                
                 <c:set var="conferenceError">
					<form:errors path="conference"/>
				</c:set>
            	<div class="form-group${not empty conferenceError ? ' has-danger' : ''}">
            		<label>Conferinta</label>
                    <form:select path = "auxConferenceId" class="form-control">
                    	<form:options items="${conferences}" itemValue = "id" itemLabel = "name"/>
                    </form:select>
                     <c:if test="${not empty conferenceError}">
                    	<div class="form-control-feedback">${conferenceError}</div>
                    </c:if>
                </div>
                
                <c:set var="beginDateError">
					<form:errors path="beginDate"/>
				</c:set>
            	<div class="form-group${not empty beginDateError ? ' has-danger' : ''}">
                    <label for="beginDateInput">Data de inceput</label>
                    <form:input type="datetime-local" required="required" path="beginDate" class="form-control" id="beginDateInput"/>
                    <c:if test="${not empty beginDateError}">
                    	<div class="form-control-feedback">${beginDateError}</div>
                    </c:if>
                </div>
                 
                <c:set var="endDateError">
					<form:errors path="endDate"/>
				</c:set>
            	<div class="form-group${not empty endDateError ? ' has-danger' : ''}">
                    <label for="endDateInput">Data de sfarsit</label>
                    <form:input type="datetime-local" required="required" path="endDate" class="form-control" id="endDateInput"/>
                    <c:if test="${not empty endDateError}">
                    	<div class="form-control-feedback">${endDateError}</div>
                    </c:if>
                </div>
                
                <c:set var="beginSubmissionsError">
					<form:errors path="beginSubmissions"/>
				</c:set>
            	<div class="form-group${not empty beginSubmissionsError ? ' has-danger' : ''}">
                    <label for="beginSubmissionsInput">Inceputul perioadei de trimitere a lucrarilor</label>
                    <form:input type="datetime-local" required="required" path="beginSubmissions" class="form-control" id="beginSubmissionsInput"/>
                    <c:if test="${not empty beginSubmissionsError}">
                    	<div class="form-control-feedback">${beginSubmissionsError}</div>
                    </c:if>
                </div>
                
                 <c:set var="endSubmissionsError">
					<form:errors path="endSubmissions"/>
				</c:set>
            	<div class="form-group${not empty endSubmissionsError ? ' has-danger' : ''}">
                    <label>Sfarsitul perioadei de trimitere a lucrarilor</label>
                    <form:input type="datetime-local" required="required" path="endSubmissions" class="form-control"/>
                    <c:if test="${not empty endSubmissionsError}">
                    	<div class="form-control-feedback">${endSubmissionsError}</div>
                    </c:if>
                </div>
                
                 <c:set var="endBiddingError">
					<form:errors path="endBidding"/>
				</c:set>
            	<div class="form-group${not empty endBiddingError ? ' has-danger' : ''}">
                    <label>Sfarsitul perioadei de bidding</label>
                    <form:input type="datetime-local" required="required" path="endBidding" class="form-control"/>
                    <c:if test="${not empty endBiddingError}">
                    	<div class="form-control-feedback">${endBiddingError}</div>
                    </c:if>
                </div>
                
                 <c:set var="endReviewError">
					<form:errors path="endReview"/>
				</c:set>
            	<div class="form-group${not empty endReviewError ? ' has-danger' : ''}">
                    <label>Sfarsitul perioadei de review</label>
                    <form:input type="datetime-local" required="required" path="endReview" class="form-control"/>
                    <c:if test="${not empty endReviewError}">
                    	<div class="form-control-feedback">${endReviewError}</div>
                    </c:if>
                </div>
    
         		
                <button type="submit" class="btn btn-success btn-block">Salveaza modificarile</button>
	        </form:form>
		</div>
	</div> 
</t:layout>
