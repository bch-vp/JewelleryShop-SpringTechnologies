<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="en"/></c:when>
</c:choose>

<fmt:setBundle basename="content.language"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

</head>
<body>
<div id="app"/>
<script>
    let text_page = {
        error_component: {
            error: {
                title: "<fmt:message key="error_component.error500.title"/>",
                text: "<fmt:message key="error_component.error500.text"/>",
                button: "<fmt:message key="error_component.error500.button"/>",
            }

        }
    }
</script>
<c:choose>
    <c:when test="${is_dev_mode}">
        <script src="http://localhost:8081/error.js"></script>
    </c:when>
    <c:otherwise>
        <script src="/js/error.js"></script>
    </c:otherwise>
</c:choose>
</body>
</html>
