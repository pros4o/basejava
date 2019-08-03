package com.test.webapp;

public class MainDeadLock {

    private static int count = 1_000_000;
    private static int result = 1;

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                new Thread() {
                    @Override
                    public void run() {
                        MainDeadLock.dev();
                        System.out.println(getName() + " , " + getState());
                        System.out.println(count);
                    }
                }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        MainDeadLock.inc();
                        System.out.println(getName() + " , " + getState());
                        System.out.println(count);
                    }
                }.start();
            }
        }

    }

    private static void inc() {
        synchronized(LOCK2) {
            count++;
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int factor = 2; factor <= count; factor++) {
                result *= factor;
                synchronized (LOCK1) {
                    count++;
                }
            }
        }
    }

    private static void dev() {
        synchronized (LOCK1) {
            count--;
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int factor = 2; factor <= count; factor++) {
                result *= factor;
                synchronized (LOCK2) {
                    count--;
                }
            }
        }

    }
}
