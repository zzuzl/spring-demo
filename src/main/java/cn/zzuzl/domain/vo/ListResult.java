package cn.zzuzl.domain.vo;

import java.util.List;

/**
 * Created by zhanglei53 on 2017/12/6.
 */
public class ListResult<T> {
    private List<T> list;
    private boolean success;
    private String info;

    public ListResult(List<T> list, boolean success) {
        this.list = list;
        this.success = success;
    }

    public ListResult(boolean success, String info) {
        this.success = success;
        this.info = info;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}