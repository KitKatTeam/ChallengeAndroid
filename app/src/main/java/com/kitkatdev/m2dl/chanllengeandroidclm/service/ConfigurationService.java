package com.kitkatdev.m2dl.chanllengeandroidclm.service;

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



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
