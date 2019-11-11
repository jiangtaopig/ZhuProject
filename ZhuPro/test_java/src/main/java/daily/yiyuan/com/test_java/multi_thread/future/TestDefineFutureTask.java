package daily.yiyuan.com.test_java.multi_thread.future;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/4
 */
public class TestDefineFutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyFutureTask<Integer> myFutureTask = new MyFutureTask<>(new MyCallable());
//        setTaskThread(myFutureTask);
        new Thread(myFutureTask)
                .start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        if (!myFutureTask.isCancelled()){
//            myFutureTask.cancel(true);
//        }

//        myFutureTask.get();
        try {
            int res = myFutureTask.get(5, TimeUnit.SECONDS);
            System.out.println("res = "+res);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给FutureTask 的 runner 设置一个值
     * @param myFutureTask
     */
    private static void setTaskThread(MyFutureTask<Integer> myFutureTask) {
        try {
            Field field = myFutureTask.getClass().getDeclaredField("runner");
            field.setAccessible(true);
            field.set(myFutureTask, new Thread("hhh"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
 class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("执行子线程操作 time " + System.currentTimeMillis());
        Thread.sleep(3 * 1000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        System.out.println("sum = "+sum);
        return sum;
    }
}
