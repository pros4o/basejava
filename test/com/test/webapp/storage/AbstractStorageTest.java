package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String NAME_1 = "Bob";
    protected static final String NAME_2 = "Ada";
    protected static final String NAME_3 = "Z";
    protected static final Resume oneResume = new Resume(UUID_1, NAME_1);
    protected static final Resume twoResume= new Resume(UUID_2, NAME_2);
    protected static final Resume threeResume = new Resume(UUID_3, NAME_3);
    protected Resume[] array;
    protected List<Resume> list;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        array = new Resume[]{oneResume, twoResume, threeResume};
        storage.save(oneResume);
        storage.save(twoResume);
        storage.save(threeResume);
        list = new ArrayList<>(3);
        list.add(twoResume);
        list.add(oneResume);
        list.add(threeResume);
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
        assertEquals(list, storage.getAllSorted());
    }

    @Test
    public void get() throws Exception {
        assertSame(storage.get("uuid1"), oneResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid10"));
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume();
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
