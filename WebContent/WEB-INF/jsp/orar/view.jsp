<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6 mt-3">
			<h3 class="mb-3 text-center">Sesiunile Editiei ${edition}
				<a class="btn btn-primary" href="<c:url value="/createSession/${edition.id}"/>">Creeare Sesiune</a>
			</h3>
			<hr>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Nume</th>
						<th>Camera</th>
						<th>Responsabil</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sessions}" var="session">
					    <tr>      
					        <td><a href="<c:url value="/viewSession/${session.id}"/>">${session.name}</a></td>
					        <td>${session.room}</td>
					        <td>${session.user}</td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
			
			
			<h4 class="mb-3 text-center">Propuneri</h4>
			<hr>
			
			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Nume</th>
						<th>User</th>
						<th>Data Creeata</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${proposals}" var="proposal">
		        		<c:set var="status" value="${service.getProposalStatus(proposal)}"/>
						<c:if test="${status == 1}">
						    <tr>      
						        <td><a href="<c:url value="/createOrar/${proposal.id}"/>">${proposal.name}</a></td>
						        <td>${proposal.user}</td>
					        	<td>
						        	<fmt:formatDate value="${proposal.created.time}"
						        	type="both" pattern="d/M/y H:m" />
					        	</td>
						    </tr>
					    </c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>		
	</div>
</t:layout>
