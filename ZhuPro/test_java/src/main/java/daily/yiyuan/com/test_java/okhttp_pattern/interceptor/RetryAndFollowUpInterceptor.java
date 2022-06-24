package daily.yiyuan.com.test_java.okhttp_pattern.interceptor;

import java.util.HashMap;

import daily.yiyuan.com.test_java.okhttp_pattern.Constants;
import daily.yiyuan.com.test_java.okhttp_pattern.Interceptor;
import daily.yiyuan.com.test_java.okhttp_pattern.Request;
import daily.yiyuan.com.test_java.okhttp_pattern.Response;

public class RetryAndFollowUpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {

        Request request = chain.request();
        if (request.headers == null) {
            request.headers = new HashMap();
        }
        request.headers.put("content-type", "json");
        request.headers.put("content-size", "1");
        System.out.println(Constants.TAG + ", RetryAndFollowUpInterceptor start request = " + request.toString());
        return chain.proceed(request);
    }
}
