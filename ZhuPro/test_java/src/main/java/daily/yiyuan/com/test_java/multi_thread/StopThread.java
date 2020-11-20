package daily.yiyuan.com.test_java.multi_thread;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/27
 */
public class StopThread {

    // interrupt 只能中断阻塞状态(wait, sleep 等)下的线程。
    public static void main(String[] args) {
//        Thread firstThread = new Thread(new MyFirstThread());
//        firstThread.start();
//
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        firstThread.interrupt(); //会中断阻塞线程

//        Thread secondThread = new SecondThread();
//        secondThread.start();
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        secondThread.interrupt(); //不会中断非阻塞的线程

//        ThirdThread thirdThread = new ThirdThread(); //使用volatile关键字 来停止线程
//        thirdThread.start();
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thirdThread.setStop(true);

        FourthThread fourthThread = new FourthThread();
//        fourthThread.start();

//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("开始调用 interrupt 方法");
//        fourthThread.interrupt();

        Object object = new Object();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println(".....start....");
                try {
                    System.out.println(".....wait....");
                    synchronized (object){
                        object.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(".....catch....");
                }
            }
        };

        thread.start();

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();


    }
}

class MyFirstThread implements Runnable{
    @Override
    public void run() {
        System.out.println("开始 MyFirstThread");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("被中断了");
        }
        System.out.println("MyFirstThread run 方法执行完毕");
    }
}

class SecondThread extends Thread{ // interrupt 不能中断非阻塞状态下的线程
    @Override
    public void run() {
        super.run();
        int i = 0;
        while (i < Integer.MAX_VALUE){
            System.out.println("i = "+i++);

        }
        System.out.println("SecondThread run 方法执行完毕");
    }
}

class ThirdThread extends Thread{
    private volatile boolean stop;
    @Override
    public void run() {
        super.run();

        int i = 0;
        while (!stop && i < Integer.MAX_VALUE){
            System.out.println("i = "+i++);
        }
        System.out.println("ThirdThread run 方法执行完毕");
    }

    public void setStop(boolean stop){
        this.stop = stop;
    }
}

class FourthThread extends Thread{
    @Override
    public void run() {
        super.run();

        int i = 0;
        while (!isInterrupted() && i < Integer.MAX_VALUE){
            System.out.println("i = "+i++);
        }
        System.out.println("FourthThread run 方法执行完毕");
    }
}
