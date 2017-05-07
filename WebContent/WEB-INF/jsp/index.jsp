<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout>
	<div class="jumbotron">
		<h1 class="display-3">Conferinte</h1>
		<table class="table table-responsive table-striped table-hover mx-auto w-50">
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
				        <td>${conference.beginSubmissions}</td>
				        <td>${conference.endSubmissions}</td>
				        <td>${conference.created}</td>  
				    </tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</t:layout>
