package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Semaphores {
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        //crearea si initializarea semafoarelor
        Semaphore sem1 = new Semaphore(0);
        Semaphore sem2 = new Semaphore(0);
        Semaphore sem3 = new Semaphore(0);
        Semaphore sem5 = new Semaphore(0);
        Semaphore sem6 = new Semaphore(0);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Work("1",sem1);//nu depinde de nimeni, il pornim simplu
            }

        }).start();
        new Thread(new Runnable() {
            @Override
            //3 depends 1
            public void run() {
                Waitnwork("3",sem1,sem3);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            //2 depends 3
            public void run() {
                Waitnwork("2",sem3,sem2);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            //5,6 depends 2
            public void run() {
                Waitnwork("5",sem2,sem5);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            //6 depends 2
            public void run() {
                Waitnwork("6",sem5,sem6);
            }

        }).start();
    }

    public static void Waitnwork(String id,Semaphore semToAquire,Semaphore semToRelease){
        // Primește id-ul threadului, semaforului care trebuie sa aștepte și semaforului care trebuie sa elibereze
        try {
            semToAquire.acquire();
            Work(id,semToRelease);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Work(String id, Semaphore semToRelease){ //id thread-ului,semaforul care elibereaza
        int millis = random.nextInt(10);
        try {
            Thread.sleep(millis);
            System.out.printf("Thread nr. %s\n",id);
            semToRelease.release(); // semaforul eliberează următorul fir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

