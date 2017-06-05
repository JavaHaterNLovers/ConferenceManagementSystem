<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6 mt-3">
			<h3 class="mb-3 text-center">Propunerile Editiei ${edition}</h3>
			<hr>
			
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
					    <tr>      
					        <td><a href="<c:url value="/viewEditionProposals/viewCommentsProposal//${proposal.id}"/>">${proposal.name}</a></td>
					        <td>${proposal.user}</td>
				        	<td>
					        	<fmt:formatDate value="${proposal.created.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td>
				        	<td>
				        		<c:set var="status" value="${service.getProposalStatus(proposal)}"/>
				        		<c:if test="${status == 1}">
									<strong class="text-success">Acceptata</strong>
								</c:if>
								<c:if test="${status == -1}">
									<strong class="text-danger">Respinsa</strong>
								</c:if>
								<c:if test="${status == 0}">
									<strong class="text-info">In asteptare</strong>
								</c:if>
				        	</td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>		
	</div>
</t:layout>
