package daily.yiyuan.com.test_java.okhttp_pattern.interceptor;

import java.util.HashMap;

import daily.yiyuan.com.test_java.okhttp_pattern.Constants;
import daily.yiyuan.com.test_java.okhttp_pattern.Interceptor;
import daily.yiyuan.com.test_java.okhttp_pattern.Request;
import daily.yiyuan.com.test_java.okhttp_pattern.Response;

public class CallServerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        if (request.headers == null) {
            request.headers = new HashMap();
        }
        request.headers.put("我要结束", "返回成功");
        System.out.println(Constants.TAG + ", CallServerInterceptor start request = " + request.toString());

        // 最后一个 Interceptor 不再往下进行链式调用，直接返回结果了

        Response response = new Response();
        response.code = 200;
        response.body = "我是百度返回的结果";
        return response;
    }
}
