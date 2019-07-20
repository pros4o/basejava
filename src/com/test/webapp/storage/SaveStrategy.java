package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.io.IOException;
import java.io.OutputStream;

public interface SaveStrategy {

    <T1> void doWrite(Resume r, T1 os) throws IOException;

    <T2> Resume doRead(T2 is) throws IOException;

}
