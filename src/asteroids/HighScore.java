/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

/**
 *
 * @author borenste_848114
 */
public class HighScore implements java.io.Serializable {

    private String first;
    private String second;
    private String third;
    private long score;

    public HighScore() {
        first = second = third = "-";
        score = -1;
    }

    public HighScore(String f, String s, String t, long sc) {
        first = f;
        second = s;
        third = t;
        score = sc;
    }

    public String getFirstInitial() {
        return first;
    }

    public String getSecondInitial() {
        return second;
    }

    public String getThirdInitial() {
        return third;
    }

    public String getInitials() {
        return first + second + third;
    }

    public long getScore() {
        return score;
    }

    @Override
    public String toString() {
        return first + second + third + " :: " + score;
    }
}
