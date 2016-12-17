<%--
  Created by IntelliJ IDEA.
  User: MarekScholtz
  Date: 9.12.2016
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Winelists">
<jsp:attribute name="body">
    <br />
    <my:a href="/winelists/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New wineList
    </my:a>
    <br />
    <table class="table">
        <thead>
            <tr>
                <th class="text-center"><fmt:message key="number"/></th>
                <th class="text-center"><fmt:message key="winelist.name"/></th>
                <th class="text-center"><fmt:message key="winelist.date"/></th>
                <th class="text-center"><fmt:message key="winelist.marketingevent"/></th>
                <th class="text-center"><fmt:message key="edit"/></th>
                <th class="text-center"><fmt:message key="remove"/></th>
                <th class="text-center"><fmt:message key="wineList.viewWines"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${winelists}" var="winelist">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <tr>
                <td class="col-xs-3 text-center">${count}.</td>
                <td class="col-xs-3 text-center"><c:out value="${winelist.name}"/></td>
                <td class="col-xs-3 text-center"><c:out value="${winelist.date}"/></td>
                <td class="col-xs-3 text-center"><c:out value="${winelist.marketingEvent.description}"/></td>

                <form:form method="get" action="${pageContext.request.contextPath}/winelists/update/${winelist.id}" cssClass="form-horizontal">
                    <td class="col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="edit"/></span>
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form>

                <form:form method="post" action="${pageContext.request.contextPath}/winelists/delete/${winelist.id}" cssClass="form-horizontal">
                    <td class="col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="remove"/></span>
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form>

                <form:form method="get" action="${pageContext.request.contextPath}/winelists/view/${winelist.id}" cssClass="form-horizontal">
                    <td class="col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="wineList.viewWines"/></span>
                            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form>
            </tr>
            </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:template>
