package daily.yiyuan.com.test_java;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/24
 */
public class TestFinal {
    public  static void main(String[] args) {
        String s = "123";
        doSth(new IBookLisner() {
            @Override
            public void doSth() {
                s.length();
            }
        });
    }

    public static void doSth(IBookLisner lisner){
        lisner.doSth();
    }

    interface IBookLisner{
        void doSth();
    }

}
