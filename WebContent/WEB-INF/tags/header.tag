<%@tag description="Header" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="currentPage" value="${pageContext.request.getAttribute('javax.servlet.forward.servlet_path')}"/>

<div class="header clearfix">
    <nav>
          <ul class="nav nav-pills float-right">
                <li class="nav-item">
                  	<a class="nav-link<c:if test="${currentPage=='/'}"> active</c:if>" href="${baseUrl}/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
	                <sec:authorize access="hasRole('ROLE_USER')" var="isLoggedIn" />
					<c:choose>
					   <c:when test="${isLoggedIn}">
						   <!-- For login user -->
							<c:url value="/logout" var="logoutUrl" />
							<form action="${logoutUrl}" method="post" id="logoutForm">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
							<!-- Because of CSRF, the logout needs to be a form not a link -->
							<script>
								function formSubmit() {
									document.getElementById("logoutForm").submit();
								}
							</script>
							<p class="nav-link">
								<c:if test="${pageContext.request.userPrincipal.principal != null}">
									<a <c:if test="${currentPage =='/profile'}">class="profile-active"</c:if> href="${baseUrl}/profile">${pageContext.request.userPrincipal.principal}</a> | 
								</c:if>
								<a href="javascript:formSubmit()">Logout</a>
							</p>
					   </c:when>
					   <c:otherwise>
					   		<a class="nav-link<c:if test="${currentPage =='/login'}"> active</c:if>" href="${baseUrl}/login">Login</a>
					   </c:otherwise>
					</c:choose>
				</li>
          </ul>
    </nav>
    <h3 class="text-muted"><a href="${baseUrl}/">CMS</a></h3>
</div>
<c:if test="${not empty flashMessage}">
	<div class="row">
		<div class="col-6 mx-auto">
			<div class="alert alert-success" role="alert">
				${flashMessage}
			</div>
		</div>
	</div>
</c:if>