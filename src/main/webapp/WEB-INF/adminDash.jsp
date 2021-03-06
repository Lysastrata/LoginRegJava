<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.firstName}"></c:out></h1>

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <table>
      <thead>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Actions</th>
      </thead>
      <c:forEach items="${admins}" var="user" >

			<tbody>
			    <td><c:out value="${user.firstName}"/></td>
			    <td><c:out value="${user.lastName}"/></td>
			    <td><c:out value="${user.username}"/></td>
			    <td>Admin</td>
			</tbody>
			</c:forEach>
		<c:forEach items="${norms}" var="user" >
		<tbody>
			    <td><c:out value="${user.firstName}"/></td>
			    <td><c:out value="${user.lastName}"/></td>
			    <td><c:out value="${user.username}"/></td>
			    <td><a href="admin/delete/${user.id}">Delete</a> <a href="admin/make/${user.id}">Make Admin</a></td>
			</tbody>
			
		</c:forEach>
    </table>
    
</body>
</html>
