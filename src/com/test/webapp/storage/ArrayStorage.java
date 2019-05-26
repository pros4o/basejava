package com.test.webapp.storage;

import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getIndex(String uuid) {
        for (int i = 0; i < carriage; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[carriage - 1];
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage[carriage] = resume;
    }
}
