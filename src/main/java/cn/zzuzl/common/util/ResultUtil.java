package cn.zzuzl.common.util;

import cn.zzuzl.domain.vo.Result;

/**
 * Created by Administrator on 2017/9/10.
 */
public final class ResultUtil {

    /**
     * 生成success的result
     * @return
     */
    public static Result successResult() {
        return createResult(true, "", "");
    }

    /**
     * 生成exception的result
     * @param e
     * @return
     */
    public static Result exceptionResult(Exception e) {
        return createResult(false, e.getMessage(), e.getMessage());
    }

    /**
     * 生成result
     * @param success
     * @param code
     * @param info
     * @return
     */
    public static Result createResult(boolean success, String code, String info) {
        Result result = new Result();
        result.setSuccess(success);
        result.setCode(code);
        result.setInfo(info);
        return result;
    }
}
