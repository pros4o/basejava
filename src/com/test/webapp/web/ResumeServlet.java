package com.test.webapp.web;

import com.test.webapp.Config;
import com.test.webapp.model.Resume;
import com.test.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        PrintWriter pw = response.getWriter();

        pw.write("<!DOCTYPE html>");
        pw.write("<html>");
        pw.write("<head>");
        pw.write("<link rel=\"stylesheet\" href=\"css/style.css\">");
        pw.write("<title>Show Resume</title>");
        pw.write("</head>");
        pw.write("<body>");

        String uuid = request.getParameter("uuid");

        if (uuid != null) {
            Resume resume = storage.get(uuid);
            pw.write("<p>Your resume filed:</p> Uuid: " + resume.getUuid() + " Full Name: " + resume.getFullName());
        } else {

            List<Resume> resumeList = storage.getAllSorted();
            pw.write("<table>");
            for (Resume resume : resumeList) {
                pw.write("<tr>");
                pw.write("<th>" + resume.getUuid() + "</th>");
                pw.write("<th>" + resume.getFullName() + "</th>");
                pw.write("</tr>");
            }
        }
        pw.write("</body>");
        pw.write("</html>");
    }
}
