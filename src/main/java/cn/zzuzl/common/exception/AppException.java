package cn.zzuzl.common.exception;

/**
 * Created by Administrator on 2017/9/10.
 */
public class AppException extends RuntimeException {
    private Long code;
    private String message;

    public AppException(Long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public AppException(Long code) {
        super(String.valueOf(code));
        this.code = code;
        this.message = String.valueOf(code);
    }

    public AppException(String message) {
        super(message);
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
