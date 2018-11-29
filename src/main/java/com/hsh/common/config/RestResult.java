package com.hsh.common.config;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hushihai
 * @version V1.0, 2018/11/6
 */
public class RestResult implements Serializable{
    private static final long serialVersionUID = 7940755926121057336L;
    /**
     * 请求是否成功
     */
    private boolean success;
    /**
     * 成功或者失败的code错误码
     */
    private Integer code;
    /**
     * 成功时返回的数据，失败时返回具体的异常信息
     */
    private Object data;
    /**
     * 请求失败返回的提示信息，给前端进行页面展示的信息
     */
    private Object errorMessage;
    /**
     * 服务器当前时间（添加该字段的原因是便于查找定位请求时间，因为实际开发过程中服务器时间可能跟本地时间不一致，加上这个时间戳便于日后定位）
     */
    private Timestamp currentTime;

    public RestResult() {
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", errorMessage=" + errorMessage +
                ", currentTime=" + currentTime +
                '}';
    }

    public static RestResult success() {
        RestResult result = new RestResult();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.code());
        return result;
    }

    public static RestResult success(Object data) {
        RestResult result = new RestResult();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.code());
        result.setData(data);
        return result;
    }

    public static RestResult failed(ResultCode code) {
        RestResult result = new RestResult();
        result.setSuccess(false);
        result.setCode(code.code());
        result.setErrorMessage(code.message());
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }
}