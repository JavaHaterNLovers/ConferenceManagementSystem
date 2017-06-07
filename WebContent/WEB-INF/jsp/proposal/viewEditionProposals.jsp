<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6 mt-3">			
			<h3 class="mb-3 text-center">Detalii Editie <a href="<c:url value='//updateEdition/${edition.id}' />" class="btn btn-success">Modificare Editie</a></h3>
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
			
			<hr>

			<h3 class="mb-3 text-center">Propunerile Editiei ${edition}
				<c:if test="${createOrar}">
					<a class="btn btn-primary" href="<c:url value="/viewOrar/${edition.id}"/>">Creeare Orar</a>
				</c:if>
			</h3>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Nume</th>
						<th>User</th>
						<th>Data Creeata</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposals}" var="proposal">
		        		<c:set var="status" value="${service.getProposalStatus(proposal)}"/>
						<c:if test="${status != -1}">
						    <tr>      
						        <td><a href="<c:url value="/viewEditionProposals/viewCommentsProposal//${proposal.id}"/>">${proposal.name}</a></td>
						        <td>${proposal.user}</td>
					        	<td>
						        	<fmt:formatDate value="${proposal.created.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td>
					        	<td>
					        		<c:if test="${status == 1}">
										<strong class="text-success">Acceptata</strong>
									</c:if>
									<c:if test="${status == 0}">
										<strong class="text-info">In asteptare</strong>
									</c:if>
					        	</td>
						    </tr>
					    </c:if>
					</c:forEach>
				</tbody>
			</table>
			
			<br />
			
			<h4 class="mb-3 text-center">Propunerile Neacceptate
				<c:if test="${reviewEnded}">
					<a href="<c:url value="/rejectedProposals/${edition.id}"/>" class="btn btn-primary">Trimite Mesaj</a>
				</c:if>
			</h4>
						
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Nume</th>
						<th>User</th>
						<th>Data Creeata</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposals}" var="proposal">
		        		<c:set var="status" value="${service.getProposalStatus(proposal)}"/>
						<c:if test="${status == -1}">
						    <tr>      
						        <td><a href="<c:url value="/viewEditionProposals/viewCommentsProposal//${proposal.id}"/>">${proposal.name}</a></td>
						        <td>${proposal.user}</td>
					        	<td>
						        	<fmt:formatDate value="${proposal.created.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td>
					        	<td>
									<strong class="text-danger">Respinsa</strong>
					        	</td>
						    </tr>
					    </c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>		
	</div>
</t:layout>
