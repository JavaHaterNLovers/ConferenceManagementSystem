<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6">
			<h3 class="mb-3 text-center">Detalii Cont</h3>
			<hr>
			
			<p><strong>Email:</strong> ${user.email}</p>
			<p><strong>Nume:</strong> ${user.nume}</p>
			<p><strong>Prenume:</strong> ${user.prenume}</p>
		</div>
		<div class="offset-3 col-6 mt-3">
			<h3 class="mb-3 text-center">Propuneri
				<sec:authorize access="hasAnyRole('ROLE_CHAIR', 'ROLE_CO_CHAIR')" var="isReviewer" />
				<c:if test="${isReviewer}">
					<a href="<c:url value='/viewProposals' />" class="btn btn-primary">Review</a>
				</c:if>
			</h3>
			<hr>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Editie</th>
						<th>Nume</th>
						<th>Data Creeata</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposals}" var="proposal">
					    <tr>      
					        <td>${proposal.edition}</td>
					        <td><a href="<c:url value="/viewProposal/${proposal.id}"/>">${proposal.name}</a></td>
				        	<td>
					        	<fmt:formatDate value="${proposal.created.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td> 
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<sec:authorize access="hasRole('ROLE_CHAIR')" var="isChair" />
		<c:if test="${isChair}">
			<div class="offset-3 col-6 mt-3">
				<h3 class="mb-3 text-center">Conferinte Creeate <a href="<c:url value='/createConference' />" class="btn btn-primary">Creeaza Conferinta</a></h3>
				<hr>
				
				<table class="table table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
							<th>Data Creeata</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${conferences}" var="conference">
						    <tr>      
						        <td>${conference.name}</td>
					        	<td>
						        	<fmt:formatDate value="${conference.created.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
		<sec:authorize access="hasRole('ROLE_CO_CHAIR')" var="isCoChair" />
		<c:if test="${isChair || isCoChair}">
			<div class="offset-3 col-6 mt-3">
				<h3 class="mb-3 text-center">Editii Creeate <a href="<c:url value='/createEdition' />" class="btn btn-primary">Creeaza Editie</a></h3>
				<hr>
				
				<table class="table table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
							<th>Data Inceput</th>
							<th>Data Sfarsit</th>
							<th>Data Creeata</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${editions}" var="edition">
						    <tr>      
						        <td><a href="<c:url value="/viewEditionProposals/${edition.id}"/>">${edition.name}</a></td>
						        <td>
						        	<fmt:formatDate value="${edition.beginDate.time}"
						        	type="both" pattern="d/M/y H:m" /> 
					        	</td> 
					        	<td>
						        	<fmt:formatDate value="${edition.endDate.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td> 
					        	<td>
						        	<fmt:formatDate value="${edition.created.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td> 
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
		<sec:authorize access="hasRole('ROLE_CHAIR')" var="isChair" />
		<c:if test="${isChair}">
			<div class="offset-3 col-6 mt-3">
				<h3 class="mb-3 text-center">Topic-uri conferinte <a href="<c:url value='/createTopic' />" class="btn btn-primary">Adauga topic</a></h3>
				<hr>
				
				<table class="table table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${topics}" var="topic">
						    <tr>      
						        <td>${topic.name}</td>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
		<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')" var="isSuperAdmin" />
		<c:if test="${isSuperAdmin}">
			<div class="offset-3 col-6 mt-3">
				<h3 class="mb-3 text-center">Lista Utilizatori</h3>
				<hr>
				
				<table class="table table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
							<th>Prenume</th>
							<th>Email</th>
							<th>Rol</th>
							<th></th>							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
						    <tr>      
						        <td>${user.nume}</td>
					        	<td>
						        	${user.prenume}
					        	</td>
					        	<td>${user.email}</td>
					        	<td>${user.rol}</td>
					        	<c:if test="${user.id != pageContext.request.userPrincipal.principal.id}">
					        		<td><a href="<c:url value='/updateUser/${user.id}' />" class="btn btn-warning">Modifica</a></td>
				        		</c:if>
						</tr>
						    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		
		
	</div>
</t:layout>
