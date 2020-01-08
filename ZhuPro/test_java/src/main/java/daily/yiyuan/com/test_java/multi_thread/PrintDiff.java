package daily.yiyuan.com.test_java.multi_thread;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/26
 */
public class PrintDiff {
    public static void main(String[] args) {

//        printFizzBuzz();

//        printFoobar();

        final ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);

        new Thread(){
            @Override
            public void run() {
                try {
                    zeroEvenOdd.zero(new IntConsumer() {
                        @Override
                        public void accept(int i) {
                            System.out.println(i);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    zeroEvenOdd.odd(new IntConsumer() {
                        @Override
                        public void accept(int i) {
                            System.out.println(i);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    zeroEvenOdd.even(new IntConsumer() {
                        @Override
                        public void accept(int i) {
                            System.out.println(i);
                        }
                    });
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private static void printFoobar() {
        final FooBar fooBar = new FooBar(5);
        new Thread() {
            @Override
            public void run() {
                try {
                    fooBar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("bar");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    fooBar.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void printFizzBuzz() {
        final FizzBuzz fizzBuzz = new FizzBuzz(16);

        new Thread() {
            @Override
            public void run() {
                try {
                    fizzBuzz.fizz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("fizz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    fizzBuzz.buzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("buzz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    fizzBuzz.fizzbuzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("fizzbuzz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    fizzBuzz.number(new IntConsumer() {
                        @Override
                        public void accept(int i) {
                            System.out.println("i = " + i);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

/**
 * leetcode 1195 交替打印字符串 :
 * <p>
 * 如果这个数字可以被 3 整除，输出 "fizz"。
 * 如果这个数字可以被 5 整除，输出 "buzz"。
 * 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
 * 如果都不能的话直接输出数字
 */
class FizzBuzz {
    private int n;
    int cnt = 1;
    private Object object = new Object();

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz". // n % 3 == 0
    public void fizz(Runnable printFizz) throws InterruptedException {

        while (cnt <= n) {
            synchronized (object) {
                if (cnt % 3 == 0 && cnt % 5 != 0) {
                    printFizz.run();
                    cnt++;
                    object.notifyAll();
                } else {
                    object.wait();
                }
            }
        }

    }

    // printBuzz.run() outputs "buzz". n % 5 == 0
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (cnt <= n) {
            synchronized (object) {
                if (cnt % 3 != 0 && cnt % 5 == 0) {
                    printBuzz.run();
                    cnt++;
                    object.notifyAll();
                } else {
                    object.wait();
                }
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz". n %3 == 0 && n%5 == 0
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (cnt <= n) {
            synchronized (object) {
                if (cnt % 3 == 0 && cnt % 5 == 0) {
                    printFizzBuzz.run();
                    cnt++;
                    object.notifyAll();
                } else {
                    object.wait();
                }
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (cnt <= n) {
            synchronized (object) {
                if (cnt % 3 != 0 && cnt % 5 != 0) {
                    printNumber.accept(cnt);
                    cnt++;
                    object.notifyAll();
                } else {
                    object.wait();
                }
            }
        }
    }
}

//.......................................................leetcode 1115.................................

/**
 * 交替打印 foo bar
 * n = 1, 输出 foobar
 * n = 2, 输出 foobarfoobar
 * 要求一个线程输出 foo 另一个线程输出 bar
 */


class FooBar {
    private int n;
    private AtomicBoolean showFoo = new AtomicBoolean(true);
    private Object lock = new Object();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (!showFoo.get()) {
                    lock.wait();
                }
                printFoo.run();
                showFoo.getAndSet(false);
                lock.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (showFoo.get()) {
                    lock.wait();
                }
                printBar.run();
                showFoo.set(true);
                lock.notify();
            }
        }
    }
}

//.......................................输入为n 有3个线程 分别输出0 奇数 偶数............................

/**
 * 输入 n = 3 , 输出 010203
 * 输入 n = 2，输出 0102
 */
class ZeroEvenOdd {
    private int n;
    private int num = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (num % 2 != 0) {
                    this.wait();
                }
                printNumber.accept(0);
                num++;
                this.notifyAll();
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int j = 2; j <= n; j = j + 2) {//偶数从2 开始 ，每次加2
            synchronized (this) {
                while (num % 2 == 0 || num % 4 != 3) {
                    this.wait();
                }
                printNumber.accept(j);
                num++;
                this.notifyAll();
            }
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int j = 1; j <= n; j = j + 2) {
            synchronized (this) {
                while (num % 2 == 0 || num % 4 != 1) {
                    this.wait();
                }
                printNumber.accept(j);
                num++;
                this.notifyAll();
            }
        }
    }

}
