package cn.zzuzl.domain.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/10.
 */
public class Result implements Serializable {
    private static final long serialVersionUID = -6566215731904148401L;

    private boolean success;
    private String code;
    private String info;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
