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
        showDirectory(searchFilePath);
    }

    private static void showDirectory(File dir) {
        File[] innerFile = dir.listFiles();
        if (innerFile != null) {
            for (File file : innerFile) {
                if (file.isDirectory()) {
                    System.out.println(createMessage("\t", file));
                    showDirectory(file);
                } else if (file.isFile()) {
                    System.out.println(createMessage("\t\t", file));
                }
            }
        } else throw new IllegalArgumentException(innerFile.toString() + " is not exist");
    }
    private static String createMessage(String symbol, File file) {
        StringBuilder message = new StringBuilder();
        message.append(symbol);
        message.append(file.getName());
        return message.toString();
    }
}
