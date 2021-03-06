<%--
  Created by IntelliJ IDEA.
  User: MarekScholtz
  Date: 9.12.2016
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:template>
    <jsp:attribute name="body">

        <div class="jumbotron" style="background-color: #822B2B; color: #EEDBE9">
            <div class="row">
                <div class="col-sm-2" style="margin-right: 2%">
                    <h1><fmt:message key="Wines"/></h1>
                </div>
                <div class="col-sm-6">
                    <br/>
                    <img src="<c:url value="/images/wine.png" />"/>
                </div>
            </div>
            <fmt:message key="nameHolder" var="wineHolder"/>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <p align="right">
                    <a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/wines/new" role="button">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <fmt:message key="create_new"/>
                    </a>
                </p>
            </sec:authorize>
        </div>

        <table class="table table-hover">
            <thead>
            <tr>
                <th class="text-left"><fmt:message key="number"/></th>
                <th class="text-left"><fmt:message key="wine.name"/></th>
                <th class="text-left"><fmt:message key="wine.vintage"/></th>
                <th class="text-left"><fmt:message key="wine.predicate"/></th>
                <th class="text-left"><fmt:message key="detail"/></th>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th class="text-left"><fmt:message key="edit"/></th>
                        <th class="text-left"><fmt:message key="delete"/></th>
                        <th class="text-left"><fmt:message key="addToWineList"/></th>
                </sec:authorize>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${wines}" var="wine">
                <c:set var="count" value="${count + 1}" scope="page"/>
                <tr>
                    <td class="col-xs-3 text-left">${count}.</td>
                    <td class="col-xs-3 text-left"><c:out value="${wine.name}"/></td>
                    <td class="col-xs-3 text-left"><c:out value="${wine.vintage}"/></td>
                    <td class="col-xs-3 text-left"><c:out value="${wine.predicate}"/></td>

                    <form:form method="get" action="${pageContext.request.contextPath}/wines/detail/${wine.id}" cssClass="form-horizontal">
                        <td class="col-xs-1 text-left">
                            <button class="btn btn-default" type="submit">
                                <span class="sr-only"><fmt:message key="detail"/></span>
                                <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                            </button>
                        </td>
                    </form:form>

                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <form:form method="get" action="${pageContext.request.contextPath}/wines/update/${wine.id}" cssClass="form-horizontal">
                            <td class="col-xs-1 text-center">
                                <button class="btn btn-default" type="submit">
                                    <span class="sr-only"><fmt:message key="edit"/></span>
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                </button>
                            </td>
                        </form:form>

                        <form:form method="post" action="${pageContext.request.contextPath}/wines/delete/${wine.id}" cssClass="form-horizontal">
                            <td class="col-xs-1 text-left">
                                <button class="btn btn-default" type="submit">
                                    <span class="sr-only"><fmt:message key="remove"/></span>
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </button>
                            </td>
                        </form:form>
                        <c:choose>
                        <c:when test="${empty wineLists}">
                             <td class="col-xs-1 text-left">
                                 <div class="dropdown">
                                     <button type="button" class="btn btn-default .dropdown-toggle" data-toggle="dropdown" disabled="true">
                                         <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <span class="caret"></span>
                                     </button>
                                     <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <c:forEach items="${wineLists}" var="winelist">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/wines/add/${wine.id}/${winelist.id}"><c:out value="${winelist.name}"/></a></li>
                                        </c:forEach>
                                     </div>
                                 </div>
                             </td>
                        </c:when>
                        <c:otherwise>
                             <form:form method="post" action="${pageContext.request.contextPath}/wines/add/${wine.id}" cssClass="form-horizontal">
                            <td class="col-xs-1 text-center">
                                <div class="dropdown">
                                    <button type="button" class="btn btn-default .dropdown-toggle" data-toggle="dropdown">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <span class="caret"></span>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <c:forEach items="${wineLists}" var="winelist">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/wines/add/${wine.id}/${winelist.id}"><c:out value="${winelist.name}"/></a></li>
                                       </c:forEach>
                                    </div>
                                </div>
                            </td>
                        </form:form>
                        </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:template>
