<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Analiza propunere</h3>
			<c:set var="analyzesProposalAction"><c:url value='/analyzesProposal/submit/${proposal.id}' /></c:set>
			
			
			<h4 class="mb-3 text-center">Detalii Propunere</h4>
			<hr>
			
			<p><strong>Autor:</strong> ${proposal.user}</p>
			<p><strong>Editie:</strong> ${proposal.edition}</p>
			<p><strong>Topicuri:</strong> ${proposal.getFormattedTopics()}</p>
			<p><strong>Keywords:</strong> ${proposal.keywords}</p>
			
			<c:if test="${not empty proposal.file}">
				<p><a href="">Descarcare fisier</a></p>
			</c:if>
			
			<p>
				<strong>Descriere:</strong><br>
				${proposal.description}
			</p>
			
        	<p>
				<strong>Data Modificata:</strong>
	        	<fmt:formatDate value="${proposal.modified.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
        	<p>
				<strong>Data Creata:</strong>
	        	<fmt:formatDate value="${proposal.created.time}"
	        	type="both" pattern="d/M/y H:m" />
        	</p>
			
			<form:form method="POST" action="${analyzesProposalAction}" modelAttribute="proposalStatus">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				
            	<c:set var="commentError">
					<form:errors path="comment"/>
				</c:set>
            	<div class="form-group${not empty commentError ? ' has-danger' : ''}">
            		<label>Comment</label>
                    <form:textarea rows="5" required="required" type="comment" path="comment" class="form-control"/></textarea>
                    <c:if test="${not empty commentError}">
                    	<div class="form-control-feedback">${commentError}</div>
                    </c:if>
                </div>
                
                <c:set var="proposalStatusError">
					<form:errors path="status"/>
				</c:set>
            	<div class="form-check${not empty proposalStatusError ? ' has-danger' : ''}">
            		 <form:radiobutton path="status"  value="analyzes" label="Doresc sa analizez propunerea" />
            		 <br><form:radiobutton path="status"  value="maybeAnalyzes" label="Poate analizez propunerea"/>
            		 <br><form:radiobutton path="status"  value="rejectAnalyzes" label="Nu doresc sa analizez propunerea"/>
                    <c:if test="${not empty proposalStatusError}">
                    	<div class="form-control-feedback">${proposalStatusError}</div>
                    </c:if>
                </div>
                          
                <button type="submit" class="btn btn-success btn-block">Trimite analiza</button>
	        </form:form>
		</div>
	</div>

</t:layout>
