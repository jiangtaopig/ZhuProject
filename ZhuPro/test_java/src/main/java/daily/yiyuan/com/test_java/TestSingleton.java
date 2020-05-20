package daily.yiyuan.com.test_java;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/5/11
 * <p>
 * 不使用 锁的 单例, 静态内部类
 * 原理 ： 是定义了静态变量 INSTANCE ，保证在类加载的过程中就实例化了对象，但是类的加载 loadClass 也是 synchronised ,所以 静态内部类的方法 也是使用了锁
 */
public class TestSingleton {
    private TestSingleton() {
    }

    private static class SingletonHolder {
        public static TestSingleton INSTANCE = new TestSingleton();
    }

    public static TestSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

}

//--------------------枚举的单例- 也是通过锁实现的---------------------------

/**
 * 枚举的内部类 底层 ENUM 实现的，这个类的成员变量都是 static 类型的，并且在静态代码块中实例化的，和下面的 饿汉 单例模式很像
 */
enum Singleton {
    INSTANCE;

    public void doAnyThing() {
        System.out.println("do anything");
    }
}

//-------------------------------饿汉单例模式1-------------------------------------------------------

class HungrySingleton {
    private HungrySingleton() {

    }

    private static HungrySingleton INSTANCE = new HungrySingleton();

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }
}

//-------------------------------饿汉单例模式2-------------------------------------------------------

class HungrySingleton2 {
    private HungrySingleton2() {

    }

    private static HungrySingleton2 INSTANCE = null;

    static {
        INSTANCE = new HungrySingleton2();
    }

    public static HungrySingleton2 getInstance() {
        return INSTANCE;
    }
}

// 以上的其实都是通过说来实现的，下面的就是不适用锁来实现了

// ----------------------------通过CAS来实现单例----------------------------------------------

class CasSingleton {
    private CasSingleton() {

    }

    private static final AtomicReference<CasSingleton> INSTANCE = new AtomicReference<CasSingleton>();

    public static CasSingleton getInstance() {
        //缺点 1 ： 如果忙等待一直执行不成功，会对CPU造成较大的内存开销
        for (; ; ) {
            CasSingleton singleton = INSTANCE.get();
            if (singleton != null) {
                return singleton;
            }
            // 缺点2 ：当有 n 个线程执行 singleton = new CasSingleton() 时，会导致大量 CasSingleton 对象被创建，可能会导致OOM
            singleton = new CasSingleton();
            if (INSTANCE.compareAndSet(null, singleton)) {
                return singleton;
            }
        }
    }
}

//---------------------------- 通过ThreadLocal 来实现单例------------------------------
class LocalSingleton{

    private LocalSingleton() {

    }

    private final static ThreadLocal<LocalSingleton> local = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            LocalSingleton localSingleton = new LocalSingleton();
            return localSingleton;
        }
    };

    public static LocalSingleton getInstance(){
        return local.get();
    }
}