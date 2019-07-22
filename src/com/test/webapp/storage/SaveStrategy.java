package com.test.webapp.storage;

import com.test.webapp.exception.IOStrategy;
import com.test.webapp.model.Resume;

public interface SaveStrategy {

    <T1> void doWrite(Resume r, T1 os) throws IOStrategy;

    <T2> Resume doRead(T2 is) throws IOStrategy;

}
