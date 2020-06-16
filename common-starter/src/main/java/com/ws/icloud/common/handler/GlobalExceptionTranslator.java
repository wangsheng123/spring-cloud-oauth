package com.ws.icloud.common.handler;


import com.ws.icloud.common.response.ResultCode;
import com.ws.icloud.common.response.ViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ws.icloud.common.exception.service.ServiceException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator  {
    @ExceptionHandler(value = RuntimeException.class)
    public ViewResponse exceptionHandler(RuntimeException e) {
        return ViewResponse.failed(ResultCode.APP_BIZ_ERROR, e.getMessage());
    }



    @ExceptionHandler(value = ServiceException.class)
    public ViewResponse handlerError(ServiceException e){
        log.error("Service Exception", e);
        return ViewResponse
                .builder()
                .code(ResultCode.APP_BIZ_ERROR)
                .message(e.getMessage())
                .success(false)
                .build();
    }
}
