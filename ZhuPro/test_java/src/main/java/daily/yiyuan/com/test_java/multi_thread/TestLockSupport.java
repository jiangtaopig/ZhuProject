package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.locks.LockSupport;

public
/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/11/19
 */
class TestLockSupport {

    static Thread t1 = null;
    static Thread t2 = null;
    //unpark函数可以先于park调用，这个正是它们的灵活之处
    public static void main(String[] args) throws InterruptedException {
        t2 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 6; i++){
                    System.out.println("i = "+i);
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LockSupport.park();
                }
            }
        };

        t1 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.unpark(t2);
            }
        };

        t1.start();
        Thread.sleep(30);
        t2.start();


    }
}
