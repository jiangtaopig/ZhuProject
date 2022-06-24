package daily.yiyuan.com.test_java.design_pattern.singleton;

public class DeviceHelper {

    /**
     *
     * 解析：把常量池内的符号引用替换成直接引用的过程
     *
     */

    private DeviceHelper() {

    }

    private static class DeviceHelperHolder {
        /**
         * jvm 虚拟机规定当主动 new 一个对象时会触发类的初始化，初始化阶段也就是执行类构造器 <clinit>()方法的过程；
         * <clinit>() 方法对于类来说不是必须的，如果一个类中既没有静态代码块也没有静态变量赋值动作，
         * 那么编译器都不会为类生成<clinit>()方法。
         *
         * 虚拟机会保证一个类的 <clinit>() 方法在多线程环境中能被正确的加锁、同步。如果多个线程初始化一个类，
         * 那么只有一个线程会去执行<clinit>()方法，其它线程都需要等待。
         *
         *
         * 下面的 instance 是属于静态变量赋值，那么会执行类构造器 <clinit>() 方法，从而保证线程安全。
         *
         */
        private static DeviceHelper instance = new DeviceHelper();
    }

    public static DeviceHelper getInstance() {
        return DeviceHelperHolder.instance;
    }
}
