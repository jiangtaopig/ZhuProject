package daily.yiyuan.com.test_java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/16
 */
public class TestDefineClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        DiskClassLoader diskClassLoader = new DiskClassLoader("file:///D:/test/"); // 如果写成 "D:\\test\\" 则会报java.net.URISyntaxException: Illegal character in opaque part at index 2：Secret.class

        Class c = diskClassLoader.findClass("Secret");
        if (c != null){
            Object obl = c.newInstance();
            Method method = c.getDeclaredMethod("printSecret", null);
            method.invoke(obl, null);
        }
    }
}
