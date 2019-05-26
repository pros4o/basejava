package com.test.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(array.length, storage.getAll().length);

        int countTrue = 0;
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < storage.getAll().length; j++)
                if (array[i].equals(storage.getAll()[j])) {
                    countTrue++;
                    break;
                }
        assertTrue(countTrue == 3);
    }
}
