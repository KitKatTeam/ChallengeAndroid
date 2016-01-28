package com.kitkatdev.m2dl.chanllengeandroidclm.service;

import android.media.MediaPlayer;

import com.kitkatdev.m2dl.chanllengeandroidclm.MainActivity;
import com.kitkatdev.m2dl.chanllengeandroidclm.MainJeu;
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
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MediaPlayer mediaPlayer1 = new MediaPlayer();
    private MediaPlayer mediaPlayer2 = new MediaPlayer();
    private MainActivity mainActivity;
    private MainJeu mainJeu;
    private Integer sens = 0;

    public MediaPlayer getMediaPlayer1() {
        return mediaPlayer1;
    }

    public void setMediaPlayer1(MediaPlayer mediaPlayer1) {
        this.mediaPlayer1 = mediaPlayer1;
    }

    public MediaPlayer getMediaPlayer2() {
        return mediaPlayer2;
    }

    public void setMediaPlayer2(MediaPlayer mediaPlayer2) {
        this.mediaPlayer2 = mediaPlayer2;
    }

    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public MainJeu getMainJeu() {
        return mainJeu;
    }

    public void setMainJeu(MainJeu mainJeu) {
        this.mainJeu = mainJeu;
    }

    public static void setInstance(ConfigurationService instance) {
        ConfigurationService.instance = instance;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

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
