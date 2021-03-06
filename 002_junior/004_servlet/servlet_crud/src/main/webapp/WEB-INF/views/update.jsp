<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="request" type="ru.job4j.crud.User"/>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="update" value="update"/>


<html>
<head>
    <title>Update user</title>
</head>
<body>

<div align="center">
    <h1>Update user</h1>
</div>

<form action="${context}/${update}" method="post">
    <table style="border: 0" align="center">
        <tr>
            <td align="left">User name:</td>
            <td><input type="text" name="name" value="${user.name}"></td>
        </tr>
        <tr>
            <td align="left">User login:</td>
            <td><input type="text" name="login" value="${user.login}"></td>
        </tr>
        <tr>
            <td align="left">User password:</td>
            <td><input type="text" name="password" value="${user.password}"></td>
        </tr>
        <tr>
            <td align="left">User email:</td>
            <td><input type="text" name="email" value="${user.email}"></td>
        </tr>
        <tr>
            <td align="left">User role:</td>
            <td>
                <select name="role">
                    <%--@elvariable id="roles" type="java.util.Collection"--%>
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${role == user.role}">
                                <option value="${role}" selected="selected">${role}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${role}">${role}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="center" valign="center">
                <input type="hidden" name="id" value="${user.id}">
                <input type="submit" value="update">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
