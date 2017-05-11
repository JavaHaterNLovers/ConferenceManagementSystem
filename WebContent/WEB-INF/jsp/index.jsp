<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout>
	<div class="jumbotron">
		<h1 class="display-3">Conferinte</h1>
		<table class="table table-responsive table-striped table-hover mx-auto w-75">
			<thead>
				<tr>
					<th>Nume</th>
					<th>Autor</th>
					<th>Inceput Depuneri</th>
					<th>Sfarsit Depuneri</th>
					<th>Creata</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${conferences}" var="conference">
				    <tr>      
				        <td>${conference.name}</td>
				        <td>${conference.author}</td>
				        <td>
				        	<fmt:formatDate value="${conference.beginSubmissions.time}"
				        	type="both" pattern="d/M/y H:m" />
			        	</td>
			        	<td>
				        	<fmt:formatDate value="${conference.endSubmissions.time}"
				        	type="both" pattern="d/M/y H:m" />
			        	</td>
			        	<td>
				        	<fmt:formatDate value="${conference.created.time}"
				        	type="both" pattern="d/M/y H:m" />
			        	</td> 
				    </tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</t:layout>
