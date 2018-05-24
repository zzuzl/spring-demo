package cn.zzuzl.domain.vo;

/**
 * Created by zhanglei53 on 2017/12/12.
 */
public class ThreeTuple<F, S, T> extends TwoTuple<F, S> {
    private final T third;

    public ThreeTuple(F first, S second, T third) {
        super(first, second);
        this.third = third;
    }

    public ThreeTuple(TwoTuple<F, S> twoTuple, T third) {
        this(twoTuple.getFirst(), twoTuple.getSecond(), third);
    }

    public T getThird() {
        return third;
    }

    @Override
    public String toString() {
        return super.toString() + ",third:" + third;
    }
}