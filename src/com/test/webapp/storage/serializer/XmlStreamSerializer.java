package com.test.webapp.storage.serializer;

import com.test.webapp.model.*;
import com.test.webapp.storage.IOStrategy;
import com.test.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements IOStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Institution.class, Link.class,
                InstitutionSection.class, SimpleTextSection.class, MarkedSection.class, Institution.Position.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(r);
        }
    }
}
