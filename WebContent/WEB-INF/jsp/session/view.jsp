<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<t:layout>
	<div class="row">
		<div class="offset-3 col-6 mt-3">
			<h3 class="mb-3 text-center">Orarul Editiei ${session.edition} - Sesiune ${session.name}</h3>
			<hr>

			<table class="table table-striped table-hover mx-auto w-100">
				<thead>
					<tr>
						<th>Propunere</th>
						<th>User</th>
						<th>Data Inceput</th>
						<th>Data Sfarsit</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${orare}" var="orar">
					    <tr>      
					        <td>${orar.proposal.name}</td>
					        <td>${orar.proposal.user}</td>
				        	<td>
					        	<fmt:formatDate value="${orar.beginDate.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td>
				        	<td>
					        	<fmt:formatDate value="${orar.endDate.time}"
					        	type="both" pattern="d/M/y H:m" />
				        	</td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>		
	</div>
</t:layout>
