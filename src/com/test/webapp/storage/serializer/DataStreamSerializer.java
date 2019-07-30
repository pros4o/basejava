package com.test.webapp.storage.serializer;

import com.test.webapp.model.*;
import com.test.webapp.storage.IOStrategy;

import java.io.*;
import java.time.LocalDate;
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
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(String.valueOf(entryTwo.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeInnerSection(dos, ((MarkedSection) entryTwo.getValue()).getTextArea());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeDeepSection(dos, ((InstitutionSection) entryTwo.getValue()).getListInst(), institution -> {
                            dos.writeUTF(institution.getHomePage().getName());
                            dos.writeUTF(institution.getHomePage().getUrl());
                            writeDeepSection(dos, institution.getPositions(), positions -> {
                                dos.writeUTF(positions.getStartPeriod().toString());
                                dos.writeUTF(positions.getEndPeriod().toString());
                                dos.writeUTF(positions.getTitle());
                                dos.writeUTF(positions.getDescription());
                            });
                        });
                        break;
                }
            }
        }
    }

    private interface DataWriter<T> {
        void write(T data) throws IOException;
    }

    private interface DataRead<T> {
        T read() throws IOException;
    }

    private <T> void writeDeepSection(DataOutputStream dos, Collection<T> collection, DataWriter<T> dataWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T data : collection) {
            dataWriter.write(data);
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
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.putIntoSections(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.putIntoSections(sectionType, new MarkedSection(readList(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.putIntoSections(sectionType, new InstitutionSection( readList(dis, () -> new Institution(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Institution.Position(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        dis.readUTF()))))));
                        break;
                }
            }
            return resume;
        }
    }

    private <T> List<T> readList(DataInputStream dis, DataRead<T> dataRead) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dataRead.read());
        }
        return list;
    }

}
