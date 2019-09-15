<%@ page import="com.test.webapp.model.ContactType" %>
<%@ page import="com.test.webapp.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.test.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<main>
    <section>
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <dl>
                <dt>Имя:</dt>
                <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
            </dl>
            <h3>Контакты: </h3>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContactInfo(type)}"></dd>
                </dl>
            </c:forEach>
            <hr>
            <h3>Секции: </h3>
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:choose>
                    <c:when test="${sectionType.equals(SectionType.PERSONAL) ||
                    sectionType.equals(SectionType.OBJECTIVE)}">
                        <dl>
                            <dt>${sectionType.name()}</dt>
                            <dd><input type="text" name="${sectionType.name()}" size="100"
                                       value="${resume.getSections(sectionType)}"></dd>
                        </dl>
                    </c:when>
                    <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) ||
                    sectionType.equals(SectionType.QUALIFICATIONS)}">
                        <dl>
                            <dt>${sectionType.name()}</dt>
                            <dd>
                                <textarea rows="25" cols="50" id="${sectionType.name()}" name="${sectionType.name()}">
                                        ${resume.getSections(sectionType)}
                                </textarea>
                            </dd>
                        </dl>
                    </c:when>
                </c:choose>
            </c:forEach>
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </form>
    </section>
</main>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
