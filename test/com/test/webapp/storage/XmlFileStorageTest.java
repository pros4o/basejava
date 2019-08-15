package com.test.webapp.storage;

import com.test.webapp.storage.serializer.XmlStreamSerializer;

public class XmlFileStorageTest extends AbstractStorageTest {
    public XmlFileStorageTest(){
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
