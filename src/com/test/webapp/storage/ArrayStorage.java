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

    /**
     * Обнуление массива
     */
    public void clear() {
        Arrays.fill(storage, 0, carriage, null);
        carriage = 0;
    }

    /**
     * Метод для сохранения резюме
     *
     * @param resume - элемент для сохранения
     */
    public void save(Resume resume) {
        if ((carriage + 1) > MAX_SIGE) {
            System.out.println("Attention. Overflow");
            return;
        }

        if (!checkResume(resume)) {
            System.out.println("Resume ... " + resume.getUuid() + " already added");
            return;
        }
        storage[carriage] = resume;
        carriage++;
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
            storage[getIndex(uuid)] = storage[carriage - 1];
            storage[carriage - 1] = null;
            carriage--;
        }
    }

    /**
     * Вывод весь массив
     *
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, carriage);
    }


    /**
     * @return number of resume
     */
    public int size() {
        return carriage;
    }

    /**
     * Проверка на наличие резюме
     *
     * @param resume - резюме для проверки
     */
    private boolean checkResume(Resume resume) {
        return !(getIndex(resume.getUuid()) >= 0);
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
        for (int i = 0; i < carriage; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Потерянный update
     *
     * @param resume - резюме для проверки
     */
    public void update(Resume resume) {
        if (checkResume(resume)) {
            System.out.println("Error");
            return;
        }
        storage[getIndex(resume.getUuid())] = resume;
    }
}
