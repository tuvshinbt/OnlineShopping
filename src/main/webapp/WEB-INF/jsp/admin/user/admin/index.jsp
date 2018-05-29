<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>
<%@include file="/WEB-INF/jsp/template/header.jsp"%>

<!--<h1>Product Inventory</h1> -->

<div class="container">
    <div class="main">
        <h1 class="page-header">Manage Admins</h1>
        <div class="clearfix">
            <div class="pull-right">
                <form id="filterForm" class="form-inline">
                    <input type="text" name="username" class="form-control" width="100" placeholder="Username">
                    <input type="text" name="lastName" class="form-control" width="100" placeholder="Last Name">
                    <input type="text" name="firstName" class="form-control" width="100" placeholder="First Name">
                    <input type="hidden" id="page" name="page" value="">
                    <select name="status" class="form-control">
                        <c:forEach items="${statuses}" var="row">
                            <option value="${row}">${row}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-default" type="button" onclick="module.list(1)">Search</button>
                </form>
            </div>
        </div>
        <div class="table-responsive">
            <div id="list-target"></div>
            <br/>
            <a href="#create" onclick="module.create()"><button class="btn btn-primary">Add Admin</button></a>
        </div>
    </div>
</div>
<br>



<%@include file="/WEB-INF/jsp/template/footer.jsp"%>

<script type="text/javascript">
    $(function () {
        module.list(1);
    });
    module = {
        list : function(page){
            if (page === undefined){
                page = 1;
            }
            $.get('${pageContext.request.contextPath}/admin/user/admin/list?page=' + page, $("#filterForm").serialize(), function(data){
                $('#list-target').html(data);
            });
        },
        create : function(){
            $('#edit-target').html('');
            $('#edit-modal').modal({
                backdrop: 'static'
            });
            $.get('${pageContext.request.contextPath}/admin/user/admin/create', null, function(data){
                $('#edit-target').html(data);
            });
        },
        edit : function(id){
            $('#edit-target').html('');
            $('#edit-modal').modal({
                backdrop: 'static'
            });
            $.get('${pageContext.request.contextPath}/admin/user/admin/edit', 'id='+id, function(data){
                $('#edit-target').html(data);
            });
        },
        changeStatus: function(id,status){
            smoke.confirm('Are sure to change status of this!',function(e){
                if (e){
                    $.get('${pageContext.request.contextPath}/admin/user/changeStatus', 'id='+id+'&status='+status, function(msg){
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
                    $.get('${pageContext.request.contextPath}/admin/user/delete', 'id='+id, function(msg){
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