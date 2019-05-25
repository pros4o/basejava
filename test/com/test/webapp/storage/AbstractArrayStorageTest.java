package com.test.webapp.storage;

import com.test.webapp.exception.*;
import com.test.webapp.model.Resume;
import org.junit.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
                storage.save(new Resume("resume number " + i));
            }
        } catch (StorageException ex) {
            Assert.fail("Cannot fill storage");
        }
        storage.save(new Resume("last"));
    }
}