package com.test.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contactInfo = new EnumMap<>(ContactType.class);

    public Resume() {

    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContactInfo() {
        return contactInfo;
    }

    public AbstractSection getSections(SectionType type) {
        return sections.get(type);
    }

    public String getContactInfo(ContactType contactType) {
        return contactInfo.get(contactType);
    }

    public void putIntoSections(SectionType sectionType, AbstractSection abstractSection) {
        sections.put(sectionType, abstractSection);
    }

    public void putIntoContactInfo(ContactType contactType, String info) {
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
        StringBuilder result = new StringBuilder();
        result.append(fullName + "\n");
        for (ContactType contactType : ContactType.values()) {
            result.append(contactType + ": " + getContactInfo(contactType) + "\n");
        }
        for (SectionType sectionType : SectionType.values()) {
            result.append(sectionType + ": " + getSections(sectionType) + "\n");
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contactInfo, resume.contactInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contactInfo);
    }

    @Override
    public int compareTo(Resume o) {
        int checkResume = fullName.compareTo(o.fullName);
        return checkResume == 0 ? uuid.compareTo(o.uuid) : checkResume;
    }
}
