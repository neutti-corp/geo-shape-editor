<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="nmap-top">
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand d-flex" href="#">
                <c:choose>
                    <c:when test="${TYPE1 eq 'EVTS'}">
                    <img class="nmap-logo" src="${pageContext.request.contextPath}/static/images/logo/KGeoSTD_Logo.png" alt="Logo">
                    </c:when>
                    <c:when test="${TYPE1 eq 'SGVS'}">
                    <img class="nmap-logo" src="${pageContext.request.contextPath}/static/images/logo/logo02.webp" alt="Logo">
                    </c:when>
                </c:choose>
                <div class="nmap-title">
                    <span class="nmap-maintitle">${SYSTEM_MAINTITLE}</span>
                    <span class="nmap-subtitle">${SYSTEM_SUBTITLE}</span>
                </div>
            </a>
            <div class="collapse d-flex justify-content-end">
                <%--<ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Dropdown
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Action</a></li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                    </li>
                </ul>--%>
                <div class="d-flex gap-3 position-relative">
                    <a class="header-right-btn" data-bs-toggle="modal" data-bs-target="#alertModal">
                        <img src="${pageContext.request.contextPath}/static/images/icon/DIV-14-${TYPE1}.svg" alt=""/>
                    </a>
                    <span class="nmap-badge">3</span>
                    <a class="header-right-btn" onclick="N.Map.Helper.openFullScreen()">
                        <img src="${pageContext.request.contextPath}/static/images/icon/DIV-21-${TYPE1}.svg"/>
                    </a>
                    <a class="header-right-btn" onclick="N.Map.UI.toggleRight()">
                        <img src="${pageContext.request.contextPath}/static/images/icon/DIV-24-${TYPE1}.svg"/>
                    </a>
                    <a class="header-right-btn" onclick="N.Map.logout()">
                        <img src="${pageContext.request.contextPath}/static/images/icon/DIV-27-${TYPE1}.svg"/>
                    </a>
                </div>
            </div>
        </div>
    </nav>
</div>
<script>

</script>
