package daily.yiyuan.com.test_java.design_pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
class SellerProxy implements InvocationHandler {

    // 代理的真实对象
    private Object mRealObject;

    public SellerProxy(Object realObj){
        mRealObject = realObj;
    }

    /**
     *
     * @param o 代理对象
     * @param method 真正执行的方法
     * @param objects 执行方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        doSthBefore();
        Object res = method.invoke(mRealObject, objects);
        doSthAfter();
        return res;
    }

    private void doSthBefore(){
        System.out.println("执行动态代理之前的操作...");
    }

    private void doSthAfter(){
        System.out.println("执行动态代理之后的操作...");
    }
}
