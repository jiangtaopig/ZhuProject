package daily.yiyuan.com.test_java.multi_thread.lock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/15
 */
public class MyThread2 extends Thread {
    private MyTask myTask;
    public MyThread2(MyTask myTask){
        this.myTask = myTask;
    }
    @Override
    public void run() {
        super.run();
        myTask.doSth("AAA");
    }
}
