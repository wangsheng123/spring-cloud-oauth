package com.ws.icloud.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Result Code Enum
 *
 * @author william
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ResultCode {

    /**
     * 0-599 HTTP 异常状态码
     */
    SUCCESS(HttpResponseCode.SC_OK, "OK"),

    UN_AUTHORIZED(HttpResponseCode.SC_UNAUTHORIZED, "未授权"),

    NOT_FOUND(HttpResponseCode.SC_NOT_FOUND, "页面不存在"),

    METHOD_NOT_ALLOWED(HttpResponseCode.SC_METHOD_NOT_ALLOWED, "请求方法不支持"),

    INTERNAL_SERVER_ERROR(HttpResponseCode.SC_INTERNAL_SERVER_ERROR, "服务器错误"),

    ACCESS_DENIED_ERROR(900, "没有权限"),

    /**
     * 600 - 999 业务异常状态码
     * 数字越大通常表示异常越严重
     */
    APP_BIZ_ERROR(601, "普通业务异常"),

    /**
     * 1000 - 1099 参数异常
     */
    PARAM_IS_VALID(1001, "参数无效"),

    PARAM_IS_BLANK(1002, "参数为空"),

    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),

    PARAM_MISS(1004, "参数缺失");

    private int code;

    private String msg;

    ResultCode() {
        this.code = HttpResponseCode.SC_OK;
        this.msg = "Operation is Successful";
    }

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResultCode valueOf(int codeNum) {
        for (ResultCode resultCode : values()) {
            if (resultCode.code == codeNum) {
                return resultCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{").append("\"code\":").append(this.code)
                .append(",").append("\"msg\":\"").append(msg).append("\"}")
                .toString();
    }

}
