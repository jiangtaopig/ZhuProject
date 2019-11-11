package daily.yiyuan.com.test_java.multi_thread.future;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

import sun.misc.Unsafe;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/4
 */
public class MyFutureTask<V> implements RunnableFuture<V> {
    /**
     *    NEW -> COMPLETING -> NORMAL --------------------------------------- 正常执行结束的流程
     *     NEW -> COMPLETING -> EXCEPTIONAL ---------------------执行过程中出现异常的流程
     *     NEW -> CANCELLED -------------------------------------------被取消，即调用了cancel(false)
     *     NEW -> INTERRUPTING -> INTERRUPTED -------------被中断，即调用了cancel(true)
     */

    private volatile int state;
    private static final int NEW = 0;            //初始状态，还未开始执行
    private static final int COMPLETING = 1;    //执行中
    private static final int NORMAL = 2;        //任务正常执行完成
    private static final int EXCEPTIONAL = 3;   //执行中发送异常
    private static final int CANCELLED = 4;     //调用 cancel(false) 的状态
    private static final int INTERRUPTING = 5;  //调用 cancel(true) 的状态, 这是一个中间状态
    private static final int INTERRUPTED = 6;   //调用cancel(true)取消异步任务，会调用interrupt()中断线程的执行，然后状态会从INTERRUPTING变到INTERRUPTED

    /**
     * callable 任务结束后会置为null
     */
    private Callable<V> callable;  //
    /**
     *  用来保存计算任务的返回结果，或者执行过程中抛出的异常。
     */
    private Object outcome; // non-volatile, protected by state reads/writes
    /**
     * 指向当前在运行Callable任务的线程
     */
    private volatile Thread runner;
    /**
     * FutureTask的内部类，表示一个阻塞队列，如果任务还没有执行结束，那么调用get()获取结果的线程会阻塞，这些线程会放在这个队列中
     */
    private volatile MyFutureTask.WaitNode waiters;

