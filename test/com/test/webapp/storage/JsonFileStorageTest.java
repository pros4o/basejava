package com.test.webapp.storage;

import com.test.webapp.storage.serializer.JsonStreamSerializer;

public class JsonFileStorageTest extends AbstractStorageTest {
    public JsonFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
