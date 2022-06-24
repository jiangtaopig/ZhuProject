package daily.yiyuan.com.test_java.okhttp_pattern;

public interface Call {

    Request request();

    interface Factory{
        Call newCall(Request request);
    }
}
