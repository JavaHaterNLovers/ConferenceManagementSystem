<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6">
			<h3 class="mb-3 text-center">Detalii Editie</h3>
			<hr>
			
			<p><strong>Autor:</strong> ${edition.author}</p>
			<p><strong>Conferinta:</strong> ${edition.conference}</p>
			<p><strong>Nume:</strong> ${edition.name}</p>
			<p>
				<strong>Data Inceput:</strong>
	        	<fmt:formatDate value="${edition.beginDate.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Data Sfarsit:</strong>
	        	<fmt:formatDate value="${edition.endDate.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Inceput Submiteri:</strong>
	        	<fmt:formatDate value="${edition.beginSubmissions.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Sfarsit Submiteri:</strong>
	        	<fmt:formatDate value="${edition.endSubmissions.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Sfarsit Bidding:</strong>
	        	<fmt:formatDate value="${edition.endBidding.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Sfarsit Review:</strong>
	        	<fmt:formatDate value="${edition.endReview.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Creata:</strong>
	        	<fmt:formatDate value="${edition.created.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        			
			<sec:authorize access="hasRole('ROLE_USER')" var="isUser" />
			<c:if test="${isUser and valid}">
	        	<hr>
				<h5 class="mt-3 text-center"><a href="<c:url value='/createProposal/${edition.id}' />" class="btn btn-success">Inscriere Speaker</a></h5>
			</c:if>
		</div>
	</div>
</t:layout>
