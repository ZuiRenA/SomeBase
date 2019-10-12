package com.shen.somebase.model;

import java.io.Serializable;

/*
 * created by shen at 2019/10/11 11:12
 */
public class SubscribeIndustryBean implements Serializable {
    private int industry_id;
    private String industry_name;
    private String icon_img_url;

    public int getId() {
        return industry_id;
    }

    public void setId(int id) {
        this.industry_id = id;
    }

    public String getName() {
        return industry_name;
    }

    public void setName(String name) {
        this.industry_name = name;
    }

    public String getIcon_img_url() {
        return icon_img_url;
    }

    public void setIcon_img_url(String icon_img_url) {
        this.icon_img_url = icon_img_url;
    }
}
