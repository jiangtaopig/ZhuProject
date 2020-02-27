package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/26
 */
public class TestSemaphore {
    public static void main(String[] args) {
        System.out.println("----------------------------------测试Semaphore---------------------------------");
        testSemaphore();

//        testZeroOddEven();

//        testCyclicBarrier();
//        testH2O();

//        printCharAndNum();


    }

    private static void printCharAndNum() {
        final NumberChar numberChar = new NumberChar();

        final int[] nums = {1, 2, 3, 4};
        final char [] chars = {'a', 'b'};

        for (int i = 0; i < nums.length; i++){
            final int tmp = i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(){
                @Override
                public void run() {
                    numberChar.printNum(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(nums[tmp]);
                        }
                    });
                }
            }.start();
        }

        for (int i = 0; i < chars.length; i++){
            final int tmp = i;
            new Thread(){
                @Override
                public void run() {
                    numberChar.printChar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(chars[tmp]);
                        }
                    });
                }
            }.start();
        }
    }

    private static void testH2O() {
        final H2O h2O = new H2O();
        String ss = "HHOHOOHHH";
        for (int i = 0; i < ss.length(); i++) {
            char ch = ss.charAt(i);
            if (ch == 'H') {
                printH(h2O);
            } else if (ch == 'O') {
                printO(h2O);
            }
        }
    }

    private static void printO(final H2O h2O) {
        new Thread() {
            @Override
            public void run() {
                try {
                    h2O.oxygen(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("O");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void printH(final H2O h2O) {
        new Thread() {
            @Override
            public void run() {
                try {
                    h2O.hydrogen(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("H");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void testCyclicBarrier() {
        System.out.println("-------------------------仅测试CyclicBarrier------------------------------------------");
        //其参数 3 表示屏障拦截的线程数量为3，每个线程调用await()方法告诉CyclicBarrier我已经到达了屏障，然后该线程被阻塞。直到达到屏障的数量等于3时，所有的线程才会继续执行下去
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {//最后一个到达的线程会执行下面的方法
                System.out.println("3个线程都到达了 ThreadName" + Thread.currentThread().getName() + ".... run ........." + System.currentTimeMillis());
            }
        });

        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Thread " + Thread.currentThread().getName() + " start : " + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " end : " + System.currentTimeMillis());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Thread " + Thread.currentThread().getName() + " start : " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " end : " + System.currentTimeMillis());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Thread " + Thread.currentThread().getName() + " start : " + System.currentTimeMillis());
                try {
                    Thread.sleep(3000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " end : " + System.currentTimeMillis());
            }
        }.start();
    }

    private static void testSemaphore() {

        final ZjtService zjtService = new ZjtService();

        new Thread() {
            @Override
            public void run() {
                zjtService.test();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                zjtService.test();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                zjtService.test();
            }
        }.start();
    }

    private static void testZeroOddEven() {
        final ZeroOddEven zeroOddEven = new ZeroOddEven(6);
        new Thread() {
            @Override
            public void run() {
                zeroOddEven.printZero(new IntConsumer() {
                    @Override
                    public void accept(int i) {
                        System.out.println(i);
                    }
                });
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                zeroOddEven.printOdd(new IntConsumer() {
                    @Override
                    public void accept(int i) {
                        System.out.println(i);
                    }
                });
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                zeroOddEven.printEven(new IntConsumer() {
                    @Override
                    public void accept(int i) {
                        System.out.println(i);
                    }
                });
            }
        }.start();
    }


}

class ZjtService {
    private Semaphore semaphore = new Semaphore(1);

    public void test() {
        System.out.println("ThreadName " + Thread.currentThread().getName() + " test ");
        try {
            semaphore.acquire();
            System.out.println("ThreadName " + Thread.currentThread().getName() + ", start " + System.currentTimeMillis());
            Thread.sleep(2000);
            System.out.println("ThreadName " + Thread.currentThread().getName() + ", end " + System.currentTimeMillis());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ThreadName " + Thread.currentThread().getName() + " semaphore release");
            semaphore.release();
        }
    }
}

/**
 * 输入为n ， 有3个线程 分别输出0 奇数 偶数
 * 输入 n = 3 , 输出 010203
 * 输入 n = 2，输出 0102
 */
class ZeroOddEven {
    private int n;
    private Semaphore zeroSemaphore;
    private Semaphore oddSemaphore;
    private Semaphore evenSemaphore;

    public ZeroOddEven(int n) {
        this.n = n;
        zeroSemaphore = new Semaphore(1); // 执行完 acquire() 方法后， 那么相应的计数器减1后等于0， 那么再次调用 acquire()后，那么这个线程就会被阻塞
        oddSemaphore = new Semaphore(0);//如果 初始值为0，那么 执行完 acquire()方法后，由于计数器为0， 当前线程阻塞，后续的流程无法执行，直到 调用 semaphore.release()
        evenSemaphore = new Semaphore(0);
    }

    public void printZero(IntConsumer printZero) {
        for (int i = 1; i <= n; i++) {
            try {
                zeroSemaphore.acquire();
                printZero.accept(0);
                if ((i & 1) == 0) {//偶数
                    evenSemaphore.release();
                } else {//奇数
                    oddSemaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printOdd(IntConsumer printOdd) {
        for (int i = 1; i <= n; i += 2) {
            try {
                oddSemaphore.acquire();
                printOdd.accept(i);
                zeroSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printEven(IntConsumer printEven) {
        for (int i = 2; i <= n; i += 2) {
            try {
                evenSemaphore.acquire();
                printEven.accept(i);
                zeroSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//------------------------------------------H2O-----------------------------------------------

/**
 * 输入 OHH 输出 OHH HHO HOH 都可以
 * 输入 OOHHHH, 输出 HHOHHO HHOHOH HHOOHH 等，每3个字符 必须要组成H2O，不管O和H的顺序
 */
class H2O {
    private CyclicBarrier cyclicBarrier;
    private Semaphore oS;
    private Semaphore hS;

    public H2O() {
        cyclicBarrier = new CyclicBarrier(3);
        oS = new Semaphore(1);
        hS = new Semaphore(2);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hS.acquire();
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        releaseHydrogen.run();
        hS.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oS.acquire();
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        releaseOxygen.run();
        oS.release();
    }

}

//------------------------------------------------每输出2个数字必须要输出一个字母--------------------------------------------

/**
 * 12a34b56c
 */
class NumberChar {
    private CyclicBarrier cyclicBarrier;
    private Semaphore numSemaphore;
    private Semaphore charSemaphore;

    public NumberChar() {
        cyclicBarrier = new CyclicBarrier(3);
        numSemaphore = new Semaphore(2);
        charSemaphore = new Semaphore(0);
    }

    public void printNum(Runnable printNum) {
        try {
            numSemaphore.acquire();
            cyclicBarrier.await();
            printNum.run();
            charSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void printChar(Runnable printChar) {
        try {
            cyclicBarrier.await();
            charSemaphore.acquire();
            printChar.run();
            numSemaphore.release(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}

