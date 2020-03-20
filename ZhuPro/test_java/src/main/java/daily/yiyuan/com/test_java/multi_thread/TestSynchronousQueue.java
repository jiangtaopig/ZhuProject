package daily.yiyuan.com.test_java.multi_thread;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/12
 */
public class TestSynchronousQueue {
    public static void main(String[] args) {


        double val = 1232;
        double a = val / 100.0;
        BigDecimal bigDecimal = new BigDecimal(val);

        NumberFormat numberFormat = new DecimalFormat("0");
        String s = numberFormat.format(bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
        System.out.println("s = "+s);



        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Product(synchronousQueue)
                .start();

//        new Customer(synchronousQueue)
//                .start();
    }

    static class Product extends Thread {
        SynchronousQueue<Integer> queue;

        public Product(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品：" + rand);
                System.out.println("等待三秒后运送出去...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //offer() 往queue里放一个element后立即返回，如果碰巧这个element被另一个thread取走了，offer方法返回true，认为offer成功；否则返回false。
//                boolean offer = queue.offer(rand);
//                System.out.println(offer);
                //put put() 往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。
                try {
                    System.out.println("开始 put");
                    queue.put(rand);
                    System.out.println("put 的数据被移走");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("产品生成完成：" + rand);
            }
        }
    }

    //消息者
    static class Customer extends Thread {
        SynchronousQueue<Integer> queue;

        public Customer(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    //take 取出并且remove掉queue里的element（认为是在queue里的。。。），取不到就 wait。
                    System.out.println("消费了一个产品:" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }
}
