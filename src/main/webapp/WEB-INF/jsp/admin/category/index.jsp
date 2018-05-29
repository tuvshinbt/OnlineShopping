<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>


<div class="container">
    <div class="main">
        <h1 class="page-header">Manage Categories</h1>
        <div class="clearfix">
            <div class="pull-right">
                <form id="filterForm" class="form-inline">
                    <input type="text" name="name" class="form-control" width="100" placeholder="name">
                    <select name="parentId" class="form-control">
                        <option value="">Select Category</option>
                        <c:forEach items="${categories}" var="row">
                            <option value="${row.id}">${row.name}</option>
                            <c:if test="${row.childCategories ne null}">
                                <c:forEach items="${row.childCategories}" var="cRow">
                                    <option value="${cRow.id}">---${cRow.name}</option>
                                    <c:if test="${cRow.childCategories ne null}">
                                        <c:forEach items="${cRow.childCategories}" var="sRow">
                                            <option value="${sRow.id}">------${sRow.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </select>
                    <select name="status" class="form-control">
                        <c:forEach items="${statuses}" var="row">
                            <option value="${row}">${row}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-default" type="button" onclick="module.list()">Search</button>
                </form>
            </div>
        </div>
        <div class="table-responsive">
            <div id="list-target"></div>
            <br/>
            <a href="#create" onclick="module.create('')"><button class="btn btn-primary">Add Category</button></a>

        </div>
    </div>
</div>
<br>



<%@include file="/WEB-INF/jsp/template/footer.jsp"%>

<script type="text/javascript">
    $(function () {
        module.list();
    });
    module = {
        childList : function(id){
            $("#filterForm [name='parentId']").val(id);
            module.list();
        },
        list : function(){
            $.get('${pageContext.request.contextPath}/admin/category/list', $("#filterForm").serialize(), function(data){
                $('#list-target').html(data);
            });
        },
        create : function(id){
            $('#edit-target').html('');
            $('#edit-modal').modal({
                backdrop: 'static'
            });
            $.get('${pageContext.request.contextPath}/admin/category/create', 'id='+id, function(data){
                $('#edit-target').html(data);
            });
        },
        changeStatus: function(id,status){
            smoke.confirm('Are sure to change status of this!',function(e){
                if (e){
                    $.get('${pageContext.request.contextPath}/admin/category/changeStatus', 'id='+id+'&status='+status, function(msg){
                        var message = msg.message || msg;
                        var type = 'st-'+(msg.type || 'success').toLowerCase();
                        $.sticky(message, {autoclose: 10000, position:'top-right', type: type});
                        module.list();
                    });
                }
            }, {cancel:"Cancel", ok:"Change"});
        },
        delete: function(id){
            smoke.confirm('Are sure to delete this!',function(e){
                if (e){
                    $.get('${pageContext.request.contextPath}/admin/category/delete', 'id='+id, function(msg){
                        var message = msg.message || msg;
                        var type = 'st-'+(msg.type || 'success').toLowerCase();
                        $.sticky(message, {autoclose: 10000, position:'top-right', type: type});
                        module.list();
                    });
                }
            }, {cancel:"Cancel", ok:"Delete"});
        }
    };
</script>