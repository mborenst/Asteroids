/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Color;

/**
 *
 * @author borenste_848114
 */
public class Settings implements java.io.Serializable {

    private float musicVolume;
    private float sfxVolume;
    private long startingLives;
    private float maxLasers;
    private long hyperSpaceChargeTime;
    private Color lineColor;

    public Settings() {
        musicVolume = 3;
        sfxVolume = 3;
        startingLives = 3;
        maxLasers = 15;
        hyperSpaceChargeTime = 30;
        lineColor = Color.WHITE;
    }

    public void setMusicVolume(int i) {
        musicVolume = i;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setSFXVolume(int i) {
        sfxVolume = i;
    }

    public float getSFXVolume() {
        return sfxVolume;
    }

    public void setStartingLives(int i) {
        startingLives = i;
    }

    public long getStartingLives() {
        return startingLives;
    }

    public void setMaxLasers(int i) {
        maxLasers = i;
    }

    public float getMaxLasers() {
        return maxLasers;
    }

    public void setHyperSpaceChargeTime(long i) {
        hyperSpaceChargeTime = i;
    }

    public long getHyperSpaceChargeTime() {
        return hyperSpaceChargeTime;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    @Override
    public String toString() {
        return "Settings: "
                + "\nMusic Volume: " + musicVolume
                + "\nSFX Volume: " + sfxVolume
                + "\nStarting Lives: " + startingLives
                + "\nMax Lasers: " + maxLasers
                + "\nHyperSpace Charge Time: " + hyperSpaceChargeTime
                + "\nLine Color: " + lineColor.toString();
    }
}
