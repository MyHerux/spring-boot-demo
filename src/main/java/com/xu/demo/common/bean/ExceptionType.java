package com.xu.demo.common.bean;

/**
 * Enum 响应类型
 */
public enum ExceptionType {
    SUCCESS("00", "success", 0),
    DATA_ERROR("400003", "数据错误", 2);

    private String code;
    private String message;
    private int level;

    ExceptionType(String code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}


