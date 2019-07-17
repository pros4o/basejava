package com.test.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File searchFilePath = new File("src/com/test/webapp");
        int deep = 0;
        showDirecoty(searchFilePath, deep);
    }

    private static void showDirecoty(File dir, int deep) {
        File[] innerFile = dir.listFiles();
        for (File some : innerFile) {
            if (some.isDirectory()) {
                System.out.println(createMessage(deep, some));
                showDirecoty(some, ++deep);
                deep--;
            } else if (some.isFile()) {
                System.out.println(createMessage(deep, some));
            }
        }
    }
    private static String createMessage(int deep, File file) {
        int i = 0;
        StringBuilder message = new StringBuilder();
        while (i < deep) {
            message.append("\t");
            i++;
        }
        message.append(file.getName());
        return message.toString();
    }
}
