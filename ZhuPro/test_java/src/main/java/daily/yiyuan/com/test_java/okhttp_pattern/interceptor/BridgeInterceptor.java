package daily.yiyuan.com.test_java.okhttp_pattern.interceptor;

import java.util.HashMap;

import daily.yiyuan.com.test_java.okhttp_pattern.Constants;
import daily.yiyuan.com.test_java.okhttp_pattern.Interceptor;
import daily.yiyuan.com.test_java.okhttp_pattern.Request;
import daily.yiyuan.com.test_java.okhttp_pattern.Response;

public class BridgeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        if (request.headers == null) {
            request.headers = new HashMap();
        }
        request.headers.put("Host", "baidu");
        request.headers.put("Connection", "Keep-Alive");
        System.out.println(Constants.TAG + ", BridgeInterceptor start request = " + request.toString());
        return chain.proceed(request);
    }
}
