package com.test.webapp.storage;
//!! не забыть отформотировать его
//
import java.util.Arrays;
import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int MAX_SIGE = 2;//не забыть вернуть на 10.000//Максимальное количество резюме
    private Resume[] storage = new Resume[MAX_SIGE];
    private int elements = 0; //количество резюме в хранилке

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
        if((elements + 1) > MAX_SIGE) {
            System.out.println("Attention. Overflow");
            return;
        }
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
        if (!checkResume(uuid)) {
            System.out.println("Resume ... " + uuid + " not found");
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
        for (int i = 0; i < elements; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[elements - 1];
                storage[elements - 1] = null;
                elements--;
            }
        }
    }

    /**
     * Вывод весь массив
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
     * @param r
     * @return
     */
    private boolean checkResume(Resume r) {
        boolean flag = false;
        if (getIndex(r.getUuid()) >= 0) flag = true;
        return flag;
    }

    /**
     * Проверка на наличие резюме
     * @param uuid
     * @return
     */
    private boolean checkResume(String uuid) {
        boolean flag = false;
        if (getIndex(uuid) >= 0) flag = true;
        return flag;
    }

    /**
     * В поисках индекса.
     * @param uuid
     * @return
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
