package daily.yiyuan.com.test_java.multi_thread;

public class Utils {

    private Utils(){

    }

    private static Utils mInstance;
    private Object object = new Object();

    public static Utils getInstance(){
        if (mInstance == null){
            synchronized (Utils.class){
                if (mInstance == null){
                    mInstance = new Utils();
                }
            }
        }
        return mInstance;
    }

    public synchronized void showMsg(){
        System.out.println("start msg : thread name = "+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end msg : thread name = "+Thread.currentThread().getName());
    }

    public void waitFor(){
        synchronized (object){
            System.out.println("start waitFor "+Thread.currentThread().getName());
            try {
                System.out.println("waitFor "+Thread.currentThread().getName() + "调用了 object.wait() ");
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("waitFor "+Thread.currentThread().getName() + "获得锁");
        }
    }

    public void notify_For(){
        synchronized (object){
            System.out.println("start notify_For "+Thread.currentThread().getName());
            object.notify();
            System.out.println("notify_For "+Thread.currentThread().getName()+"调用了 notify ");
        }
        System.out.println("notify_For "+Thread.currentThread().getName()+"释放了锁");
    }

}
