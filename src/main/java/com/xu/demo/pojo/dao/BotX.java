package com.xu.demo.pojo.dao;

import java.util.Date;

public class BotX {
    private Integer id;

    private String name;

    private String info;

    private Date creatTime;

    private String bot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot == null ? null : bot.trim();
    }
}