package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.utils.EncryptUtils;
import com.example.za_zhujiangtao.zhupro.utils.RsaEncryptUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RsaActivity extends AppCompatActivity {

    private static final RSAPrivateKey KEY = EncryptUtils.loadPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDFCVD2jei+ZK5SL7PB4lvrmkBZkk64GMcytxOI4BpGdcXHKNH06h6OtQfQFHJF1i8Qo0XfeeIv2hbzdsV2qCSeVxVtPmMkMaPrRTV/u6eY1wTuTkEv320ivRwLxLoyQ7Wr6jMPq+d7v5KHpwXNx4+8HkXah8QjDL+4S3DHq8+eoDgQrBI5zNYMPz39tTMueR8YZY+4g6/Q2gcWWdMSVNPLPz6TivEFjImZVC3bAbsLyaAHYKQz6JA78ktCTVIZyMfcfJ1bcFzPZbRcYZj+gTkPbtk5b1gI7dXWioaPSk7ArY+ul+DHo1wdXs0vKcS+H5tTTeEuefvcwlzS4P5ppaxrAgMBAAECggEAEzPUOyMbYqrHPmU9vJCF6tEuokwa9eAcUbn74mQMy38g1kcJJzhF4ByOdV34wQudrJ5s6dmkDIvRnsqddfHvF6tfADWLTfS0h/JHw3oTXFbgOSVRPwwkQ7VwbUkv6EQnCWkartVPCwVhj2zcqPBg9rFwl1qR5+SPLrPTevIomsnQcSstFVyu/tWyqfS59rbo79NV0zp1cz3+VTRJ27+61umPzRSrgz83YRObChXwVeNqPjz7CJ95Qd+cF0Gj4b83pu4PttrGrfUdwNMlz9BFtSUla0/ODIYzO6osnXXv3ulW8HrrTqxrmA/OZ8EaLmFw3zfNC6++fVfGeU4kvUydGQKBgQD+A/uNR8qj7naZ76WhYncIu7q3y4GMMz4eVBl7vOgTelLkl7GStblt0WspSBpIpfXb6bcubVoqxHjITYQV2sOtr6dMKiayDZde6sLq/zvbYPGqAQgOJmL9++2eEubdxZV9BygIt3Pgh/Ri79M+VaqADnitG8Vi6G0TE7VtmORSTQKBgQDGk2DeEJmb6RbHlLf+rjFRNkxs90xDNjilA2YYZ4SdvXzbQrv3BnyMMpFGZl/f6DjYnIK7AxlSxlw2pYQMnSrNiJdTUfl7l4c9rYun03+p3BFDPiBKO4NbkSCavhmO2PnwB+skmFAZKhoId2Q8RzR8Mwy+5UL1qMThz/rI6WQllwKBgAjFbG9Bdl7YwiGoo8iB1OhpSxZDobKMpm13q/WY3U6m36vjDQ+q1L9VBuVDBx1RFb9FP70Lq5xkcmNAQfpbNnyN3zIDFpj5XSu64YYp0BBQNagQJw4OB0cL5vWZY31u8C2EDL7YTtkG+Mpdy6aQYLXRSht4JQW4TuPkv1QMfn3VAoGBAJIBbRj6Raegc9JdZAgLllFrRjhcgKNuq6mQ3TGWc6jz2WjyIXiZOVxwFecYgOgGPD3wniLZPXZiSQ0ZGAWaTg1/oBxwSqr4GEVeZDAKz3RaTgsOE/ng1k2uYLXGjs48dVFq0RdAMqy+CxQsjPPROyMS+g0mTtyHsfOC05eEG9GTAoGAWg3KkfGoBPU3Mh2znTGX6WEy4WADbznLGdwpD/jyQ+QGEZ5ExTSQob/pvUavmIHT5a5Gjb4QGK8EX6plFnw90YCGOPf8CjAZQUQ7Ke+j8DXRoBYjDlhXXETMP/A3tZ26k0X4iW5/BSDw4HGSHQdcD13CeDb3O60rsbZckJGYg1s=");
    private static final RSAPublicKey TEST_KEY = EncryptUtils.loadPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxQlQ9o3ovmSuUi+zweJb65pAWZJOuBjHMrcTiOAaRnXFxyjR9OoejrUH0BRyRdYvEKNF33niL9oW83bFdqgknlcVbT5jJDGj60U1f7unmNcE7k5BL99tIr0cC8S6MkO1q+ozD6vne7+Sh6cFzcePvB5F2ofEIwy/uEtwx6vPnqA4EKwSOczWDD89/bUzLnkfGGWPuIOv0NoHFlnTElTTyz8+k4rxBYyJmVQt2wG7C8mgB2CkM+iQO/JLQk1SGcjH3HydW3Bcz2W0XGGY/oE5D27ZOW9YCO3V1oqGj0pOwK2Prpfgx6NcHV7NLynEvh+bU03hLnn73MJc0uD+aaWsawIDAQAB");
    private static final int RSA = 1;
    private static final String TAG = "RsaActivity";

    @BindView(R.id.sign)
    Button mRsaSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_layout);
        ButterKnife.bind(this);

        mRsaSign.setOnClickListener(v -> {
            String originalSign = "zzzjjjtt";
            Log.e(TAG, "original sign: " + originalSign);
            String sign = EncryptUtils.signSHA256withRSAPrivate(KEY, originalSign);
            Log.e(TAG, "final sign: " + sign);
            Log.e(TAG, "verify result: " + EncryptUtils.verifySignSHA256withRSAPublic(TEST_KEY, originalSign, sign));
            Log.e(TAG, "final sign 222 : " + sign);


            rsaSignAndUnsign();
        });
    }

    private void rsaSignAndUnsign(){
        //初始化密钥
        //生成密钥对
        Map<String, Object> keyMap = null;
        try {
            keyMap = RsaEncryptUtils.initKey();
            //公钥
            byte[] publicKey = RsaEncryptUtils.getPublicKey(keyMap);
            //私钥
            byte[] privateKey = RsaEncryptUtils.getPrivateKey(keyMap);
            Log.e(TAG,"公钥：" + Base64.encodeToString(publicKey, Base64.NO_WRAP));
            Log.e(TAG,"私钥：" + Base64.encodeToString(privateKey, Base64.NO_WRAP));

            String str = "RSA密码交换算法";
            Log.e(TAG,"原文:" + str);
            //甲方进行数据的加密
            byte[] code1 = RsaEncryptUtils.encryptByPrivateKey(str.getBytes(), privateKey);
            Log.e(TAG,"加密后的数据：" + Base64.encodeToString(code1,Base64.NO_WRAP));
            //乙方进行数据的解密
            byte[] decode1 = RsaEncryptUtils.decryptByPublicKey(code1, publicKey);
            Log.e(TAG,"乙方解密后的数据：" + new String(decode1) + "");

            str = "乙方向甲方发送数据RSA算法";

            Log.e(TAG,"原文:" + str);

            //乙方使用公钥对数据进行加密
            byte[] code2 = RsaEncryptUtils.encryptByPublicKey(str.getBytes(), publicKey);
            Log.e(TAG,"加密后的数据：" + Base64.encodeToString(code2, Base64.NO_WRAP));

            //甲方使用私钥对数据进行解密
            byte[] decode2 = RsaEncryptUtils.decryptByPrivateKey(code2, privateKey);

            Log.e(TAG,"甲方解密后的数据：" + new String(decode2));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
