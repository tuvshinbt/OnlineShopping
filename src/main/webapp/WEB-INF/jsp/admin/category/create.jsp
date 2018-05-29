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
            $('#categoryForm').ajaxForm({
                target:'#edit-target',
                url:'${pageContext.request.contextPath}/admin/category/create'
            });
        },
        success: function() {
            $('#edit-modal').modal('hide');
            module.list();
        },
        submit: function() {
            if($('#file').val() == null || $('#file').val() == ''){
                $('#file').remove();
            }
            $('#categoryForm').submit();
        }
    };
</script>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">
        <c:choose>
            <c:when test="${categoryForm.id ne null}">
                Edit <i>${categoryForm.name}</i> Category
            </c:when>
            <c:otherwise>
                Create category
            </c:otherwise>
        </c:choose>
    </h4>
</div>
<div class="modal-body">
    <form:form modelAttribute="categoryForm" method="post" enctype="multipart/form-data">
        <form:hidden path="id"/>
        <div class="form-group">
            <label for="name">Name</label>
            <form:errors path="name" cssStyle="color: red" />
            <form:input path="name" class="form-control" />
        </div>
        <div class="form-group">
            <label for="file">Image</label>
            <form:errors path="file" cssStyle="color: red" />
            <form:input path="file" type="file" class="form-control" />
        </div>
        <form:select path="parentId" cssClass="form-control">
            <form:option value="">Select Category</form:option>
            <c:forEach items="${categories}" var="row">
                <form:option value="${row.id}">${row.name}</form:option>
                <c:if test="${row.childCategories ne null}">
                    <c:forEach items="${row.childCategories}" var="cRow">
                        <form:option value="${cRow.id}">---${cRow.name}</form:option>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </form:select>
    </form:form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" onclick="create.submit()">Save changes</button>
</div>



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
