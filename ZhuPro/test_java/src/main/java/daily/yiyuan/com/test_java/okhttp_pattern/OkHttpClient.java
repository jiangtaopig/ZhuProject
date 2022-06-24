package daily.yiyuan.com.test_java.okhttp_pattern;

public class OkHttpClient implements Call.Factory {
    @Override
    public Call newCall(Request request) {
        return new RealCall(request);
    }
}
