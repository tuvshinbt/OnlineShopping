<%--
  Created by IntelliJ IDEA.
  User: Erdenebayar
  Date: 5/2/2018
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>

<script>
    create = {
        init: function() {
            $('#adminForm').ajaxForm({
                target:'#edit-target',
                url:'${pageContext.request.contextPath}/admin/user/admin/create'
            });
        },
        success: function() {
            $('#edit-modal').modal('hide');
            module.list();
        },
        submit: function() {
            $('#adminForm').submit();
        }
    };
</script>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">Create Admin</h4>
</div>
<div class="modal-body">
    <form:form modelAttribute="adminForm" method="post">
        <div class="form-group">
            <form:errors path="firstName" cssStyle="color: red" />
            <label for="firstName">First name</label>
            <form:input path="firstName" class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="lastName" cssStyle="color: red" />
            <label for="lastName">Last name</label>
            <form:input path="lastName" class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="username" cssStyle="color: red" />
            <label for="username">Username</label>
            <form:input path="username" class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="email" cssStyle="color: red" />
            <label for="email">Email</label>
            <form:input path="email" type="email" class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="password" cssStyle="color: red" />
            <label for="password">Password</label>
            <form:password path="password" class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="rePassword" cssStyle="color: red" />
            <label for="rePassword">Verify Password</label>
            <form:password path="rePassword" class="form-Control" />
        </div>
    </form:form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" onclick="create.submit()">Save changes</button>
</div>
<br>

<script type="text/javascript">
    $(function () {
        create.init();
    });
</script>

<c:if test="${!empty message}">
    <script>
        $.sticky('<spring:message code="${message.message}"/>', {autoclose : 5000, position: "top-right", type: "st-${fn:toLowerCase(message.type)}" });

        <c:if test="${message.type eq 'SUCCESS'}">
        create.success();
        </c:if>
    </script>
</c:if>
