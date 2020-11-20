package daily.yiyuan.com.test_java.multi_thread;


import java.util.concurrent.Semaphore;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/11/20
 */
public class TestFoo {

    public static void main(String[] args) throws InterruptedException {

//        Thread threadA = new Thread("A"){
//            @Override
//            public void run() {
//
//                System.out.println("A SLEEP");
//                try {
//                    Thread.sleep(2_000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("A over");
//            }
//        };
//
//        Thread threadB = new Thread("B"){
//            @Override
//            public void run() {
//                try {
//                    System.out.println("B JOIN ");
//                    threadA.join();
//                    System.out.println("B over");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        threadB.start();
//        Thread.sleep(40);
//        threadA.start();

        doFoo();

    }

    private static void doFoo() throws InterruptedException {
        Foo foo = new Foo();

        Thread threadA = new Thread("A"){
            @Override
            public void run() {
                try {
                    foo.first(() -> System.out.println("first"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadB = new Thread("B"){
            @Override
            public void run() {

                try {
                    foo.second(() -> System.out.println("second"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadC = new Thread("C"){
            @Override
            public void run() {
                try {
                    foo.third(() -> System.out.println("third"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadB.start();
        Thread.sleep(30);
        threadC.start();
        threadA.start();
    }
}

class Foo{
    // Semaphore 最重要的场景就是按顺序执行
    Semaphore second = new Semaphore(0);
    Semaphore third = new Semaphore(0);
    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        second.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        second.acquire();
        printSecond.run();
        third.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        third.acquire();
        printThird.run();
    }
}
