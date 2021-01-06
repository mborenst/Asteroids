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
public class Meteor extends Asteroid {

    public Meteor() {
        super();
    }

    public Meteor(double d, double x, double y) {
        super();
        this.x = x;
        this.y = y;
        double speed = Math.random() + .75;
        xSpeed = speed * Math.cos(Math.toRadians(d));
        ySpeed = speed * Math.sin(Math.toRadians(d));
        score = 50;
        dists = new float[10];
        shapex = new float[10];
        shapey = new float[10];
        rotationSpeed = ((Math.random() * 4) - 2) / 30;
        setShape();
    }

    @Override
    public void draw(Graphics g) {
        updateShape();
        if (Space.showHitBoxes) {
            double minx = Double.MAX_VALUE;
            double maxx = Double.MIN_VALUE;
            double miny = Double.MAX_VALUE;
            double maxy = Double.MIN_VALUE;
            for (int i = 0; i < shapex.length; i++) {
                if (shapex[i] < minx) {
                    minx = shapex[i];
                }
                if (shapex[i] > maxx) {
                    maxx = shapex[i];
                }
                if (shapey[i] < miny) {
                    miny = shapey[i];
                }
                if (shapey[i] > maxy) {
                    maxy = shapey[i];
                }
            }
            g.drawLine((int) Math.round(x + maxx),
                    (int) Math.round(y + maxy),
                    (int) Math.round(x + minx),
                    (int) Math.round(y + maxy));
            g.drawLine((int) Math.round(x + maxx),
                    (int) Math.round(y + maxy),
                    (int) Math.round(x + maxx),
                    (int) Math.round(y + miny));
            g.drawLine((int) Math.round(x + minx),
                    (int) Math.round(y + miny),
                    (int) Math.round(x + minx),
                    (int) Math.round(y + maxy));
            g.drawLine((int) Math.round(x + minx),
                    (int) Math.round(y + miny),
                    (int) Math.round(x + maxx),
                    (int) Math.round(y + miny));
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

    private void setShape() {
        int numPoints = 10;
        double width = 25;
        double height = 25;
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
        for (int i = 0; i < 10; i++) {
            shapex[i] = (float) ((float) Math.cos(angle + radians) * dists[i]);
            shapey[i] = (float) ((float) Math.sin(angle + radians) * dists[i]);
            angle += (2 * Math.PI) / 10;
        }
    }

    @Override
    public boolean isTouching(GameObject other) {
        boolean tOrf;
        if (shapex != null && shapey != null) {
            double minx = Double.MAX_VALUE;
            double maxx = Double.MIN_VALUE;
            double miny = Double.MAX_VALUE;
            double maxy = Double.MIN_VALUE;
            for (int i = 0; i < shapex.length; i++) {
                if (shapex[i] < minx) {
                    minx = shapex[i];
                }
                if (shapex[i] > maxx) {
                    maxx = shapex[i];
                }
                if (shapey[i] < miny) {
                    miny = shapey[i];
                }
                if (shapey[i] > maxy) {
                    maxy = shapey[i];
                }
            }
            if (other instanceof SpaceShip) {
                SpaceShip o = (SpaceShip) other;
                tOrf = (o.getMaxX() < x + maxx && o.getMaxX() > x + minx)
                        || (o.getMinX() < x + maxx && o.getMinX() > x + minx);
                tOrf = ((o.getMaxY() < y + maxy && o.getMaxY() > y + miny)
                        || (o.getMinY() < y + maxy && o.getMinY() > y + miny)) && tOrf;
                return tOrf;
            }
            tOrf = other.getX() < x + maxx && other.getX() > x + minx;
            tOrf = other.getY() < y + maxy && other.getY() > y + miny && tOrf;
            return tOrf;
        } else {
            if (other instanceof SpaceShip) {
                SpaceShip o = (SpaceShip) other;
                tOrf = (o.getMaxX() < x + (25 / 2)
                        && o.getMaxX() > x - (25 / 2))
                        || (o.getMinX() < x + (25 / 2)
                        && o.getMinX() > x - (25 / 2));
                tOrf = ((o.getMaxY() < y + (25 / 2)
                        && o.getMaxY() > y - (25 / 2))
                        || (o.getMinY() < y + (25 / 2)
                        && o.getMinY() > y - (25 / 2))) && tOrf;
                return tOrf;
            }
            tOrf = other.getX() < x + (25 / 2) && other.getX() > x - (25 / 2);
            tOrf = other.getY() < y + (25 / 2) && other.getY() > y - (25 / 2)
                    && tOrf;
            return tOrf;
        }
    }

    @Override
    public String toString() {
        return "Smaller " + super.toString();
    }
}
