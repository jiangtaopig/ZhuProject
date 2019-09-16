package daily.yiyuan.com.test_java.multi_thread.lock.consumer_producer;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/29
 */
public class TestMain {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        P_Thread p1 = new P_Thread(myStack);
        p1.setName("p1");

        P_Thread p2 = new P_Thread(myStack);
        p2.setName("p2");

        p1.start();
        p2.start();

        C_Thread c1 = new C_Thread(myStack);
        c1.setName("c1");
        C_Thread c2 = new C_Thread(myStack);
        c2.setName("c2");
        C_Thread c3 = new C_Thread(myStack);
        c3.setName("c3");
        c1.start();
        c2.start();
        c3.start();
    }
}

class P_Thread extends Thread {
    private MyStack myStack;

    public P_Thread(MyStack stack) {
        myStack = stack;
    }

    @Override
    public void run() {
        super.run();
        int i = 0;
        while (true) {
            myStack.push();
            i++;
        }
    }
}

class C_Thread extends Thread {
    private MyStack myStack;

    public C_Thread(MyStack stack) {
        myStack = stack;
    }

    @Override
    public void run() {
        super.run();
        int i = 0;
        while (true) {
            myStack.pop();
            i++;
        }
    }
}