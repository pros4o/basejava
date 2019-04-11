package com.test.webapp.storage;

import java.util.Arrays;

import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int MAX_SIGE = 10000;//не забыть вернуть на 10.000//Максимальное количество резюме
    private Resume[] storage = new Resume[MAX_SIGE];
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
        if ((elements + 1) > MAX_SIGE) {
            System.out.println("Attention. Overflow");
            return;
        }

        if (checkResume(r)) {
            storage[elements] = r;
            elements++;
        }
    }

    /**
     * @param uuid - метка резюме
     * @return резюме по его uuid
     */
    public Resume get(String uuid) {
        if (!checkResume(uuid)) {
            return null;
        } else {
            return storage[getIndex(uuid)];
        }
    }

    /**
     * Удаляет элемент из массива по его uuid
     *
     * @param uuid - метка резюме
     */
    public void delete(String uuid) {
        if (checkResume(uuid)) {
            storage[getIndex(uuid)] = storage[elements - 1];
            storage[elements - 1] = null;
            elements--;
        }
    }

    /**
     * Вывод весь массив
     *
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, elements);
    }

    /**
     * @return number of resume
     */
    public int size() {
        return elements;
    }

    /**
     * Проверка на наличие резюме
     *
     * @param r - резюме для проверки
     */
    private boolean checkResume(Resume r) {
        if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume ... " + r.getUuid() + " already added");
            return false;
        }
        return true;
    }

    /**
     * Проверка на наличие резюме
     *
     * @param uuid инд. резюме
     * @return возвращает true, если резюме есть в хранилке
     */
    private boolean checkResume(String uuid) {
        if (getIndex(uuid) >= 0) return true;
        System.out.println("Resume ... " + uuid + " not found");
        return false;
    }

    /**
     * В поисках индекса.
     *
     * @param uuid инд. резюме
     * @return возвращает индекс, если нету, то -1
     */
    private int getIndex(String uuid) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
