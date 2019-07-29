package com.test.webapp.storage.serializer;

import com.test.webapp.model.*;
import com.test.webapp.storage.IOStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements IOStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContactInfo();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entryTwo : sections.entrySet()) {
                SectionType sectionType = entryTwo.getKey();
                dos.writeUTF(sectionType.name());
                if (sectionType == SectionType.PERSONAL) {
                    dos.writeUTF(String.valueOf(entryTwo.getValue()));
                }
                if (sectionType == SectionType.OBJECTIVE) {
                    dos.writeUTF(String.valueOf(entryTwo.getValue()));
                } else if (sectionType == SectionType.ACHIEVEMENT) {
                    writeInnerSection(dos, ((MarkedSection)entryTwo.getValue()).getTextArea());
                } else if (sectionType == SectionType.QUALIFICATIONS) {
                    writeInnerSection(dos, ((MarkedSection)entryTwo.getValue()).getTextArea());
                } else if (sectionType == SectionType.EXPERIENCE) {
                    writeInnerSection(dos, ((InstitutionSection)entryTwo.getValue()).getListInst());
                } else if (sectionType == SectionType.EDUCATION) {
                    writeInnerSection(dos, ((InstitutionSection)entryTwo.getValue()).getListInst());
                }
            }


        }
    }

    private <T> void writeInnerSection(DataOutputStream dos, Collection<T> collection) throws IOException {
        dos.writeInt(collection.size());
        for (T data : collection) {
            dos.writeUTF(String.valueOf(data));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.putIntoContactInfo(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++){
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                if (sectionType == SectionType.PERSONAL) {
                    resume.putIntoSections(sectionType, new SimpleTextSection(dis.readUTF()));
                }
                if (sectionType == SectionType.OBJECTIVE) {
                    resume.putIntoSections(sectionType, new SimpleTextSection(dis.readUTF()));
                }
                if (sectionType == SectionType.ACHIEVEMENT) {
                    resume.putIntoSections(sectionType, new MarkedSection(readList(dis)));
                }
                if (sectionType == SectionType.QUALIFICATIONS) {
                    resume.putIntoSections(sectionType, new MarkedSection(readList(dis)));
                }
                if (sectionType == SectionType.EXPERIENCE) {
                    resume.putIntoSections(sectionType, new InstitutionSection(
                            readList(dis)
                    ));
                }
                if (sectionType == SectionType.EDUCATION)
                    resume.putIntoSections(sectionType, new InstitutionSection(
                            readList(dis)
                    ));
            }
            return resume;
        }
    }

    private <T> List<T> readList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for(int i = 0; i < size ; i++) {
            list.add((T) dis.readUTF());
        }
        return list;
    }

}
