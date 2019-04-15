package com.test.webapp.storage;

import java.util.Arrays;

import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int MAX_SIGE = 10_000;
    private Resume[] storage = new Resume[MAX_SIGE];
    private int carriage = 0;


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


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume ..." + uuid + " not found");
            return null;
        } else {
            return storage[index];
        }
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
        return Arrays.copyOf(storage, carriage);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Error");
            return;
        }
        storage[index] = resume;
    }

    public int size() {
        return carriage;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < carriage; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
