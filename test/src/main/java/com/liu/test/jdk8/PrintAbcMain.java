package com.liu.test.jdk8;

public class PrintAbcMain {
    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();
        Thread threadA = new Thread(() ->{
            int count = 0;
            while (count < 10){
                synchronized (printABC){
                    while(printABC.getStatus() %3 != 0){
                        try {
                            printABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printABC.print("A");
                    count++;
                    printABC.setStatus(printABC.getStatus() + 1);
                    printABC.notifyAll();
                }
            }

        });


        Thread threadB = new Thread(() ->{
            int count = 0;
            while (count < 10){
                synchronized (printABC){
                    while(printABC.getStatus() %3 != 1){
                        try {
                            printABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printABC.print("B");
                    count++;
                    printABC.setStatus(printABC.getStatus() + 1);
                    printABC.notifyAll();
                }
            }

        });

        Thread threadC = new Thread(() ->{
            int count = 0;
            while (count < 10){
                synchronized (printABC){
                    while(printABC.getStatus() %3 != 2){
                        try {
                            printABC.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printABC.print("C");
                    count++;
                    printABC.setStatus(printABC.getStatus() + 1);
                    printABC.notifyAll();
                }
            }

        });
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
