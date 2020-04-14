package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class Print2NumAnd1Char {
   static Thread t1 = null;
   static Thread t2 = null;
    public static void main(String[] args) {
        AtomicBoolean isNum = new AtomicBoolean(true);
        final String[] number = {"1", "2", "3", "4", "5", "6", "7", "8"};
        final String[] chars = {"a", "b", "c", "d"};

        //下面的方法输出12a34b56c78d
        new PrintNumberThread(number, isNum).start();
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new PrintCharThread(chars, isNum).start();

        printCharAndNum();
    }

    /**
     *   //输出a1b2c3d4
     */
    private static void printCharAndNum() {

        final String[] number1 = {"1", "2", "3", "4"};
        final String[] chars1 = {"a", "b", "c", "d"};

        t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0, len = number1.length; i < len; i++) {
                    System.out.println("num ...i = "+i);
                    LockSupport.park(); //阻塞当前线程
                    System.out.println(number1[i]);
                    LockSupport.unpark(t2); //唤起线程t2
                }
            }
        };

        t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0, len = chars1.length; i < len; i++) {
                    System.out.println("char ...i = "+i);
                    System.out.println(chars1[i]);
                    LockSupport.unpark(t1);//唤起线程t1
                    LockSupport.park();//阻塞当前线程
                }
            }
        };

        t1.start();
        t2.start();
    }

}

class PrintNumberThread extends Thread {

    private String[] numbs;
    private AtomicBoolean isNum;

    public PrintNumberThread(String[] numbs, AtomicBoolean isNum) {
        this.numbs = numbs;
        this.isNum = isNum;
    }

    @Override
    public void run() {
        printNum();
    }

    private void printNum() {
        int cnt = 0;
        for (String val : numbs) {
            while (!isNum.get()) {
                //使当前线程从执行状态（运行状态）变为可执行态（就绪状态）。cpu会从众多的可执行态里选择，也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的
                Thread.yield();
            }

            System.out.print(val);
            cnt++;
            if (cnt == 2) {
                cnt = 0;
                isNum.set(false);
            }
        }
//        isNum.set(false);
    }
}

class PrintCharThread extends Thread {
    private String[] chars;
    private AtomicBoolean isNum;

    public PrintCharThread(String[] chars, AtomicBoolean isNum) {
        this.chars = chars;
        this.isNum = isNum;
    }

    @Override
    public void run() {
        printChars();
    }

    private void printChars() {
        int cnt = 0;
        for (String val : chars) {
            while (isNum.get()) {
                Thread.yield();
            }

            System.out.print(val);
            cnt++;
            if (cnt == 1) {
                cnt = 0;
                isNum.set(true);
            }
        }
//        isNum.set(true);
    }
}
