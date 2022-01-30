/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Creation Time: Sep 1, 2020, 1:14:50 PM
 *
 * @author Mason Borenstein The Purpose of Background is to
 */
public class Background {

    ArrayList<Star> stars;

    public Background(int starCount) {
        stars = new ArrayList<>();
        for (int i = 0; i < starCount; i++) {
            stars.add(new Star());
        }
    }

    public void timeStep() {
        for (Star star : stars) {
            star.timeStep();
        }
    }

    public void draw(Graphics g, Space space) {
        g.setColor(space.lineColor);
        for (Star star : stars) {
            boolean touchingAsteroid = false;
            for (Asteroid a: space.rubble) {
                if (a.isTouching(star)) {
                    touchingAsteroid = true;
                }
            }
            boolean touchingAlien = false;
            for (Alien a: space.aliens) {
                if (a.isTouching(star)) {
                    touchingAlien = true;
                }
            }
            if (!touchingAsteroid && !touchingAlien) {
                star.draw(g);
            }
        }
    }
}
