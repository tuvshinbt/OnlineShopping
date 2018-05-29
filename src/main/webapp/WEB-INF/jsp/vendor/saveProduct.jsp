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
            $('#productForm').ajaxForm({
                target:'#edit-target',
                url:'${pageContext.request.contextPath}/vendor/product/save'
            });
        },
        success: function() {
            $('#edit-modal').modal('hide');
            modules.list();
        },
        submit: function() {
            if($('#file').val() == null || $('#file').val() == ''){
                $('#file').remove();
            }
            $('#productForm').submit();

        }
    };
</script>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">Product:</h4>
</div>
<div class="modal-body">
    <form:form modelAttribute="productForm" method="post" enctype="multipart/form-data">
        <form:hidden path="id"/>
        <div class="form-group">
            <label for="categories">Category</label>
            <form:select name="categoryId" cssClass="form-control" path="categoryId">
                <form:option value="">Select Category</form:option>
                <c:forEach items="${categories}" var="row">
                    <form:option value="${row.id}">${row.name}</form:option>
                    <c:if test="${row.childCategories ne null}">
                        <c:forEach items="${row.childCategories}" var="cRow">
                            <form:option value="${cRow.id}">---${cRow.name}</form:option>
                            <c:if test="${cRow.childCategories ne null}">
                                <c:forEach items="${cRow.childCategories}" var="sRow">
                                    <form:option value="${sRow.id}">------${sRow.name}</form:option>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </form:select>
            <form:errors path="categoryId" cssStyle="color: red" />
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <form:errors path="name" cssStyle="color: red" />
            <form:input path="name" class="form-Control" />
        </div>
        <div class="form-group">
            <label for="quantity">Quantity</label>
            <form:errors path="quantity" cssStyle="color: red" />
            <form:input path="quantity" class="form-Control" />
        </div>
        <div class="form-group">
            <label for="price">Price</label>
            <form:errors path="price" cssStyle="color: red" />
            <form:input path="price" type="number" class="form-Control" />
        </div>
        <div class="form-group">
            <label for="description"> Description</label>
            <form:errors path="description" cssStyle="color: red" />
            <form:textarea path="description"  class="form-Control" />
        </div>
        <div class="form-group">
            <form:errors path="categoryId" cssStyle="color: red" />
            <label for="status">Status</label>
            <select name="status" class="form-control">
                <c:forEach items="${statuses}" var="row">
                    <option value="${row}">${row}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="image">Image</label>
            <form:errors path="file" cssStyle="color: red" />
            <form:input path="file" type="file" class="form-Control" />
            <form:input path="image" type="hidden"/>
        </div>
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
