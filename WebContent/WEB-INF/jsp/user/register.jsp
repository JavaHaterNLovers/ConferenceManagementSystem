<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
    </head>
    <body>
        <h3>Register</h3>
        <form:form method="POST" action="${baseUrl}/register/submit" modelAttribute="user">
            <table>
            	<c:set var="emailError">
					<form:errors path="email"/>
				</c:set>
            	<tr class="${not empty emailError ? 'has-error' : ''}">
                    <td><form:label path="email">Email</form:label></td>
                    <td><form:input path="email"/></td>
                    <td>${emailError}</td>
                </tr>
                <tr>
                    <td><form:label path="password">Password</form:label></td>
                    <td><form:input path="password"/></td>
                    <td><form:errors path="password" /></td>
                </tr>
                <tr>
                    <td><form:label path="nume">Nume</form:label></td>
                    <td><form:input path="nume"/></td>
                    <td><form:errors path="nume" /></td>
                </tr>
                <tr>
                    <td><form:label path="prenume">Prenume</form:label></td>
                    <td><form:input path="prenume"/></td>
                    <td><form:errors path="prenume" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>