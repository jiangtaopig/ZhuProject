package daily.yiyuan.com.test_java.multi_thread.lock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/15
 */
public class MyTask {
    public void doSth(String action){
        synchronized (action){
            while (true){
                System.out.println(Thread.currentThread().getName()+"start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

