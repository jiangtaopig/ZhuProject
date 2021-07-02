package com.example.za_zhujiangtao.zhupro.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by za-zhujiangtao on 2018/8/3.
 */

public class TestWebviewActivity extends Activity {

    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.load_js)
    TextView button;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.forward)
    TextView forward;

    @BindView(R.id.load_other_html)
    TextView loadOtherHtml;

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_main_layout);
        ButterKnife.bind(this);

        WebSettings settings = webView.getSettings();

        // 设置WebView对JavaScript的支持
        initSetting(settings);

        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        webView.loadUrl("https://www.zuifuli.com/h5/");//file:///android_asset/javascript.html

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        webView.addJavascriptInterface(new AndroidInvokeJs(), "test");//AndroidInvokeJs类对象映射到js的test对象


        button.setOnClickListener(view -> {
            webView.post(() -> {
                int version = Build.VERSION.SDK_INT;
                if (version < 18) {
                    // 注意调用的JS方法名要对应上
                    // 调用javascript的callJS()方法
                    webView.loadUrl("javascript:callJS()");
                } else {
                    webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                            Log.e("TestWebviewActivity", "evaluateJavascript value = " + value);
                        }
                    });
                }
            });
        });

        loadOtherHtml.setOnClickListener(v -> {
            webView.loadUrl("file:///android_asset/test.html");//   file:///android_asset/test.html
        });

        back.setOnClickListener(v -> {
            WebBackForwardList list = webView.copyBackForwardList();
            if (webView.canGoBack()) {
                webView.goBack();
            }
        });

        forward.setOnClickListener(v -> {

            if (webView.canGoForward()) {
                webView.goForward();
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.e("TestWebviewActivity", "request url = " + request.getUrl().toString());
//                if (request.getUrl().toString().contains("baidu")) {
//                    view.loadUrl(request.getUrl().toString());
//                    return true;
//                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("TestWebviewActivity", "url = " + url);
                if (url.contains("baidu")) {
                    view.loadUrl(url);
                    return true;
                } else if (!(url.startsWith("http:") || url.startsWith("https:"))) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("TestWebviewActivity", "onPageStarted url = " + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
//                Log.e("TestWebviewActivity", "shouldInterceptRequest url = " + url);
//                WebResourceResponse webResourceResponse = handleIntercept(request);
                return null;
            }
        });


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                String scheme = uri.getScheme();
                if (!TextUtils.isEmpty(scheme) && scheme.equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        // 执行JS所需要调用的逻辑
                        Log.e("TestWebviewActivity", "js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        String arg1 = uri.getQueryParameter("arg1");
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();
                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }

                jsCallback(message, true, defaultValue);
                result.confirm("js call android");
//                return super.onJsPrompt(view, url, message, defaultValue, result);
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(TestWebviewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(TestWebviewActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });
    }

    /**
     * 视频播放全屏 webview 是不支持全屏播放的
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        TestWebviewActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(TestWebviewActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    private void initSetting(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setUseWideViewPort(true); // 关键点
        settings.setDefaultTextEncodingName("utf-8"); //设置编码
        settings.setAllowFileAccess(true);// 支持文件流
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);// 调整到适合webview大小
        settings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        settings.setBlockNetworkImage(false);

        String cachePath = getApplicationContext().getCacheDir().getPath(); // 把内部私有缓存目录'/data/data/包名/cache/'作为WebView的AppCache的存储路径
        settings.setAppCachePath(cachePath);
        settings.setAppCacheMaxSize(5 * 1024 * 1024);

        //开启缓存机制
        settings.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);// true 表示允许Chrome://inspect/#devices
        }

        // 设置可以访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);


        //允许H5本地存储(localStorage & sessionStorage)
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        String app = " ;app/zuifuli/" + "1.0.0/";
        String userAgent = settings.getUserAgentString() + app;
        Log.d("TestWebviewActivity", "userAgent = " + userAgent);
        settings.setUserAgentString(userAgent);

    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private WebResourceResponse handleIntercept(WebResourceRequest request) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Call call = okHttpClient.newCall(new Request.Builder()
//                .url(request.getUrl().toString())
//                .method(request.getMethod(), null)
//                .headers(Headers.of(request.getRequestHeaders()))
//                .build()
//        );
//        try {
//            final Response response = call.execute();
//            return new WebResourceResponse(
//                    response.header("content-type", "text/html"), // You can set something other as default content-type
//                    response.header("content-encoding", "utf-8"),  //you can set another encoding as default
//                    response.body().byteStream()
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private <ResponseType> void jsCallback(String action, boolean success, ResponseType response) {
        JSCallbackMessage<ResponseType> message = new JSCallbackMessage<>(action);
        message.setSuccess(success);
        String callback;
        if (response instanceof String) {
            String msg = message.toString();
            callback = new StringBuilder(msg).insert(msg.length() - 1, ",\"params\":" + response).toString();
        } else {
            message.setParams(response);
            callback = message.toString(null);
        }

        String ret = "javascript:LocalJSBridge.callJsBack('" + callback + "')";
        String realRet = ret.replaceAll("\\\\", "\\\\\\\\");
        if (webView != null) {
            AndroidSchedulers.mainThread().createWorker().schedule(() -> webView.loadUrl(realRet)); //android 通过 loadUrl 来调用 js 的 callJsBack 方法
        }
    }

    public class AndroidInvokeJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void hello(String msg) {
            Toast.makeText(TestWebviewActivity.this, "JS调用了Android的hello方法", Toast.LENGTH_SHORT).show();
        }
    }
}
