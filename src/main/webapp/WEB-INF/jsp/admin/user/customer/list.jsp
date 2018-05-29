<%--
  Created by IntelliJ IDEA.
  User: Erdenebayar
  Date: 5/2/2018
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include.jsp"%>

<table class="table table-striped">
    <thead>
    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Name</th>
        <th>Status</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="row">
        <tr <c:if test="${row.status ne 'ENABLED'}">class="danger" </c:if>>
            <td><i>${row.username}</i></td>
            <td><i>${row.email}</i></td>
            <td>${row.firstName} - ${row.lastName}</td>
            <td>${row.status}</td>
            <td>
                    <%--<a href="#" type="button" onclick="module.delete('${row.id}')">
                        <i class="glyphicon glyphicon-info-sign"></i>
                    </a>--%>
                <a href="#edit" onclick="module.edit('${row.id}')">
                    <i class="glyphicon glyphicon-pencil"></i>
                </a>
                <c:if test="${row.status eq 'ENABLED'}">
                    <a href="#delete" type="button" onclick="module.delete('${row.id}')">
                        <i class="glyphicon glyphicon-remove"></i>
                    </a>
                </c:if>
                <c:if test="${row.status ne 'ENABLED'}">
                    <a href="#changeStatus" type="button" onclick="module.changeStatus('${row.id}', 'ENABLED')">
                        <i class="glyphicon glyphicon-ok"></i>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="/WEB-INF/jsp/template/pagination.jsp"%>