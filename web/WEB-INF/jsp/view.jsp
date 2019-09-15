<%@ page import="com.test.webapp.model.SimpleTextSection" %>
<%@ page import="com.test.webapp.model.MarkedSection" %>
<%@ page import="com.test.webapp.model.InstitutionSection" %>
<%@ page import="com.test.webapp.model.Institution" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="resume" type="com.test.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<main>
    <section>
        <h2>
            ${resume.fullName}&nbsp;
            <a href="resume?uuid=${resume.uuid}&action=edit">Edit</a>
        </h2>
        <p>
            <c:forEach var="contactEntry" items="${resume.contactInfo}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.test.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </p>
        <p>
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.test.webapp.model.SectionType, com.test.webapp.model.AbstractSection>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="com.test.webapp.model.AbstractSection"/>
        <h2>${sectionEntry.key.name()}</h2>
        <c:choose>
            <c:when test="${(sectionEntry.key == 'PERSONAL') || (sectionEntry.key =='OBJECTIVE')}">
                <p>
                    <%=
                    ((SimpleTextSection) section).toString()
                    %>
                </p>
            </c:when>
            <c:when test="${(sectionEntry.key == 'ACHIEVEMENT') || (sectionEntry.key =='QUALIFICATIONS')}">
                <ul>
                    <c:forEach var="listItems" items="<%=((MarkedSection) sectionEntry.getValue()).getItems()%>">
                        <li>${listItems}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${(sectionEntry.key == 'EXPERIENCE') || (sectionEntry.key =='EDUCATION')}">
                <c:forEach var="inst" items="<%=((InstitutionSection) sectionEntry.getValue()).getListInst()%>">
                    <p class="EXPandEdu">
                    <c:if test="${inst.homePage.url != null}">
                        <a href="${inst.homePage.url}">${link.name}</a>
                    </c:if>
                    <c:if test="${inst.homePage.url == null}">
                        <p>${inst.homePage.name}</p>
                    </c:if>
                    </p>
                    <c:set var="time_now" value="<%=LocalDate.now()%>"/>
                    <c:forEach var="position" items="${inst.positions}">
                        <jsp:useBean id="position" type="com.test.webapp.model.Institution.Position"/>
                        <c:choose>
                            <c:when test="${position.endPeriod == time_now}">
                                <p>Период: ${position.startPeriod} по текущий момент</p>
                            </c:when>
                            <c:when test="${position.endPeriod != null}">
                                <p>Период: ${position.startPeriod} по ${position.endPeriod}</p>
                            </c:when>
                        </c:choose>
                        <p><strong>${position.title}</strong></p>
                        <p style="font-size: 12px">${position.description}</p>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
        </c:forEach>
        </p>
    </section>
</main>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
