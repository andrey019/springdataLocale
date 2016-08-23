<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <title>Denied!</title>
</head>
<body>
<c:choose>
    <c:when test="${user == 'anonymousUser'}">
        what are you doing here?.. O_o
    </c:when>
    <c:otherwise>
        ${user}, that's not meant for you!
    </c:otherwise>
</c:choose>

</body>
</html>
