package com.test.webapp.web;

import com.test.webapp.Config;
import com.test.webapp.model.*;
import com.test.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid == "") {
            resume = new Resume(UUID.randomUUID().toString(), "");
            resume.setFullName(fullName);
        } else if (uuid == null) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.putIntoContactInfo(type, value);
            } else {
                resume.getContactInfo().remove(type);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (value == null || value.trim().length() == 0 && values.length < 2) {
                resume.getSections().remove(sectionType);
            } else {
                switch (sectionType.name()) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.putIntoSections(sectionType, new SimpleTextSection(request.getParameter(sectionType.name())));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        resume.putIntoSections(sectionType, new MarkedSection(Arrays.asList(value.split("\n"))));
                }
            }
        }


        if ("".equals(uuid)) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        String fullName = request.getParameter("fullName");
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                if (uuid.equals("")) {
                    resume = new Resume("", "");
                } else {
                    resume = storage.get(uuid);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
