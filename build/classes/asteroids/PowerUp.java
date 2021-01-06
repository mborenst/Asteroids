/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author borenste_848114
 */
public class PowerUp extends DynamicObject {

    private int power;
    private boolean inEffect;

    public PowerUp() {
        super();
        x = Math.random() * Space.BOARD_WIDTH;
        y = Space.BOARD_HEIGHT;
        xSpeed = 0;
        ySpeed = 1;
        power = (int) (Math.random() * 1);
        inEffect = false;
    }

    @Override
    public void draw(Graphics g) {
        // if (Space.showHitBoxes) {
        g.setColor(Color.GREEN);
        g.fillRect((int) Math.round(x - 10), (int) Math.round(y - 10), 20, 20);
        g.setColor(Color.PINK);
    }

    public int move() {
        x += xSpeed;
        if (x > Space.BOARD_WIDTH) {
            x = 0;
        }
        if (x < 0) {
            x = Space.BOARD_WIDTH;
        }
        y += ySpeed;
        if (y >= Space.BOARD_HEIGHT) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getPower() {
        return power;
    }

    public void setEffect(boolean e) {
        inEffect = e;
    }

    public boolean getEffect() {
        return inEffect;
    }

    public boolean isTouching(DynamicObject other) {
        if (other instanceof SpaceShip) {
            SpaceShip o = (SpaceShip) other;
            return ((o.getMaxX() < x + (25 / 2)
                    && o.getMaxX() > x - (25 / 2))
                    || (o.getMinX() < x + (25 / 2)
                    && o.getMinX() > x - (25 / 2)))
                    && ((o.getMaxY() < y + (25 / 2)
                    && o.getMaxY() > y - (25 / 2))
                    || (o.getMinY() < y + (25 / 2)
                    && o.getMinY() > y - (25 / 2)));
        }
        return other.getX() < x + (25 / 2) && other.getX() > x - (25 / 2)
                && other.getY() < y + (25 / 2) && other.getY() > y - (25 / 2);
    }

    @Override
    public String toString() {
        String i = "PowerUp " + super.toString();
        i = power + " " + i;
        return i;
    }
}
