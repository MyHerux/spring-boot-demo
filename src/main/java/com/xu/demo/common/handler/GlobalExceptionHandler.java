package com.xu.demo.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.xu.demo.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 统一错误码异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    public JSONObject BusinessExceptionHandler(BusinessException exception) throws IOException {
        logger.info(exception.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", exception.getCode());
        jsonObject.put("message", exception.getMessage());
        return jsonObject;
    }

    @ExceptionHandler(value = Exception.class)
    public JSONObject OtherExceptionHandler(Exception e) throws IOException {
        logger.error(e.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", "300005");
        jsonObject.put("message", "系统异常");
        return jsonObject;
    }
}
