package daily.yiyuan.com.test_java.algoritham;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/14
 */
class TestLFU {
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(){
            @Override
            protected boolean removeEldestEntry() {
                return super.removeEldestEntry();
            }
        };
    }
}
