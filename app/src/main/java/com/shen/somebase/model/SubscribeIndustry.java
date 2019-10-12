package com.shen.somebase.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * created by shen at 2019/10/11 11:11
 */
public class SubscribeIndustry implements Serializable {
    private List<SubscribeIndustryBean> like;
    private List<SubscribeIndustryBean> unlike;

    public List<SubscribeIndustryBean> getLike() {
        return like == null ? new ArrayList<SubscribeIndustryBean>() : like;
    }

    public void setLike(List<SubscribeIndustryBean> like) {
        this.like = like;
    }

    public List<SubscribeIndustryBean> getUnlike() {
        return unlike == null ? new ArrayList<SubscribeIndustryBean>() : unlike;
    }

    public void setUnlike(List<SubscribeIndustryBean> unlike) {
        this.unlike = unlike;
    }
}
