package daily.yiyuan.com.test_java.okhttp_pattern;

public class Response {
    public int code;
    public String body;

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
