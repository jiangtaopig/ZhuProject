package daily.yiyuan.com.test_java.generics;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
class Point <T> {
    private T x;
    private T y;

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {

        this.y = y;
    }

    /**
     * 泛型方法
     */
    public <E> E getX(E x){
        return x;
    }
}
