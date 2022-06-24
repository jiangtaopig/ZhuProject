package daily.yiyuan.com.test_java.okhttp_pattern;

import java.util.Map;

final public class Request {
    public String url;
    public Map<String, String> headers;

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", headers=" + headers +
                '}';
    }
}
