
package com.xu.demo.common.exception;


import com.xu.demo.common.bean.ExceptionType;

/**
 * 业务异常
 *
 * @author xuhua
 * @since 1.0.0
 */
public class BusinessException extends Exception {

    private String code;

    private String message;

    private int level;

    public BusinessException(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.level = exceptionType.getLevel();
    }

    public BusinessException(String code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", level=" + level +
                '}';
    }
}
