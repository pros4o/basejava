package com.test.webapp.storage;

import com.test.webapp.exception.*;
import com.test.webapp.model.Resume;
import org.junit.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private Resume[] array = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};

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
        assertEquals("uuid1", storage.get(UUID_1).toString());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        storage.update(resume);
        assertEquals(storage.get("uuid4"), storage.get(resume.getUuid()));
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume());
        assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }


    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        try {
            for(int i = 4; i <= 10_000; i++) {
                storage.save(new Resume("resume number " + i));
            }
        } catch (Exception ex) {
            Assert.fail("Cannot fill storage");
        }
        storage.save(new Resume("last"));
    }
}