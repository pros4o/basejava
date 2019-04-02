import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int elements = 0;
    /**
     * Обнуление массива
     */
    void clear() {
        Arrays.fill(storage,null);
        elements = 0;
    }

    /**
     * Метод для сохранения резюме
     * @param r - элемент для сохранения
     */
    void save(Resume r) {
        for(int cariage = 0; cariage < storage.length - 1; cariage++) {
            if (storage[cariage] == null) {
                storage[cariage] = r;
                elements++;
                break;
            }
        }
    }

    /**
     *
     * @param uuid - метка резюме
     * @return резюме по его uuid
     */
    Resume get(String uuid) {
        Resume temp = new Resume();
        temp.uuid = uuid;
        for (int i=0; i<elements - 1;i++) {
            if(storage[i].uuid.equals(uuid)) {
                temp = storage[i];
            }
        }
        System.out.println("Такого элемента нету!");
        return temp;
    }

    /**
     * Удаляет элемент из массива по его uuid
     * @param uuid - метка резюме
     */
    void delete(String uuid) {
        int index = 0;

        for (int i = 0; i < elements - 1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
            }
        }
        if (index == elements) {
            storage[index] = null;
        } else {
            for (int i = index; index + i <= elements; i++)
                storage[i] = storage[i + 1]; //until index +1 <= elements;
        }
        elements--;
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
