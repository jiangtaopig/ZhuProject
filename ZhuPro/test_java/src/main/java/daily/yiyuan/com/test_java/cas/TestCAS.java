package daily.yiyuan.com.test_java.cas;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/30
 */
public class TestCAS {
    public static void main(String[] args) {
//        Node node = new Node(4);
//        boolean flag = node.casNext(node, new Node(5));
//        System.out.println(flag + ", val = " + node.next.value);

        Node[] nodes = new Node[5];
        for (int i = 0; i < 5; i++) {
            nodes[i] = new Node(i + 10);
        }

        Node node = Node.get(nodes, 0);
        System.out.println("node val = " + node.value); //输出 nodes[0] = 10;

        Node expect = nodes[0];
        Node update = new Node(23);
        boolean res = Node.casTabAt(nodes, 0, expect, update);//比较nodes[0]和expect是否相等，如果相等则替换nodes[0]为update
        System.out.println("res = " + res + ", nodes[0].val = " + nodes[0].value);//这里nodes[0]和expect相等，则替换nodes[0]为update，输出为23

        Node node1 = new Node(4);
        node1.defaultVal = 2;
        boolean flag = node1.casDefaultVal(5);
        System.out.println("flag = " + flag + ", defaultVal = " + node1.defaultVal);

        System.out.println("----------------------------------------------------测试MyInfo的偏移量----------------------------------------------------------------");
        testMyInfo();

    }

    private static void testMyInfo() {
        Unsafe unsafe = UnSafeUtils.getUnsafe();
        for (Field field : MyInfo.class.getDeclaredFields()) {
            System.out.println(field.getName() + "---" + field.getType() + ", offset = " + unsafe.objectFieldOffset(field));
        }


        try {
            unsafe.putObject(MyInfo.class, unsafe.objectFieldOffset(MyInfo.class.getDeclaredField("name")), "pig");
            String name = (String) unsafe.getObject(MyInfo.class, unsafe.objectFieldOffset(MyInfo.class.getDeclaredField("name")));
            System.out.println("name = " + name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                try {
                    unsafe.putObject(MyInfo.class, unsafe.objectFieldOffset(MyInfo.class.getDeclaredField("name")), "dog");
                    String name = (String) unsafe.getObject(MyInfo.class, unsafe.objectFieldOffset(MyInfo.class.getDeclaredField("name")));
                    System.out.println("on thread , name = " + name);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /*
        age---int, offset = 12   由于对象头占12个字节，所以 age 的偏移量等于12， int 占4个字节，所以 weight 的偏移量等于 12+4 = 16； weight是double类型 占 8 个字节，所以 name 的偏移量等于 16+8 = 24
        name---class java.lang.String, offset = 24
        weight---double, offset = 16
         */
    }

    private static class Node {
        int value;
        String data;
        volatile Node next;
        static final sun.misc.Unsafe UNSAFE;
        static final long nextOffset;
        private static final int ABASE;
        private static final int ASHIFT;
        int defaultVal;
        private static long DEFAULT_VAL;

        public Node(int val) {
            value = val;
        }

        static {
            try {
                UNSAFE = UnSafeUtils.getUnsafe();
                Class<?> k = Node.class;
                nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next")); //Node 对象的变量 next 偏移量
                DEFAULT_VAL = UNSAFE.objectFieldOffset(k.getDeclaredField("defaultVal"));
                ABASE = UNSAFE.arrayBaseOffset(Node[].class);//可以获取数组第一个元素的偏移地址
                //返回数组中一个元素占用的大小
                int scale = UNSAFE.arrayIndexScale(Node[].class);

                if ((scale & (scale - 1)) != 0)
                    throw new Error("array index scale not a power of two");
                ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);

                System.out.println("ABASE = " + ABASE + ", scale = " + scale + ", ASHIFT = " + ASHIFT);
                long valOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("value"));
                System.out.println("valOffset = " + valOffset);//输出 等于12 ，因为对象头占12个字节，所以 变量 value 的偏移量为 12；
                long dataOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("data"));
                System.out.println("dataOffset = " + dataOffset);
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        //利用 unsafe 来获取数组中对应index的值
        public static Node get(Node[] tab, int index) {
            return (Node) UNSAFE.getObject(tab, ((long) index << ASHIFT) + ABASE);
        }

        /**
         * cas next 变量
         *
         * @param cmp
         * @param val
         * @return
         */
        boolean casNext(Node cmp, Node val) {
            /**
             * compareAndSwapObject(Object obj, long offset, Object expect, Object update)
             * obj 操作的对象
             * offset 对象的地址偏移量
             * expect offset 所保存的对象和 expect 比较，相等才更新对象的值为update
             * update 更新的值
             */
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        boolean casDefaultVal(int update) {
            return UNSAFE.compareAndSwapInt(this, DEFAULT_VAL, defaultVal, update);
        }

        static boolean casTabAt(Node[] tab, int index, Node expect, Node update) {
            return UNSAFE.compareAndSwapObject(tab, ((long) index << ASHIFT) + ABASE, expect, update);
        }


    }
}

class MyInfo {
    int age;
    String name;
    double weight;
}
