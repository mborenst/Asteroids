/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Graphics;

/**
 *
 * @author borenste_848114
 */
public class Meteorite extends Meteor {

    public float[] dists;
    public float[] shapex;
    public float[] shapey;
    public double radians;
    public double rotationSpeed;

    public Meteorite(double d, double x, double y) {
        super();
        this.x = x;
        this.y = y;
        double speed = Math.random() + 1.25;
        xSpeed = speed * Math.cos(Math.toRadians(d));
        ySpeed = speed * Math.sin(Math.toRadians(d));
        score = 100;
        dists = new float[8];
        shapex = new float[8];
        shapey = new float[8];
        rotationSpeed = ((Math.random() * 6) - 3) / 30;
        setShape();
    }

    @Override
    public void draw(Graphics g) {
        updateShape();
        if (Space.showHitBoxes) {
            g.drawLine((int) Math.round(x + 7),
                    (int) Math.round(y + 7),
                    (int) Math.round(x - 7),
                    (int) Math.round(y + 7));
            g.drawLine((int) Math.round(x + 7),
                    (int) Math.round(y + 7),
                    (int) Math.round(x + 7),
                    (int) Math.round(y - 7));
            g.drawLine((int) Math.round(x - 7),
                    (int) Math.round(y - 7),
                    (int) Math.round(x - 7),
                    (int) Math.round(y + 7));
            g.drawLine((int) Math.round(x - 7),
                    (int) Math.round(y - 7),
                    (int) Math.round(x + 7),
                    (int) Math.round(y - 7));
        }
        for (int i = 0; i < shapex.length; i++) {
            int j = i - 1;
            if (j < 0) {
                j = shapex.length - 1;
            }
            g.drawLine((int) Math.round(x + shapex[i]),
                    (int) Math.round(y + shapey[i]),
                    (int) Math.round(x + shapex[j]),
                    (int) Math.round(y + shapey[j]));
        }
    }

    @Override
    public boolean isTouching(GameObject other) {
        boolean tOrf = false;
        if (other instanceof SpaceShip) {
            SpaceShip o = (SpaceShip) other;
            tOrf = (o.getMaxX() < x + 7 && o.getMaxX() > x - 7)
                    || (o.getMinX() < x + 7 && o.getMinX() > x - 7);
            tOrf = ((o.getMaxY() < y + 7 && o.getMaxY() > y - 7)
                    || (o.getMinY() < y + 7 && o.getMinY() > y - 7)) && tOrf;
            return tOrf;
        }
        tOrf = other.getX() < x + 7 && other.getX() > x - 7;
        tOrf = other.getY() < y + 7 && other.getY() > y - 7 && tOrf;
        return tOrf;
    }

    private void setShape() {
        int numPoints = 8;
        double width = 14;
        double height = 14;
        radians = Math.random() * (2 * Math.PI);
        int radius = (int) Math.round(width / 2);
        dists = new float[numPoints];
        shapex = new float[numPoints];
        shapey = new float[numPoints];
        for (int i = 0; i < numPoints; i++) {
            dists[i] = (float) ((Math.random() * (radius / 2)) + (radius / 2));
        }
        float angle = 0;
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = (float) ((float) Math.cos(angle + radians) * dists[i]);
            shapey[i] = (float) ((float) Math.sin(angle + radians) * dists[i]);
            angle += (2 * Math.PI) / numPoints;
        }
    }

    @Override
    public void updateShape() {
        float angle = 0;
        for (int i = 0; i < 8; i++) {
            shapex[i] = (float) ((float) Math.cos(angle + radians) * dists[i]);
            shapey[i] = (float) ((float) Math.sin(angle + radians) * dists[i]);
            angle += (2 * Math.PI) / 8;
        }
    }

    @Override
    public String toString() {
        return "Even " + super.toString();
    }
}
