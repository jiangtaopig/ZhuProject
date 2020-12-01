package daily.yiyuan.com.test_java.design_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/11/30
 */
public interface IImageListener {
    String sendRequest(String url);
    void success();
    void cancel();
}
