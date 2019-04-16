package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage{
    protected final static int MAX_SIGE = 10_000;

    protected Resume[] storage = new Resume[MAX_SIGE];
    protected int carriage = 0;

    public int size() {
        return carriage;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
