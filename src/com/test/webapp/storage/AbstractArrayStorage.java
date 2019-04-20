package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int MAX_SIGE = 10_000;

    protected Resume[] storage = new Resume[MAX_SIGE];
    protected int carriage = 0;

    public int size() {
        return carriage;
    }

    public void clear() {
        Arrays.fill(storage, 0, carriage, null);
        carriage = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, carriage);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Error");
            return;
        }
        storage[index] = resume;
    }

    public void save(Resume resume) {
        if ((carriage) >= MAX_SIGE) {
            System.out.println("Attention. Overflow");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Resume ... " + resume.getUuid() + " already added");
            return;
        }
        addResume(resume, index);
        carriage++;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
        } else {
            System.out.println("Resume ... " + uuid + " not found");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);
}
