<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="form" type="com.sdl.dxa.modules.alpacaforms.model.Form" scope="request"/>
<jsp:useBean id="submittedForms" type="java.util.List" scope="request"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>All Forms</title>
    <link rel="stylesheet" href="${markup.versionedContent('/assets/css/main.css')}" type="text/css"/>
    <script src="${markup.versionedContent('/assets/scripts/header.js')}"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${markup.versionedContent('/assets/scripts/ie.js')}"></script>
    <![endif]-->
</head>
<body>
<main class="page-row page-row-expanded" role="main">
    <div class="container-fluid page-border">
        <h1>${form.title} : Submitted Forms</h1>
        <div class="list-group">
            <c:forEach var="form" items="${submittedForms}">
                <a href="/admin/form/get/${form.id}" class="list-group-item">
                    <h4 class="list-group-item-heading">ID: ${form.id}</h4>
                    <p class="list-group-item-text">
                        Date: <fmt:formatDate type="both" value="${form.date}"/>
                    </p>
                </a>
            </c:forEach>
        </div>
    </div>
</main>

<script src="${markup.versionedContent('/assets/scripts/main.js')}"></script>
</body></html>