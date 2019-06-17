package com.test.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Institutions {
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private String linkName;
    private String linkURL;
    private String content;
    private String nameInstitutions;

    public Institutions(LocalDate startPeriod, LocalDate endPeriod, String linkName, String linkURL, String content, String nameInstitutions) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.linkName = linkName;
        this.linkURL = linkURL;
        this.content = content;
        this.nameInstitutions = nameInstitutions;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameInstitutions() {
        return nameInstitutions;
    }

    public void setNameInstitutions(String nameInstitutions) {
        this.nameInstitutions = nameInstitutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institutions that = (Institutions) o;
        return startPeriod.equals(that.startPeriod) &&
                Objects.equals(endPeriod, that.endPeriod) &&
                Objects.equals(linkName, that.linkName) &&
                Objects.equals(linkURL, that.linkURL) &&
                Objects.equals(content, that.content) &&
                nameInstitutions.equals(that.nameInstitutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPeriod, endPeriod, linkName, linkURL, content, nameInstitutions);
    }
}
