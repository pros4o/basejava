package com.test.webapp.storage;

import java.util.Arrays;
import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int elements = 0;

    /**
     * Обнуление массива
     */
    public void clear() {
        Arrays.fill(storage, 0, elements, null);
        elements = 0;
    }

    /**
     * Метод для сохранения резюме
     *
     * @param r - элемент для сохранения
     */
    public void save(Resume r) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                System.out.println("Элемент уже существует");
                return;
            }
        }
        storage[elements] = r;
        elements++;
    }

    /**
     * @param uuid - метка резюме
     * @return резюме по его uuid
     */
    public Resume get(String uuid) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    /**
     * Удаляет элемент из массива по его uuid
     *
     * @param uuid - метка резюме
     */
    public void delete(String uuid) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[elements - 1];
                storage[elements - 1] = null;
                elements--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, elements);
    }

    public int size() {
        return elements;
    }
}
