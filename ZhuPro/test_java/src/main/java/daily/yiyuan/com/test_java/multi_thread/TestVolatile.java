package daily.yiyuan.com.test_java.multi_thread;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/25
 */
class TestVolatile {

    public static int[] arr = new int[5];

    public static void main(String[] args) throws InterruptedException {

        new Thread() {
            @Override
            public void run() {
                super.run();
//                try {
//                    sleep(1_000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("开始设置");
                arr[4] = 33;
                System.out.println("设置结束");
            }
        }.start();

//        Thread.sleep(10);

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    System.out.println("xxxxxxx");
                    if (arr[4] == 33){
                        System.out.println("----");
                        break;
                    }
                }
                System.out.println("循环结束");
            }
        }.start();
    }
}
