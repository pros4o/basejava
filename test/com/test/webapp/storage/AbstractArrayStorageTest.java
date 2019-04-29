package com.test.webapp.storage;

import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;
import org.junit.*;


public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }


    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        //заполняем

    }
}