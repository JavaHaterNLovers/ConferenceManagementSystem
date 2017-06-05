<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6 mt-3">
			<h3 class="mb-3 text-center">Propuneri Noi</h3>
			<hr>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Editie</th>
						<th>Nume</th>
						<th>User</th>
						<th>Data Creeata</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposals}" var="proposal">
					    <tr>      
					        <td>${proposal.edition}</td>
					        <td><a href="<c:url value="/analyzesProposal/${proposal.id}"/>">${proposal.name}</a></td>
					        <td>${proposal.user}</td>
				        	<td>
					        	<fmt:formatDate value="${proposal.created.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td> 
					    </tr>
					</c:forEach>
				</tbody>
			</table>
			
			<h3 class="mb-3 text-center">Propuneri Pentru Review</h3>
			<hr>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Editie</th>
						<th>Nume</th>
						<th>User</th>
						<th>Data Creeata</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposalsReview}" var="proposal">
					    <tr>      
					        <td>${proposal.edition}</td>
					        <td><a href="<c:url value="/analyzesProposal/${proposal.id}"/>">${proposal.name}</a></td>
					        <td>${proposal.user}</td>
				        	<td>
					        	<fmt:formatDate value="${proposal.created.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td> 
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>		
	</div>
</t:layout>
