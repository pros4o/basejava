package com.test.webapp.storage;

import java.util.Arrays;

import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    public void clear() {
        Arrays.fill(storage, 0, carriage, null);
        carriage = 0;
    }


    public void save(Resume resume) {
        if ((carriage) >= MAX_SIGE) {
            System.out.println("Attention. Overflow");
            return;
        }

        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume ... " + resume.getUuid() + " already added");
            return;
        }
        storage[carriage] = resume;
        carriage++;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[carriage - 1];
            storage[carriage - 1] = null;
            carriage--;
        } else {
            System.out.println("Resume ... " + uuid + " not found");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, carriage);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Error");
            return;
        }
        storage[index] = resume;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < carriage; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
