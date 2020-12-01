package daily.yiyuan.com.test_java.design_pattern;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/11/30
 */
class Test {

    public static void main(String[] args) {
        getImageByUrl(new MyImageRequest(){
            @Override
            public String sendRequest(String url) {
                return super.sendRequest(url);
            }
        }, "xxxx");
    }

    private static void getImageByUrl(IImageListener listener, String url){
        System.out.println("doSth");
        if (listener != null){
            listener.sendRequest(url);
        }
    }
}

