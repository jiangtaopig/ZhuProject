package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TestThread {
    public static void main(String [] args){
        testFuture();
    }

    private static void testFuture(){
        ExecutorService service = Executors.newCachedThreadPool();
        Task task = new Task();

        //方法1
//        Future<Integer> result = service.submit(task);
//        service.shutdown();//停止接收新任务，原来的任务继续执行完

        //方法2、使用FutureTask

        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        service.submit(futureTask);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程执行");
        System.out.println("子线程是否执行完， isDone = "+futureTask.isDone());
        System.out.println("取消子线程任务"+futureTask.cancel(true) +", 是否取消 ？ "+futureTask.isCancelled());
        try {
            //方法1，则这里改为result.get()
            System.out.println("执行结果 = "+ futureTask.get()); //get是阻塞的，所以会阻塞主主线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");

    }


}

class Task implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println("执行子线程");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i< 100; i++){
            sum += i;
        }
        System.out.println("子线程执行完成");
        return sum;
    }
}
