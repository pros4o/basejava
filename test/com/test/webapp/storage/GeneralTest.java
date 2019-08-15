package com.test.webapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageByResumeTest.class,
        MapStorageByUUIDTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        XmlPathStorageTest.class,
        XmlFileStorageTest.class,
        JsonFileStorageTest.class,
        JsonPathStorageTest.class,
        //DataFileStorageTest.class
        DataPathStorageTest.class
})
public class GeneralTest {
}
