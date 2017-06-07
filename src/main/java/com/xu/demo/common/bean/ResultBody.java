package com.xu.demo.common.bean;


import com.alibaba.fastjson.JSONObject;

public class ResultBody {


    public static JSONObject json(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_code", ExceptionType.SUCCESS.getCode());
        jsonObject.put("message", ExceptionType.SUCCESS.getMessage());
        jsonObject.put("content", data);
        return jsonObject;
    }
}
