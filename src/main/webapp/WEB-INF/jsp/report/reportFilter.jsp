<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/include.jsp" %>
<%@include file="/WEB-INF/jsp/template/header.jsp" %>

<section id="aa-myaccount">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="aa-myaccount-area" style="padding: 50px">
                    <div class="col-md-10" style="float: none; margin-left: 20%; width: 50%; height: 50%">
                        <h4 style="color:orangered" align="center">REPORT</h4>
                        <form:form method="post" modelAttribute="reportFilterForm" action="${pageContext.request.contextPath}/report/reportFilter">
                            <div class="form-group" style="border-width: 20px ">
                                <label class="col-md-3">From:</label>
                                <%@ page import="java.text.*, java.util.Date" %>
                                <% DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    String formattedDate = df.format(new Date()); %>
                                <form:input id="begin_Date" class="col-md-7" path="begin_Date" type="date"
                                            min="2017-01-10" tabindex="1" value="<%= formattedDate %>"/> <br/>
                                <div class="form-group">
                                    <label class="col-md-3"></label>
                                    <div class="col-md-7">
                                        <form:errors path="begin_Date" cssStyle="color: red"/>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="form-group" style="border-width: 20px ">
                                <label class="col-md-3">To:</label>
                                <form:input id="end_Date" class="col-md-7" path="end_Date" type="date" max="2050-01-10"
                                            min="begin_Date.value" tabindex="2" value="<%= formattedDate %>"/><br/>
                                <div class="form-group">
                                    <label class="col-md-3"></label>
                                    <div class="col-md-7">
                                        <form:errors path="end_Date" cssStyle="color: red"/><br/>
                                        <c:if test="${not empty error}">
                                            <p style="color:red" class="col-md-16">${error}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <!-- check sec ROLE-->
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <div class="form-group">
                                    <label class="col-md-3">Vendor:</label>
                                    <label for="allVendor" class="col-md-7">
                                        <input type="checkbox" tabindex="3" value="0" id="allVendor" checked
                                        /> All</label><br/>
                                    <label class="col-md-3"></label>
                                    <form:select tabindex="4" id="vendor" class="col-md-7" path="lstVendor_Id"
                                                 multiple="multiple" disabled="true">
                                        <c:forEach var="v" items="${vendors}">
                                            <form:option selected="selected" id="otherVendors" value="${v.id}">
                                                ${v.companyName}
                                            </form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <br/><br/><br/><br/>
                            </sec:authorize>

                            <div class="form-group">
                                <label class="col-md-3">Category:</label>
                                <label class="col-md-7"><input type="checkbox" value="0" tabindex="5"
                                                               id="allCategory" checked/>
                                    All</label><br/>
                                <label class="col-md-3"></label>
                                <form:select tabindex="6" id="category" class="col-md-7" path="lstCategory_Id"
                                             multiple="multiple" size="5" disabled="true">
                                    <c:forEach var="c" items="${categories}">
                                        <form:option selected="selected" value="${c.id}">${c.name}</form:option>
                                    </c:forEach>
                                </form:select>
                                <br/><br/><br/><br/>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <br/><br/>
                                    <label class="col-md-3">Report Type:</label>
                                    <form:select path="reportType" id="reportType" class="col-md-7">
                                        <form:option value="generalReport">General Report</form:option>
                                        <form:option value="ProfitReport">Profit Report</form:option>
                                        <%--<form:option value="ChartReport">Chart Report</form:option>--%>
                                    </form:select>
                                </sec:authorize>
                                <br/><br/>
                            </div>
                            <div class="col-md-10" align="center">
                                <input id="btnExport" style="color: orangered" type="submit" value="Export"
                                       tabindex="7"/>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/report/report.js" charset="utf-8"></script>
<%@include file="/WEB-INF/jsp/template/footer.jsp" %>

<c:if test="${!empty message}">
    <script type="text/javascript">
        $.sticky('<spring:message code="${message.message}"/>', {
            autoclose: 5000,
            position: "top-right",
            type: "st-${fn:toLowerCase(message.type)}"
        });

        <%--<c:if test="${message.type eq 'SUCCESS'}">--%>
        <%----%>
        <%--</c:if>--%>
    </script>
</c:if>
