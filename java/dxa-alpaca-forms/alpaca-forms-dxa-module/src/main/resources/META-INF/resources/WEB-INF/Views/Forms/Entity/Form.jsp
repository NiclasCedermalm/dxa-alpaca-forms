<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<jsp:useBean id="entity" type="com.sdl.dxa.modules.alpacaforms.model.Form" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>

<div>
    <c:if test="${not empty entity.title}">
        <h3 ${markup.property(entity, "title")}>${entity.title}</h3>
    </c:if>

    <script>
        var alpaca_form_${entity.id} = ${entity.aggregatedDefinition};
    </script>

    <div id="csrf-${entity.id}">
        <dxa:csrf-token/>
    </div>

    <div class="alpaca-form" id="${entity.id}"></div>

</div>
