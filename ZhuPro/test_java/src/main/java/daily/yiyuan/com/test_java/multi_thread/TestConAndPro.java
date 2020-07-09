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
                for (int i = 0; i < 1; i++){
                    conAndProService.consumer();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1; i++){
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
            System.out.println(Thread.currentThread().getName() +"发出signal信号");
            // 注意 不是调用了 signal 其他处于 await状态的线程就能执行，而是要等待 lock.unlock()之后才行
            condition.signal(); //注
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName() +"释放了lock");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    public void consumer(){
        try {
            lock.lock();
            while (list.size() == 0){// 注3
                condition.await(); // 注4
            }
            int val = list.pop();
            System.out.println("ThreadName : "+Thread.currentThread().getName()+"*************** consumer val *************** = "+val);
            System.out.println(Thread.currentThread().getName() +"发出signal信号");
            condition.signal();//注4
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName() +"释放了lock");
            lock.unlock();
        }
    }

    // 为什么 wait 或 await 要放在同步块中呢

    // 假设消费者先开始执行，执行完注释3 ,但是并没有执行注释4就发生上下文切换，执行到注释1 ；
    // 因为 list 里面没值 所以往里面 add 然后 执行注释 2，发出 signal，由于此时 消费者 还没有执行 wait 所以signal 无效；
    //再次发生上下文切换，由于注3 之前执行过了，直接 await 了，消费者线程阻塞了；
    // 然后再次执行注释1，由于 list中的数据没有被消费掉，所以执行 await 导致 生产者线程也阻塞了。

}