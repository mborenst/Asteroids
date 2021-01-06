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
public class Laser extends DynamicObject {

    private int id;
    private double distance;

    public Laser() {
        super();
    }

    public Laser(Alien a) {
        super();
        distance = 0;
        this.id = 0;
        x = a.getX();
        y = a.getY();
        double d = a.getLaserDirection();
        xSpeed = -6 * Math.cos(d);
        ySpeed = -6 * Math.sin(d);
    }

    public Laser(SpaceShip s, int id) {
        super();
        distance = 0;
        this.id = id + 1;
        x = s.getX();
        y = s.getY();
        double d = s.getDirection();
        xSpeed = -6 * Math.cos(Math.toRadians(d));
        ySpeed = -6 * Math.sin(Math.toRadians(d));
    }

    public Laser(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) Math.round(x - 1), (int) Math.round(y - 1), 2, 2);
    }

    public boolean move() {
        distance += 5;
        x += xSpeed;
        if (x > Space.BOARD_WIDTH) {
            x = 0;
        }
        if (x < 0) {
            x = Space.BOARD_WIDTH;
        }
        y += ySpeed;
        if (y > Space.BOARD_HEIGHT) {
            y = 0;
        }
        if (y < 0) {
            y = Space.BOARD_HEIGHT;
        }
        return distance >= 800;
    }

    @Override
    public boolean isTouching(GameObject other) {
        boolean tOrf;
        if (other instanceof SpaceShip) {
            SpaceShip o = (SpaceShip) other;
            tOrf = (o.getMaxX() < x + 2.5 && o.getMaxX() > x - 2.5)
                    || (o.getMinX() < x + 2.5 && o.getMinX() > x - 2.5);
            tOrf = ((o.getMaxY() < y + 2.5 && o.getMaxY() > y - 2.5)
                    || (o.getMinY() < y + 2.5 && o.getMinY() > y - 2.5)) && tOrf;
            return tOrf;
        }
        tOrf = other.getX() < x + 2.5 && other.getX() > x - 2.5;
        tOrf = other.getY() < y + 2.5 && other.getY() > y - 2.5 && tOrf;
        return tOrf;
    }

    @Override
    public String toString() {
        return "Laser " + id + " " + super.toString();
    }
}
