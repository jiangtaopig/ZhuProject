package daily.yiyuan.com.test_java.okhttp_pattern;

public interface Interceptor {

    Response intercept(Chain chain);

    interface Chain {

        Request request();

        Response proceed(Request request);

    }
}
