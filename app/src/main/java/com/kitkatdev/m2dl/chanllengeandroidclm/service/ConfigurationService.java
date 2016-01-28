package com.kitkatdev.m2dl.chanllengeandroidclm.service;

import com.kitkatdev.m2dl.chanllengeandroidclm.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by STEVE on 28/01/2016.
 */
public class ConfigurationService {

    /**
     * Singleton
     */
    private static ConfigurationService instance = null;
    private ConfigurationService(){}
    public static ConfigurationService getInstance(){
        if(instance == null) {
            instance = new ConfigurationService();
        }
        return instance;
    }


    /**
     * Method
     */

    private String userName;
    private List<Score> scoreList = new ArrayList<Score>();

    /**
     * Getter
     * @return
     */
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
