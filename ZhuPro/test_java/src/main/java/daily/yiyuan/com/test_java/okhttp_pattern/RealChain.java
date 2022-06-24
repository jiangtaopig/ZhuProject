package daily.yiyuan.com.test_java.okhttp_pattern;

import java.util.List;

public class RealChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;
    private int index;
    private Request request;

    public RealChain(List<Interceptor> interceptors, Request request, int index) {
        this.interceptors = interceptors;
        this.request = request;
        this.index = index;
    }


    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response proceed(Request request) {
        Interceptor.Chain next = new RealChain(interceptors, request, index + 1);
        Interceptor interceptor = interceptors.get(index);
        return interceptor.intercept(next);
    }
}
