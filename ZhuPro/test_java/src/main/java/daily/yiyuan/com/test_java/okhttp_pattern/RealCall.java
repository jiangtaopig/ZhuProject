package daily.yiyuan.com.test_java.okhttp_pattern;

public class RealCall implements Call{

    private Request request;

    public RealCall(Request request) {
        this.request = request;
    }

    @Override
    public Request request() {
        return request;
    }
}
