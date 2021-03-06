<%--@elvariable id="roles" type="java.util.List<Role>"--%>
<%--@elvariable id="loggedUser" type="ru.job4j.crud.model.User"--%>
<%--@elvariable id="users" type="java.util.List<ru.job4j.crud.model.User>"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="dateTime" scope="request" class="java.util.Date"/>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>

<!-- User update dialog -->
<div id="user-update-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <!-- Title and close button -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Update user</h4>
            </div>
            <form id="user-update-form" class="form-horizontal">
                <div class="modal-body">
                    <p class="validateTips">All form fields are required.</p>
                    <!-- Id of user to update -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Choose id:</label>
                        <div class="col-sm-10">
                            <select id="user-update-select-id" name="id" class="form-control">
                                <c:forEach items="${users}" var="user">
                                    <option value="${user.id}">
                                            ${user.id}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!-- Login -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Login:</label>
                        <div class="col-sm-10">
                            <input name="login" type="text" class="form-control"
                                   placeholder="Enter login (e.g. nagibator2000)">
                        </div>
                    </div>
                    <!-- Password -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Password:</label>
                        <div class="col-sm-10">
                            <input name="password" type="password" class="form-control"
                                   placeholder="Enter password (e.g. qwerty123)">
                        </div>
                    </div>
                    <!-- Role -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Role:</label>
                        <div class="col-sm-10">
                            <select name="role" class="form-control">
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role}">
                                            ${role}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!-- Name -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Name:</label>
                        <div class="col-sm-10">
                            <input name="name" type="text" class="form-control"
                                   placeholder="Enter name (e.g. John Sullivan)">
                        </div>
                    </div>
                    <!-- Email -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Email:</label>
                        <div class="col-sm-10">
                            <input name="email" type="email" class="form-control"
                                   placeholder="Enter email (e.g. sullivan@netmail.com)">
                        </div>
                    </div>
                    <!-- Country -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Country:</label>
                        <div class="col-sm-10">
                            <div>
                                <select name="country-select" class="form-control"></select>
                            </div>
                            <div>
                                <input name="country-form" type="text" class="form-control"
                                       placeholder="Enter your country if not found in list.">
                            </div>
                        </div>
                    </div>
                    <!-- City -->
                    <div class="form-group">
                        <label class="control-label col-sm-2">City:</label>
                        <div class="col-sm-10">
                            <div>
                                <select name="city-select" class="form-control"></select>
                            </div>
                            <div>
                                <input name="city-form" type="text" class="form-control"
                                       placeholder="Enter your city if not found in list.">
                            </div>
                        </div>
                    </div>
                    <!-- Allow form submission with keyboard without duplicating the dialog button -->
                    <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>