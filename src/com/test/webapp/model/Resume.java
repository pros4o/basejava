package com.test.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<SectionType, Section> sections = new HashMap<>();
    private Map<ContactType, String> contactInfo = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void putIntoSections(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    public void putIntoContactInfo(ContactType contactType, String info){
        contactInfo.put(contactType, info);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        int checkResume = fullName.compareTo(o.fullName);
        return checkResume == 0 ? uuid.compareTo(o.uuid) : checkResume;
    }
}
