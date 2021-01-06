/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Color;

/**
 *
 * @author Borenste_848114
 */
public class Firework extends Particle {

    private Color color;

    public Firework(float x, float y) {
        super(x, y);
        int c = (int) (Math.random() * 7);
        if (c == 0) {
            color = Color.RED;
        } else if (c == 1) {
            color = Color.ORANGE;
        } else if (c == 2) {
            color = Color.YELLOW;
        } else if (c == 3) {
            color = Color.GREEN;
        } else if (c == 4) {
            color = Color.BLUE;
        } else if (c == 5) {
            color = Color.MAGENTA;
        } else {
            color = Color.WHITE;
        }
    }

    public Color getColor() {
        return color;
    }
}
