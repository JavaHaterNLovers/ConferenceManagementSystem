<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>		
	<div class="row">
		<div class="col-5 mx-auto">
			<h3 class="mb-3 text-center">Trimite un mesaj propunerilor respinse pentru editia - ${edition}</h3>
			
			<form method="POST" action="<c:url value="/rejectedProposals/submit/${edition.id}" />">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

                  <textarea required rows="5" name="message" class="form-control mb-3" placeholder="Mesaj"></textarea>
          
                <button type="submit" class="btn btn-success btn-block">Trimite</button>
	        </form>
		</div>
	</div> 
</t:layout>