    /**
     * Returns result or throws exception for completed task.
     *
     * @param s completed state value
     */
    @SuppressWarnings("unchecked")
    private V report(int s) throws ExecutionException {
        Object x = outcome;
        if (s == NORMAL) //如果是任务完成状态，则直接返回任务执行结果
            return (V) x;
        if (s >= CANCELLED)//如果是任务取消了，则抛出 CancellationException
            throw new CancellationException();
        throw new ExecutionException((Throwable) x); //执行任务时发生了异常，抛出执行异常
    }

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     * @param callable the callable task
     * @throws NullPointerException if the callable is null
     */
    public MyFutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       // ensure visibility of callable
    }

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Runnable}, and arrange that {@code get} will return the
     * given result on successful completion.
     *
     * @param runnable the runnable task
     * @param result   the result to return on successful completion. If
     *                 you don't need a particular result, consider using
     *                 constructions of the form:
     *                 {@code Future<?> f = new FutureTask<Void>(runnable, null)}
     * @throws NullPointerException if the runnable is null
     */
    public MyFutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
    }

    public boolean isCancelled() {
        return state >= CANCELLED;
    }

    public boolean isDone() {
        return state != NEW;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        System.out.println("cancel state = "+state);
        //判断state是否为NEW，如果不是NEW，说明任务已经结束或者被取消了，该方法会执行返回false
        if (!(state == NEW &&
                U.compareAndSwapInt(this, STATE, NEW,
                        mayInterruptIfRunning ? INTERRUPTING : CANCELLED))) //设置 state 状态， 如果 mayInterruptIfRunning 为true 设置state 状态为INTERRUPTING
            return false;
        try {    // in case call to interrupt throws exception
            System.out.println("cancel ......mayInterruptIfRunning..."+mayInterruptIfRunning);
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null)
                        t.interrupt(); //中断正在执行任务的线程
                } finally { // final state
                    U.putOrderedInt(this, STATE, INTERRUPTED); //设置 state 为 INTERRUPTED 即已中断状态
                }
            }
        } finally {
            finishCompletion();//唤醒等待返回结果的线程，释放资源
        }
        return true;
    }

    /**
     *  获取任务执行的结果，会抛出 CancellationException 异常
     */
    public V get() throws InterruptedException, ExecutionException {
        int s = state;
        System.out.println("get s = "+s);
        if (s <= COMPLETING) //如果当前的状态是小于等于正在执行中的状态即任务没有完成，那么就阻塞住当前线程，否则直接 report 结果
            s = awaitDone(false, 0L);
        return report(s);
    }

    /**
     * @throws CancellationException {@inheritDoc}
     */
    public V get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (unit == null)
            throw new NullPointerException();
        int s = state;
        if (s <= COMPLETING &&
                (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING)
            throw new TimeoutException();
        return report(s);
    }

    /**
     * Protected method invoked when this task transitions to state
     * {@code isDone} (whether normally or via cancellation). The
     * default implementation does nothing.  Subclasses may override
     * this method to invoke completion callbacks or perform
     * bookkeeping. Note that you can query status inside the
     * implementation of this method to determine whether this task
     * has been cancelled.
     */
    protected void done() {
    }

    /**
     * Sets the result of this future to the given value unless
     * this future has already been set or has been cancelled.
     *
     * <p>This method is invoked internally by the {@link #run} method
     * upon successful completion of the computation.
     *
     * @param v the value
     */
    protected void set(V v) {
        if (U.compareAndSwapInt(this, STATE, NEW, COMPLETING)) {//判断当前的 状态是不是 NEW ，如果是则 设置state为 COMPLETING
            outcome = v;  //为返回结果赋值
            U.putOrderedInt(this, STATE, NORMAL); //正常完成任务的最后状态
            finishCompletion();
        }
    }

    /**
     * Causes this future to report an {@link ExecutionException}
     * with the given throwable as its cause, unless this future has
     * already been set or has been cancelled.
     *
     * <p>This method is invoked internally by the {@link #run} method
     * upon failure of the computation.
     *
     * @param t the cause of failure
     */
    protected void setException(Throwable t) {
        if (U.compareAndSwapInt(this, STATE, NEW, COMPLETING)) { //如果当前的 state 是NEW ，则设置为 COMPLETING，
            outcome = t; //get 返回的结果为 异常
            U.putOrderedInt(this, STATE, EXCEPTIONAL); // 异常的最终状态为 EXCEPTIONAL
            finishCompletion();
        }
    }

    public void run() {
        boolean isRunnerNull = U.compareAndSwapObject(this, RUNNER, null, Thread.currentThread());
        System.out.println("run state = " + state + ", isRunnerNull = " + isRunnerNull);
        //如果 state != NEW 表示任务一开始执行了，直接返回
        // 通过CAS 操作来判断变量 runner （RUNNER是指向 runner的指针）是否为null， 如果为null 则 将runner 赋值为当前线程并返回true ,如果不为null则返回false
        if (state != NEW || !isRunnerNull)
            return;
        try {
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;
                try {
                    result = c.call();
                    ran = true;
                } catch (Throwable ex) {//执行 call()发生异常，比方说 空指针异常等
                    result = null;
                    ran = false;
                    setException(ex); //设置当前状态为异常
                }
                if (ran)//执行成功
                    set(result);//设置成功的状态
            }
        } finally {
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            int s = state;
            if (s >= INTERRUPTING)//处理中断， 里面的逻辑很简单，while 循环，如果是 INTERRUPTING 则调用 yield 让出当前线程
                handlePossibleCancellationInterrupt(s);
        }
    }

    /**
     * Executes the computation without setting its result, and then
     * resets this future to initial state, failing to do so if the
     * computation encounters an exception or is cancelled.  This is
     * designed for use with tasks that intrinsically execute more
     * than once.
     *
     * @return {@code true} if successfully run and reset
     */
    protected boolean runAndReset() {
        if (state != NEW ||
                !U.compareAndSwapObject(this, RUNNER, null, Thread.currentThread()))
            return false;
        boolean ran = false;
        int s = state;
        try {
            Callable<V> c = callable;
            if (c != null && s == NEW) {
                try {
                    c.call(); // don't set result
                    ran = true;
                } catch (Throwable ex) {
                    setException(ex);
                }
            }
        } finally {
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            s = state;
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
        return ran && s == NEW;
    }

    /**
     * Ensures that any interrupt from a possible cancel(true) is only
     * delivered to a task while in run or runAndReset.
     */
    private void handlePossibleCancellationInterrupt(int s) {
        // It is possible for our interrupter to stall before getting a
        // chance to interrupt us.  Let's spin-wait patiently.
        if (s == INTERRUPTING)
            while (state == INTERRUPTING)
                Thread.yield(); // wait out pending interrupt

        // assert state == INTERRUPTED;

        // We want to clear any interrupt we may have received from
        // cancel(true).  However, it is permissible to use interrupts
        // as an independent mechanism for a task to communicate with
        // its caller, and there is no way to clear only the
        // cancellation interrupt.
        //
        // Thread.interrupted();
    }

    /**
     * Simple linked list nodes to record waiting threads in a Treiber
     * stack.  See other classes such as Phaser and SynchronousQueue
     * for more detailed explanation.
     */
    static final class WaitNode {
        volatile Thread thread;
        volatile MyFutureTask.WaitNode next;

        WaitNode() {
            thread = Thread.currentThread();
        }
    }

    /**
     *  ----只用调用get方法的时候才会产生 等待线程----
     * 删除并且通知所有等待结果的线程任务完成啦，可以返回结果了，
     * 调用done() ，是一个 project方法，并且是空实现，用户可以自己实现该方法
     * callable 置位 null
     */
    private void finishCompletion() {
        // assert state > COMPLETING;
        System.out.println("finishCompletion waiters = "+waiters);
        for (MyFutureTask.WaitNode q; (q = waiters) != null; ) {
            System.out.println("finishCompletion q = "+q);
            if (U.compareAndSwapObject(this, WAITERS, q, null)) {
                for (; ; ) { //遍历通知且删除所有等待结果的线程
                    Thread t = q.thread;
                    System.out.println("finishCompletion t = "+t.getName());
                    if (t != null) {
                        q.thread = null;
                        LockSupport.unpark(t);  //通知正在等待结果的线程任务执行完成了，可以返回结果啦。
                    }
                    MyFutureTask.WaitNode next = q.next;
                    if (next == null)
                        break;
                    q.next = null; // unlink to help gc
                    q = next;
                }
                break;
            }
        }
        done();
        callable = null;        // to reduce footprint
    }

    /**
     * 等待任务完成 或 任务取消 或 等待时间超时
     * @param timed true 表示需要使用 timed 等待
     * @param nanos 如果 timed 为true，需要等待的时间
     * @return 返回状态或超时
     */
    private int awaitDone(boolean timed, long nanos)
            throws InterruptedException {
        long startTime = 0L;    // Special value 0L means not yet parked
        MyFutureTask.WaitNode q = null;
        boolean queued = false;
        for (; ; ) {
            int s = state;
            System.out.println("awaitDone s = "+s+", q = "+q+", queued = "+queued);
            if (s > COMPLETING) { //如果当前状态为 已完成 、取消或异常 则直接返回状态
                if (q != null)
                    q.thread = null;
                return s;
            } else if (s == COMPLETING)// 表示任务结束(正常/异常)，但是结果还没有保存到outcome字段，当前线程让出执行权，给其他线程先执行
                Thread.yield();
            else if (Thread.interrupted()) {//如果调用get()的线程被中断了，就从等待的线程栈中移除这个等待节点，然后抛出中断异常
                removeWaiter(q);
                throw new InterruptedException();
            } else if (q == null) {// 如果等待节点q=null,就创建一个等待节点
                boolean flag = timed && nanos <= 0L;
                System.out.println("awaitDone s = "+s+", q = "+q+", flag =  "+flag);
                if (flag)
                    return s;
                q = new MyFutureTask.WaitNode();
            } else if (!queued)//如果这个等待节点还没有加入等待队列，就加入队列头
                queued = U.compareAndSwapObject(this, WAITERS,
                        q.next = waiters, q);
            else if (timed) { //如果设置了超时等待时间, 这个是调用 get(5, TimeUnit.SECONDS)才会走到这里
                final long parkNanos;
                if (startTime == 0L) { // first time
                    startTime = System.nanoTime();
                    if (startTime == 0L)
                        startTime = 1L;
                    parkNanos = nanos;
                } else {
                    long elapsed = System.nanoTime() - startTime;
                    if (elapsed >= nanos) { //等待的时间比任务完成需要的时间短，则删除等待的线程
                        removeWaiter(q);//删除等待操作线程的具体实现
                        return state;
                    }
                    parkNanos = nanos - elapsed;
                }
                if (state < COMPLETING) { //等待的时间比任务完成需要的时间长，那么阻塞当前线程，等任务结束后唤醒该线程（LockSupport.unPark来唤醒）
                    LockSupport.parkNanos(this, parkNanos);
                }
            } else
                LockSupport.park(this); //阻塞住，等待唤醒，这个是任务正常完成 或 取消 时会调用 LockSupport.unPark 操作来唤醒
        }
    }

    /**
     * Tries to unlink a timed-out or interrupted wait node to avoid
     * accumulating garbage.  Internal nodes are simply unspliced
     * without CAS since it is harmless if they are traversed anyway
     * by releasers.  To avoid effects of unsplicing from already
     * removed nodes, the list is retraversed in case of an apparent
     * race.  This is slow when there are a lot of nodes, but we don't
     * expect lists to be long enough to outweigh higher-overhead
     * schemes.
     */
    private void removeWaiter(MyFutureTask.WaitNode node) {
        if (node != null) {
            node.thread = null;
            retry:
            for (; ; ) {          // restart on removeWaiter race
                System.out.println("removeWaiter ");
                for (MyFutureTask.WaitNode pred = null, q = waiters, s; q != null; q = s) {
                    s = q.next;
                    System.out.println("removeWaiter pred = "+pred+", q = "+q+", s = "+s+", q.thread = "+q.thread);
                    if (q.thread != null)
                        pred = q;
                    else if (pred != null) {
                        pred.next = s;
                        if (pred.thread == null) // check for race
                            continue retry;
                    } else {
                        boolean flag = U.compareAndSwapObject(this, WAITERS, q, s);
                        System.out.println("removeWaiter flag = "+flag);
                        if (!flag){
                            continue retry;
                        }
                    }
                }
                break;
            }
        }
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe U;
    private static final long STATE;
    private static final long RUNNER;
    private static final long WAITERS;

    static {
        try {
            U = getUnsafe();
            STATE = U.objectFieldOffset(FutureTask.class.getDeclaredField("state"));     //state的偏移量，类似于指针，通过他来设置 state的值
            RUNNER = U.objectFieldOffset(FutureTask.class.getDeclaredField("runner"));   //同上
            WAITERS = U.objectFieldOffset(FutureTask.class.getDeclaredField("waiters")); //同上
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }

        // Reduce the risk of rare disastrous classloading in first call to
        // LockSupport.park: https://bugs.openjdk.java.net/browse/JDK-8074773
        Class<?> ensureLoaded = LockSupport.class;
    }

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }

}
