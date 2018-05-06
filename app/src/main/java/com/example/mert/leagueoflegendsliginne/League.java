package com.example.mert.leagueoflegendsliginne;

/**
 * Created by Mert on 30.04.2018.
 */

public class League {
    private int imageID;
    private int leagueNameID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getLeagueNameID() {
        return leagueNameID;
    }

    public void setLeagueNameID(int leagueNameID) {
        this.leagueNameID = leagueNameID;
    }

    public League(int imageID, int leagueNameID){
        this.imageID = imageID;
        this.leagueNameID = leagueNameID;
    }


}
