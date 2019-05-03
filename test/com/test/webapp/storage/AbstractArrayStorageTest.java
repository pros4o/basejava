package com.test.webapp.storage;

import com.test.webapp.exception.*;
import com.test.webapp.model.Resume;
import org.junit.*;

import static org.junit.Assert.*;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Resume[] array = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));

    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        assertArrayEquals(array, storage.getAll());
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        storage.update(resume);
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid10"));
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume());
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume("uuid1"));
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

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid10");
    }
}