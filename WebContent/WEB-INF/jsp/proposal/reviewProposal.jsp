<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout>
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Review propunere</h3>
			<c:set var="reviewProposalAction"><c:url value='/reviewProposal/submit/${proposal.id}' /></c:set>
			
			
			<h4 class="mb-3 text-center">Detalii Propunere</h4>
			<hr>
			
			<p><strong>Autor:</strong> ${proposal.user}</p>
			<p><strong>Editie:</strong> ${proposal.edition}</p>
			<p><strong>Topicuri:</strong> ${proposal.getFormattedTopics()}</p>
			<p><strong>Keywords:</strong> ${proposal.keywords}</p>
			
			<c:if test="${not empty proposal.file}">
				<p><a href="<c:url value='#' />" class="btn btn-primary">Descarcare fisier</a></p>
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
			
			<h5 class="mb-2 text-center">Evaluari:</h5>
       		<div class="list-group">
				<c:forEach var="listValue" items="${allReviews}">
					 <div class="list-group-item list-group-item-action flex-column align-items-start mb-3">
					    <div class="d-flex w-100 justify-content-between">
					      <h5 class="mb-1">${listValue.user}</h5>
					      <small class="text-muted">${listValue.status}</small>
					    </div>
					    <p class="mb-1">${listValue.comment}</p>
					    <small class="text-muted">
						    Creata:
						    <fmt:formatDate value="${listValue.created.time}"
	        				type="both" pattern="d/M/y H:m" /> Modificata:
	        				<fmt:formatDate value="${listValue.modified.time}"
	        				type="both" pattern="d/M/y H:m" />
        				</small>
					  </div>
				</c:forEach>
			</div>
			
			<c:if test="${not reviewEnd}">
				<h5 class="mb-2 text-center mt-3">Evaluarea mea:</h5>
				<form:form method="POST" action="${reviewProposalAction}" modelAttribute="proposalStatus">
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
	            		 <form:radiobutton path="status" value="strongAccept" label="Accept Mult" />
	            		 <br><form:radiobutton path="status" value="accept" label="Accept"/>
	            		 <br><form:radiobutton path="status" value="weekAccept" label="Accept Oarecum"/>
	            		 <br><form:radiobutton path="status" value="borderlinePaper" label="Lucrare Mediocra"/>
	            		 <br><form:radiobutton path="status" value="weekReject" label="Repind Oarecum"/>
	            		 <br><form:radiobutton path="status" value="reject" label="Respind"/>
	            		 <br><form:radiobutton path="status" value="strongReject" label="Respind Mult"/>
	                    <c:if test="${not empty proposalStatusError}">
	                    	<div class="form-control-feedback">${proposalStatusError}</div>
	                    </c:if>
	                </div>
	                          
	                <button type="submit" class="btn btn-success btn-block">Trimite review</button>
		        </form:form>
	        </c:if>
		</div>
	</div>

</t:layout>
