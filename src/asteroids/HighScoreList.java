/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.HashMap;

/**
 *
 * @author borenste_848114
 */
public class HighScoreList implements java.io.Serializable {

    private final HashMap<Long, HighScore[]> highList;
    private HighScore[] highs;

    public HighScoreList() {
        highs = new HighScore[40];
        for (int i = 0; i < highs.length; i++) {
            highs[i] = null;
        }
        highList = new HashMap<>();
    }

    public void add(HighScore hs) {
        if (highs.length < 40) {
            HighScore[] hss = highs;
            highs = new HighScore[40];
            for (int i = 0; i < hss.length; i++) {
                highs[i] = hss[i];
            }
        }
        boolean replace = true;
        for (int i = 0; i < highs.length; i++) {
            if (highs[i] == null) {
                replace = false;
                highs[i] = hs;
                break;
            }
        }
        if (replace) {
            replace(hs);
        }
        sort();
    }

    public void clearAll() {
        for (int i = 0; i < highs.length; i++) {
            highs[i] = null;
        }
    }

    public void remove(int index) {
        for (int i = index; i < highs.length - 1; i++) {
            if (highs[i + 1] == null) {
                highs[i] = null;
                break;
            } else {
                highs[i] = highs[i + 1];
            }
        }
        sort();
    }

    public void replace(HighScore hs) {
        for (int i = 0; i < highs.length; i++) {
            if (highs[i].getScore() < hs.getScore()) {
                insert(hs, i);
                break;
            }
        }
        sort();
    }

    public void insert(HighScore hs, int index) {
        HighScore hhss = hs;
        for (int i = index; i < highs.length; i++) {
            hhss = highs[i];
            highs[i] = hs;
            if (i + 1 < highs.length) {
                hs = highs[i + 1];
            }
        }
        sort();
    }

    public void sort() {
        int i;
        for (i = 0; i < highs.length; i++) {
            if (highs[i] == null) {
                break;
            }
        }
        Arrays.sort(highs, new Whale());
    }

    public void save(long l) {
        highList.put(l, highs);
    }

    public void switchLives(long init, long fin) {
        save(init);
        if (highList.containsKey(fin)) {
            highs = highList.get(fin);
        } else {
            highList.put(fin, new HighScore[40]);
            highs = highList.get(fin);
        }
        while (highs == null) {
            highs = new HighScore[40];
        }
    }

    @Override
    public String toString() {
        String output = "High Scores:\n";
        if (highs != null) {
            for (HighScore high : highs) {
                if (high != null) {
                    output += high.toString() + "\n";
                }
            }
        }
        return output;
    }

}

class Whale implements Comparator<HighScore> {

    @Override
    public int compare(HighScore o1, HighScore o2) {
        final int dateComparisonResult = (int) (o2.getScore() - o1.getScore());
        final int yourObjectComparisonResult;

        if (dateComparisonResult == 0) {
            yourObjectComparisonResult = o1.getInitials()
                    .compareTo(o2.getInitials());
        } else {
            yourObjectComparisonResult = dateComparisonResult;
        }

        return yourObjectComparisonResult;
    }
}
