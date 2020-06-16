package com.ws.icloud.common.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ws.icloud.common.serializer.ResultCodeDeserializer;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ViewResponse <T> {

    private String message = "";

    @JsonDeserialize(using = ResultCodeDeserializer.class)
    private ResultCode code = ResultCode.SUCCESS;

    private boolean success = true;

    private T view = null;

    public static <T> ViewResponse<T> success() {
        return response(true, "ok", null);
    }

    public static <T> ViewResponse<T> success(T view) {
        return response(true, "ok", view);
    }

    public static ViewResponse<Void> failed(String message){
        return response(false, ResultCode.APP_BIZ_ERROR ,message, null);
    }

    public static ViewResponse<Void> failed(ResultCode code) {
        return response(code,code.getMsg(), null);
    }

    public static ViewResponse<Void> failed(ResultCode code, String message) {
        return response(code, message, null);
    }

    public static <T> ViewResponse<T> response(boolean success, String message, T view) {
        return response(success, ResultCode.SUCCESS, message, view);
    }

    public static <T> ViewResponse<T> response(ResultCode resultCode, String message, T view) {
        return response(Objects.equals(resultCode, ResultCode.SUCCESS), resultCode, message, view);
    }

    public static <T> ViewResponse<T> response(boolean success, ResultCode resultCode, String message, T view) {
        ViewResponse<T> viewResponse = new ViewResponse<>();
        viewResponse.setSuccess(success);
        viewResponse.setCode(resultCode);
        viewResponse.setMessage(message);
        viewResponse.setView(view);
        return viewResponse;
    }

}
