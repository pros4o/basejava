package com.test.webapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageByResumeTest.class,
        MapStorageByUUIDTest.class
})
public class GeneralTest {
}
