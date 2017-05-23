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
			<h3 class="mb-3 text-center">Propuneri</h3>
			<hr>
			
			<table class="table table-responsive table-striped table-hover mx-auto w-100">
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
					        <td>${proposal.name}</td>
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
				<h3 class="mb-3 text-center">Conferinte Creeate</h3>
				<hr>
				
				<table class="table table-responsive table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
							<th>Data Creeata</th>
							<th><a href="<c:url value='/createConference' />"  class="btn btn-primary">Creeaza Conferinta</a></th>
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
				<h3 class="mb-3 text-center">Editii Creeate</h3>
				<hr>
				
				<table class="table table-responsive table-striped table-hover mx-auto w-100">
					<thead>
						<tr>
							<th>Nume</th>
							<th>Data Inceput</th>
							<th>Data Sfarsit</th>
							<th>Data Creeata</th>
							<th><a href="<c:url value='/createEdition' />" class="btn btn-primary">Creeaza Editie</a></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${editions}" var="edition">
						    <tr>      
						        <td>${edition.name}</td>
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
	</div>
</t:layout>
