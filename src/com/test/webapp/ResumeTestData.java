package com.test.webapp;

import com.test.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = ResumeTestData.fillContentResume("22", "Григорий Кислин");
        System.out.println(r);
    }

    public static Resume fillContentResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
      /*  resume.putIntoContactInfo(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.putIntoContactInfo(ContactType.SKYPE, "grigory.kislin");
        resume.putIntoContactInfo(ContactType.MAIL, "gkislin@yandex.ru");
        resume.putIntoContactInfo(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.putIntoContactInfo(ContactType.GITHUB, "https://github.com/gkislin");
        resume.putIntoContactInfo(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.putIntoContactInfo(ContactType.HOMEPAGE, "http://gkislin.ru/");

        resume.putIntoSections(SectionType.PERSONAL, new SimpleTextSection("Ведущий стажировок " +
                "и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.putIntoSections(SectionType.OBJECTIVE, new SimpleTextSection("Аналитический склад ума," +
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

        resume.putIntoSections(SectionType.EXPERIENCE, new InstitutionSection(
                new Institution(
                        new Link("Java Online Projects", "http://javaops.ru/"),
                        new Institution.Position(
                                LocalDate.parse("1/10/2013", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.now(),
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."
                        )),
                new Institution(
                        new Link("Wrike", "https://www.wrike.com/"),
                        new Institution.Position(
                                LocalDate.parse("1/10/2014", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/01/2016", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "Старший разработчик (backend)",
                                "Старший разработчик (backend)\n" +
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java" +
                                        " 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
                        )),
                new Institution(
                        new Link("RIT Center", ""),
                        new Institution.Position(
                                LocalDate.parse("1/04/2012", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/10/2014", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная" +
                                        " политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация " +
                                        "Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД " +
                                        "и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C" +
                                        " (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                                        " Интеграция Alfresco JLAN для online редактирование из браузера документов " +
                                        "MS Office. Maven + plugin development, Ant, Apache Commons, Spring security," +
                                        " Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell" +
                                        " remote scripting via ssh tunnels, PL/Python"
                        )
                )
        ));

       resume.putIntoSections(SectionType.EDUCATION, new InstitutionSection(
                new Institution(
                        new Link("Coursera", null),//"https://www.coursera.org/course/progfun"),
                        new Institution.Position(
                                LocalDate.parse("1/03/2013", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/05/2013", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                null
                        )
                ),
                new Institution(
                        new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        new Institution.Position(
                                LocalDate.parse("1/03/2011", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/04/2011", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                ""
                        )
                ),
                new Institution(
                        new Link("Siemens AG", "http://www.siemens.ru/"),
                        new Institution.Position(
                                LocalDate.parse("1/01/2005", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/05/2005", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                ""
                        )
                ),
                new Institution(
                        new Link("Alcatel", "http://www.alcatel.ru/"),
                        new Institution.Position(
                                LocalDate.parse("1/09/1997", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/03/1998", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                " \t6 месяцев обучения цифровым телефонным сетям (Москва)",
                                ""
                        )
                ),
                new Institution(
                        new Link("Санкт-Петербургский национальный исследовательский университет информационных" +
                                " технологий, механики и оптики", "http://www.ifmo.ru/"),
                        new Institution.Position(
                                LocalDate.parse("1/09/1993", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/07/1996", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "Аспирантура (программист С, С++)",
                                ""
                        ),
                        new Institution.Position(
                                LocalDate.parse("1/09/1987", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                LocalDate.parse("1/07/1993", DateTimeFormatter.ofPattern("d/MM/yyyy")),
                                "Инженер (программист Fortran, C)",
                                ""
                        )
                )
        ));*/
    return resume;
    }
}
