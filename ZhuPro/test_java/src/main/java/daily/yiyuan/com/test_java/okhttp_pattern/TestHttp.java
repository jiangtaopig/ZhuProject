package daily.yiyuan.com.test_java.okhttp_pattern;

import java.util.ArrayList;
import java.util.List;

import daily.yiyuan.com.test_java.okhttp_pattern.interceptor.BridgeInterceptor;
import daily.yiyuan.com.test_java.okhttp_pattern.interceptor.CallServerInterceptor;
import daily.yiyuan.com.test_java.okhttp_pattern.interceptor.RetryAndFollowUpInterceptor;

public class TestHttp {
    public static void main(String[] args) {
        RetryAndFollowUpInterceptor retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor();
        BridgeInterceptor bridgeInterceptor = new BridgeInterceptor();
        CallServerInterceptor callServerInterceptor = new CallServerInterceptor();
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(retryAndFollowUpInterceptor);
        interceptors.add(bridgeInterceptor);
        interceptors.add(callServerInterceptor);

        Request request = new Request();
        request.url = "www.baidu.com";

        Interceptor.Chain realChain = new RealChain(interceptors, request, 0);

        Response response = realChain.proceed(request);
        System.out.println(Constants.TAG + " , response = " + response);

    }
}
