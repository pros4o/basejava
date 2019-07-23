package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {

    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;

}
