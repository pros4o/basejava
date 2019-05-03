package com.test.webapp;

import com.test.webapp.model.Resume;
import com.test.webapp.storage.*;

import java.lang.reflect.*;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);
        field.set(r, "new_uuid2");
        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));
    }
}
