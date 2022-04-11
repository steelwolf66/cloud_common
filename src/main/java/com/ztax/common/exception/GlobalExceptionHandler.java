package com.ztax.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ztax.common.result.Result;
import com.ztax.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局系统异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常，异常原因：{}",e.getMessage(),e);
        HttpUtils.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.failed(e.getMessage());
    }


    @ExceptionHandler(JsonProcessingException.class)
    public Result handleJsonProcessingException(JsonProcessingException e) {
        log.error("Json转换异常，异常原因：{}",e.getMessage(),e);
        HttpUtils.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        log.error("业务异常，异常原因：{}",e.getMessage(),e);
        if (e.getResultCode() != null) {
            return Result.failed(e.getResultCode());
        }
        HttpUtils.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("未知异常，异常原因：{}",e.getMessage(),e);
        HttpUtils.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.failed(e.getMessage());
    }
}
