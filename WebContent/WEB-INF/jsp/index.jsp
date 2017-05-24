<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout>
	<div class="jumbotron">
		<h1 class="display-3">Editii Conferinte</h1>
		<table class="table table-striped table-hover mx-auto w-75">
			<thead>
				<tr>
					<th>Conferinta</th>
					<th>Nume</th>
					<th>Autor</th>
					<th>Data Inceput</th>
					<th>Data Sfarsit</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${editions}" var="edition">
				    <tr>
						<td>${edition.conference}</td>
				        <td><a href="<c:url value="/viewEdition/${edition.id}"/>">${edition.name}</a></td>
				        <td>${edition.author}</td>
				        <td>
				        	<fmt:formatDate value="${edition.beginDate.time}"
				        	type="both" pattern="d/M/y H:m" />
			        	</td>
			        	<td>
				        	<fmt:formatDate value="${edition.endDate.time}"
				        	type="both" pattern="d/M/y H:m" />
			        	</td>
			        	<td>
			        		<a href="#" class="btn btn-primary">Inscriere</a>
			        	</td>
				    </tr>
				    
				</c:forEach>
			</tbody>
		</table>
	</div>
</t:layout>
