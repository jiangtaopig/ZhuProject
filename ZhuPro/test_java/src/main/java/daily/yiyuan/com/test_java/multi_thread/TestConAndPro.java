package daily.yiyuan.com.test_java.multi_thread;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/25
 * 生产者消费者
 */
public class TestConAndPro {
    public static void main(String[] args) {

        final ConAndProService conAndProService = new ConAndProService();
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    conAndProService.consumer();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    conAndProService.producer();
                }
            }
        }.start();

    }
}


class ConAndProService{
    private final static Stack<Integer> list = new Stack<>(); //list里面只存放一个数据
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void producer(){
        try {
            lock.lock();
            while (list.size() == 1){ //注1
                condition.await();
            }
            int val = new Random().nextInt(10)+1;
            System.out.println("ThreadName : "+Thread.currentThread().getName()+" ...........producer val ........ = "+val);
            list.add(val);
            condition.signal(); //注2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void consumer(){
        try {
            lock.lock();
            while (list.size() == 0){//注3
                condition.await();
            }
            int val = list.pop();
            System.out.println("ThreadName : "+Thread.currentThread().getName()+"*************** consumer val *************** = "+val);
            condition.signal();//注4
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 为什么 wait 或 await 要放在同步块中呢

    // 假设消费者先开始执行，执行完注释3 然后发生上下文切换，执行到注释1 ；
    // 因为 list 里面没值 所以往里面 add 然后 执行注释 2，发出 signal，由于此时 消费者 还没有执行 wait 所以signal 无效；
    //再次发生上下文切换，由于注3 之前执行过了，直接 await 了，消费者线程阻塞了；
    // 然后再次执行注释1，由于 list中的数据没有被消费掉，所以执行 await 导致 生产者线程也阻塞了。

}