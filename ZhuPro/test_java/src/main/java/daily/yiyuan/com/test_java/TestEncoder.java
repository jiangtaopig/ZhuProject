package daily.yiyuan.com.test_java;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/27
 */
public class TestEncoder {
    public static void main(String[] args) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        String a = "AAAAAAA";
        String afterEncode = base64Encoder.encode(a.getBytes());
        try {
            String decode = new String(decoder.decodeBuffer(afterEncode));
            System.out.println("a = " + a + ", afterEncode = " + afterEncode + ", decode = " + decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
