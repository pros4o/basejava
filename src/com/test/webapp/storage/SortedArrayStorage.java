package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, carriage, searchKey);
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        System.arraycopy(storage, index + 1, storage, index, carriage - index);
        carriage--;
    }

    @Override
    protected void addResumeToStorage(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        int index = Arrays.binarySearch(storage, 0, carriage, searchKey);
        if (index < 0) index = - index - 1;
        storage[index] = resume;
    }
}

