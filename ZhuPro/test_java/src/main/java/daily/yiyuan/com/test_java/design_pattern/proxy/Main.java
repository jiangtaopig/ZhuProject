package daily.yiyuan.com.test_java.design_pattern.proxy;

import java.lang.reflect.Proxy;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 *
 * 代理模式氛围静态代理和动态代理：
 * 相同点：
 * 代理对象和目标对象都需要实现一个【公共接口】
 *
 * 不同点：
 * 1.动态代理产生代理对象的时机是【运行时动态生成】，它没有Java源文件
 * 2. 它是直接生成字节码文件实例化代理对象
 *
 * 我们现在举个例子，小猪想让他在法国读书的好朋友【小王】给他买红酒
 *
 */
class Main {
    public static void main(String[] args) {
//        testStaticProxy();
        dynamicProxy();
    }


    /**
     * 这时，小猪想找小王给女友买瓶香水，那么需要改动小王里面的代码（比如，在小王类中要添加实际香水商店的引用和逻辑，详情见代码）
     * 可以发现 违反了对扩展开发对修改关闭的原则，这时候就该动态代理出场啦
     */
    private static void testStaticProxy(){
        RealWineSeller realWineSeller = new RealWineSeller();
        XiaoWangProxy proxy = new XiaoWangProxy(realWineSeller);
        proxy.buyWine(2499.8);


        IPerfume perfume = new RealPerfumeSeller();
        XiaoWangProxy proxy1 = new XiaoWangProxy(perfume);
        proxy1.buyPerfume(399.9);
    }

    private static void dynamicProxy(){
        // 代理买酒
        RealWineSeller wineSeller = new RealWineSeller();
        SellerProxy sellerProxy = new SellerProxy(wineSeller);
        IWine sellWine = (IWine) Proxy.newProxyInstance(wineSeller.getClass().getClassLoader(), wineSeller.getClass().getInterfaces(), sellerProxy);
        sellWine.buyWine(1234.5);

        // 代理买香水
        RealPerfumeSeller perfumeSeller = new RealPerfumeSeller();
        SellerProxy sellerProxy1 = new SellerProxy(perfumeSeller);
        IPerfume perfume = (IPerfume) Proxy.newProxyInstance(perfumeSeller.getClass().getClassLoader(), perfumeSeller.getClass().getInterfaces(), sellerProxy1);
        perfume.buyPerfume(299.9);
    }
}
