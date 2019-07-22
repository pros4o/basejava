package com.test.webapp.storage;

import com.test.webapp.exception.IOStrategy;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements SaveStrategy {

    @Override
    public <T1> void doWrite(Resume r, T1 os) {
        try (ObjectOutputStream oos = new ObjectOutputStream((OutputStream) os)) {
            oos.writeObject(r);
        } catch (IOException e) {
            throw new IOStrategy("Error write resume", null, e);
        }
    }

    @Override
    public <T2> Resume doRead(T2 is) {
        try (ObjectInputStream ois = new ObjectInputStream((InputStream) is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new IOStrategy("Error read resume", null, e);
        }
    }
}
