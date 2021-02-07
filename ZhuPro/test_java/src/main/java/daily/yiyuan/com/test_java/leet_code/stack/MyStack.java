package daily.yiyuan.com.test_java.leet_code.stack;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/1
 */
class MyStack {

    private int max; // 栈的最大容量
    private int top = -1; // 栈顶元素的索引

    private int [] data;

    public MyStack(int size){
        if (size <= 0)
            throw new IllegalArgumentException();
        this.max = size;
        data = new int[size];
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return max - 1 == top;
    }

    public void push(int val){
        if (isFull())
            throw new IllegalStateException("stack is full !!!");
        this.top++;
        data[top] = val;
    }

    public int pop (){
        if (isEmpty())
            throw new IllegalStateException("stack is empty !!!");
        int value = data[top];
        this.top --;
        return value;
    }
}
