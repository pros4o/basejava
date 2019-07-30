package com.test.webapp.storage.serializer;

import com.test.webapp.model.*;
import com.test.webapp.storage.IOStrategy;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements IOStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeDeepSection(dos, r.getContactInfo().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeDeepSection(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(String.valueOf(entry.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeDeepSection(dos, ((MarkedSection) entry.getValue()).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeDeepSection(dos, ((InstitutionSection) entry.getValue()).getListInst(), institution -> {
                            dos.writeUTF(institution.getHomePage().getName());
                            if (institution.getHomePage().getUrl() != null) {
                                dos.writeUTF(institution.getHomePage().getUrl());
                            } else dos.writeUTF("null");
                            writeDeepSection(dos, institution.getPositions(), positions -> {
                                dos.writeUTF(positions.getStartPeriod().toString());
                                dos.writeUTF(positions.getEndPeriod().toString());
                                dos.writeUTF(positions.getTitle());
                                if (positions.getDescription() != null) {
                                    dos.writeUTF(positions.getDescription());
                                } else dos.writeUTF("null");
                            });
                        });
                        break;
                }
            });
        }
    }

    private interface MapRead {
        void writeMap() throws IOException;
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

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readMap(dis, () -> {
                resume.putIntoContactInfo(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readMap(dis, () -> {
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
                        resume.putIntoSections(sectionType, new InstitutionSection(readList(dis, () -> new Institution(
                                new Link(dis.readUTF(), checkFiled(dis)),
                                readList(dis, () -> new Institution.Position(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        checkFiled(dis)))))));
                        break;
                }
            });
            return resume;
        }
    }

    private void readMap(DataInputStream dis, MapRead mapRead) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            mapRead.writeMap();
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

    private String checkFiled(DataInputStream dis) throws IOException {
        String urlLink = dis.readUTF();
        return urlLink.equals("null") ? null : urlLink;
    }
}
