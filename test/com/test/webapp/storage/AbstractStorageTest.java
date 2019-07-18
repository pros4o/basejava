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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("J:\\some3");
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final Resume oneResume = new Resume(UUID_1, "Ada");
    protected static final Resume twoResume = new Resume(UUID_2, "Bob");
    protected static final Resume threeResume = new Resume(UUID_3, "Z");
    protected static final Resume resume = new Resume("uuid4", "X");
    {
        ResumeTestData.fillContentResume(oneResume);
        ResumeTestData.fillContentResume(twoResume);
        ResumeTestData.fillContentResume(threeResume);
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
        assertSame(oneResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, "John");
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
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
