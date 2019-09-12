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
    </section>
</main>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
