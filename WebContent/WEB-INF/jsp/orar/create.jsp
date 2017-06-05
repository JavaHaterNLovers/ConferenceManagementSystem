<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Detalii propunere</h3>

			<div class="text-center">
				<c:if test="${not empty proposal.file}">
					<p><a href="<c:url value='#' />" class="btn btn-primary">Descarcare fisier</a></p>
				</c:if>
				<c:if test="${valid}">
					<a href="<c:url value='#' />" class="btn btn-success">Update</a>
				</c:if>		
			</div>	
			<p><strong>Nume:</strong> ${proposal.name}</p>
			<p><strong>Autor:</strong> ${proposal.user}</p>
			<p><strong>Conferinta:</strong> ${proposal.edition.conference}</p>
			<p><strong>Editie:</strong> ${proposal.edition}</p>
			<p><strong>Topic-uri:</strong>
				${proposal.getFormattedTopics()}
			</p>
			<p><strong>Cuvinte cheie:</strong> ${proposal.keywords}</p>
			<p><strong>Descriere:</strong> ${proposal.description}</p>
			<p>
				<strong>Creata:</strong>
	        	<fmt:formatDate value="${proposal.created.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Modificata:</strong>
	        	<fmt:formatDate value="${proposal.modified.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<hr>
        	
			<h3 class="mb-3 text-center">Creare Orar</h3>
			
			<c:set var="createOrarAction"><c:url value='/createOrar/submit/${proposal.id}' /></c:set>
			<form:form method="POST" action="${createOrarAction}" modelAttribute="orar">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
                
                <c:set var="sectionError">
					<form:errors path="section"/>
				</c:set>
            	<div class="form-group${not empty sectionError ? ' has-danger' : ''}">
            		<label>Sesiune</label>
                    <form:select path="section" items="${sessions}" itemValue="id" itemLabel="name" class="form-control js-example-basic-multiple" />
                   	<c:if test="${not empty sectionError}">
                    	<div class="form-control-feedback">${sectionError}</div>
                    </c:if>
                </div>
                
                <c:set var="beginDateError">
					<form:errors path="beginDate"/>
				</c:set>
            	<div class="form-group${not empty beginDateError ? ' has-danger' : ''}">
                    <label for="beginDateInput">Data inceput</label>
                    <form:input type="datetime-local" required="required" path="beginDate" class="form-control" />
                    <c:if test="${not empty beginDateError}">
                    	<div class="form-control-feedback">${beginDateError}</div>
                    </c:if>
                </div>
                 
                <c:set var="endDateError">
					<form:errors path="endDate"/>
				</c:set>
            	<div class="form-group${not empty endDateError ? ' has-danger' : ''}">
                    <label for="endDateInput">Data sfarsit</label>
                    <form:input type="datetime-local" required="required" path="endDate" class="form-control" />
                    <c:if test="${not empty endDateError}">
                    	<div class="form-control-feedback">${endDateError}</div>
                    </c:if>
                </div>

                <button type="submit" class="btn btn-success btn-block">Creeaza</button>
	        </form:form>
		</div>
	</div> 
	<script>
   		$(document).ready(function() {
   			$(".js-example-basic-multiple").select2();
   		});
  	</script>
</t:layout>
