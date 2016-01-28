package com.kitkatdev.m2dl.chanllengeandroidclm.model;

import android.content.Intent;

/**
 * Created by STEVE on 28/01/2016.
 */
public class Score {

    private String name;
    private Integer point;

    public Score(String name, Integer point) {
        this.name = name;
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
