package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapStorageByFullNameTest extends AbstractMapStorageTest {
    public MapStorageByFullNameTest(){
        super(new MapStorageByFullName());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, NAME_1);
        storage.update(resume);
        assertSame(resume, storage.get(NAME_1));
    }

    @Test
    public void get() throws Exception {
        assertSame(storage.get("Bob"), oneResume);
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume();
        storage.save(resume);
        assertEquals(4, storage.size());
        assertEquals(resume, storage.get(resume.getFullName()));
    }
}