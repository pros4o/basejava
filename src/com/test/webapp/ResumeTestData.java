package com.test.webapp;

import com.test.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Ada");
        resume.putIntoContactInfo(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.putIntoContactInfo(ContactType.SKYPE, "grigory.kislin");
        resume.putIntoContactInfo(ContactType.MAIL, "gkislin@yandex.ru");
        resume.putIntoContactInfo(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.putIntoContactInfo(ContactType.GITHUB, "https://github.com/gkislin");
        resume.putIntoContactInfo(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.putIntoContactInfo(ContactType.HOMEPAGE, "http://gkislin.ru/");

        resume.putIntoSections(SectionType.PERSONAL, new SimpleSection("Ведущий стажировок " +
                "и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.putIntoSections(SectionType.OBJECTIVE, new SimpleSection("Аналитический склад ума," +
                " сильная логика, креативность, инициативность. Пурист кода и архитектуры. "));

        List<String> textAreaAchievement = new ArrayList<>(6);
        textAreaAchievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников. ");
        textAreaAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления" +
                " проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        textAreaAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM." +
                " Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:" +
                " Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей," +
                " интеграция CIFS/SMB java сервера. ");
        textAreaAchievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий" +
                " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5," +
                " Highstock для алгоритмического трейдинга. ");
        textAreaAchievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных" +
                " сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации" +
                "о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования" +
                " и мониторинга системы по JMX (Jython/ Django). ");
        textAreaAchievement.add("Реализация протоколов по приему платежей всех основных платежных системы" +
                " России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.putIntoSections(SectionType.ACHIEVEMENT, new MarkedSection(textAreaAchievement));

        List<String> textAreaQualifications = new ArrayList<>(15);
        textAreaQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        textAreaQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        textAreaQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, ");
        textAreaQualifications.add("MySQL, SQLite, MS SQL, HSQLDB ");
        textAreaQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ");
        textAreaQualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts, ");
        textAreaQualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit," +
                " Selenium (htmlelements). ");
        textAreaQualifications.add("Python: Django. ");
        textAreaQualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        textAreaQualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ");
        textAreaQualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB," +
                " StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5," +
                " ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ");
        textAreaQualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, ");
        textAreaQualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher," +
                " Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer. ");
        textAreaQualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования," +
                " архитектурных шаблонов, UML, функционального программирования ");
        textAreaQualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.putIntoSections(SectionType.QUALIFICATIONS, new MarkedSection(textAreaQualifications));

        List<Institutions> institutionsJob = new ArrayList<>();
        institutionsJob.add(new Institutions(LocalDate.parse("1/10/2013", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                LocalDate.now(), "Java Online Projects", "http://javaops.ru/", "Автор проекта.\nСоздание, организация" +
                " и проведение Java онлайн проектов и стажировок.",
                "Java Online Projects"));
        institutionsJob.add(new Institutions(LocalDate.parse("1/10/2014", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                LocalDate.parse("1/01/2016", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                "Wrike", "https://www.wrike.com", "Старший разработчик (backend).\nПроектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", "Wrike."));
        resume.putIntoSections(SectionType.EXPERIENCE, new InstitutionsSection(institutionsJob));

        List<Institutions> institutionsStudy = new ArrayList<>();
        institutionsStudy.add(new Institutions(LocalDate.parse("1/10/2014", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                LocalDate.parse("1/01/2016", DateTimeFormatter.ofPattern("d/MM/yyyy")), "Coursera", "https://www.coursera.org/course/progfun",
                "'Functional Programming Principles in Scala' by Martin Odersky", "Coursera"
        ));
        resume.putIntoSections(SectionType.EDUCATION, new InstitutionsSection(institutionsStudy));
    }
}
