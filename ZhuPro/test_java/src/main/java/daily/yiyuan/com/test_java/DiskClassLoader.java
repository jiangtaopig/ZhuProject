package daily.yiyuan.com.test_java;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/16
 *  加载磁盘中的class 类， JVM自带的3个加载器无法加载磁盘中的.class 文件
 */
public class DiskClassLoader extends ClassLoader {
    private String filePath;

    public DiskClassLoader(String path) {
        filePath = path;
    }

    @Override
    protected Class<?> findClass(String s) {
        String newPath = filePath + s + ".class";
        byte[] classBytes = null;
        Path path;
        try {
            path = Paths.get(new URI(newPath));
            classBytes = Files.readAllBytes(path);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return defineClass(s, classBytes, 0, classBytes.length);
    }
}
