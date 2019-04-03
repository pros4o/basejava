//import java.lang.reflect.Array;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int elements = 0;

    /**
     * Обнуление массива
     */
    void clear() {
        //Arrays.fill(storage,null);
        Arrays.fill(storage, 0, elements, null);
        elements = 0;
    }

    /**
     * Метод для сохранения резюме
     *
     * @param r - элемент для сохранения
     */
    void save(Resume r) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
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
    Resume get(String uuid) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].uuid.equals(uuid)) {
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
    void delete(String uuid) {
        for (int i = 0; i < elements; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[elements - 1];
                storage[elements - 1] = null;
                elements--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, elements);
    }

    int size() {
        return elements;
    }
}
