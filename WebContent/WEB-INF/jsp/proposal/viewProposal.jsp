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
			<h3 class="mb-3 text-center">Detalii propunere</h3>
			<hr>
				<c:if test="${status == 1}">
					<h5 class="mb-2 text-center text-primary">Status: acceptata</h5>
				</c:if>
				<c:if test="${status == -1}">
					<h5 class="mb-2 text-center text-danger">Status: respinsa</h5>
				</c:if>
				<c:if test="${status == 0}">
					<h5 class="mb-2 text-center text-info">Status: in asteptare</h5>
				</c:if>
			
			<hr>
			<div class="text-center">
				<a href="<c:url value='#' />" class="btn btn-primary">Descarca</a>
				<c:if test="${valid}">
					<a href="<c:url value='#' />" class="btn btn-success">Update</a>
				</c:if>		
			</div>	
			<p><strong>Nume:</strong> ${proposal.name}</p>
			<p><strong>Autor:</strong> ${proposal.user}</p>
			<p><strong>Conferinta:</strong> ${proposal.edition.conference}</p>
			<p><strong>Editie:</strong> ${proposal.edition}</p>
			<br />
			<%-- <p><strong>Topic-uri:</strong>
				<c:forEach var="listValue" items="${proposal.topics}">
					${listValue}
				</c:forEach>
			</p> --%>
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
        	<h5 class="mb-2 text-center">Review-uri</h5>
				<c:forEach var="listValue" items="${proposalStatus}">
					${listValue.comment}<br/>
				</c:forEach>
			</p>
		</div>
	</div> 
</t:layout>
