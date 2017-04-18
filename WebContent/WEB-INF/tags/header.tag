<%@tag description="Header" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="currentPage" value="${pageContext.request.getAttribute('javax.servlet.forward.servlet_path')}"/>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>

<div class="header clearfix">
    <nav>
          <ul class="nav nav-pills float-right">
                <li class="nav-item">
                  	<a class="nav-link<c:if test="${currentPage=='/'}"> active</c:if>" href="${baseUrl}/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                  	<a class="nav-link<c:if test="${currentPage =='/welcome'}"> active</c:if>" href="${baseUrl}/welcome">Welcome</a>
                </li>
          </ul>
    </nav>
    <h3 class="text-muted"><a href="${baseUrl}/">CMS</a></h3>
</div>