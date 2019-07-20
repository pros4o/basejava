package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements SaveStrategy {

    @Override
    public <T1> void doWrite(Resume r, T1 os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream((OutputStream) os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public <T2> Resume doRead(T2 is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream((InputStream) is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
