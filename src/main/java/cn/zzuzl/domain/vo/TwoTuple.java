package cn.zzuzl.domain.vo;

/**
 * Created by zhanglei53 on 2017/12/12.
 */
public class TwoTuple<F, S> {
    private final F first;
    private final S second;

    public TwoTuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "first:" + first + ",second:" + second;
    }
}