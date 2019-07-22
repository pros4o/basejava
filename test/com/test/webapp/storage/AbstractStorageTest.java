package com.test.webapp.storage;

import com.test.webapp.ResumeTestData;
import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("J:\\some\\basejava\\basejava\\storage");
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final Resume oneResume;
    protected static final Resume twoResume;
    protected static final Resume threeResume;
    protected static final Resume resume;

    static {
        oneResume = ResumeTestData.fillContentResume(UUID_1, "Ada");
        twoResume = ResumeTestData.fillContentResume(UUID_2, "Bob");
        threeResume = ResumeTestData.fillContentResume(UUID_3, "Z");
        resume = ResumeTestData.fillContentResume("uuid4", "X");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(twoResume);
        storage.save(oneResume);
        storage.save(threeResume);
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
    public void getAllSorted() throws Exception {
        List<Resume> sortedList = storage.getAllSorted();
        assertEquals(Arrays.asList(oneResume, twoResume, threeResume), sortedList);
    }

    @Test
    public void get() throws Exception {
        assertEquals(oneResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, "John");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resume);
    }

    @Test
    public void save() throws Exception {
        storage.save(resume);
        assertEquals(4, storage.size());
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(oneResume);
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
