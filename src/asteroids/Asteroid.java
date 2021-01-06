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
public class Asteroid extends DynamicObject {

    public int score;
    public float[] dists;
    public float[] shapex;
    public float[] shapey;
    public double radians;
    public double rotationSpeed;
    public Color color;

    public Asteroid() {
        x = 100;
        y = 100;
        xSpeed = .5;
        ySpeed = .5;
        score = 20;
        dists = new float[12];
        shapex = new float[12];
        shapey = new float[12];
        if (Math.round(Math.random()) == 0) {
            rotationSpeed = .005;
        } else {
            rotationSpeed = -.005;
        }
        int c = (int) (Math.random() * 10);
        if (c == 0) {
            color = new Color(139, 69, 19);
        } else if (c == 1) {
            color = new Color(160, 82, 45);
        } else if (c == 2) {
            color = new Color(165, 42, 42);
        } else if (c == 3) {
            color = new Color(228, 0, 0);
        } else if (c == 4) {
            color = new Color(210, 105, 30);
        } else if (c == 5) {
            color = new Color(205, 133, 63);
        } else if (c == 6) {
            color = new Color(222, 184, 135);
        } else if (c == 7) {
            color = Color.WHITE;
        } else if (c == 8) {
            color = Color.LIGHT_GRAY;
        } else {
            color = Color.GRAY;
        }
        setShape();
    }

    public Asteroid(double x, double y, double d) {
        this.x = x;
        if (this.x < 0) {
            this.x = Space.BOARD_WIDTH + x;
        }
        this.y = y;
        if (this.y < 0) {
            this.y = Space.BOARD_HEIGHT + y;
        }
        xSpeed = Math.cos(Math.toRadians(d));
        ySpeed = Math.sin(Math.toRadians(d));
        score = 20;
        dists = new float[12];
        shapex = new float[12];
        shapey = new float[12];
        rotationSpeed = ((Math.random() * 2) - 1) / 30;
        int c = (int) (Math.random() * 8);
        if (c == 0) {
            color = new Color(139, 69, 19);
        } else if (c == 1) {
            color = new Color(160, 82, 45);
        } else if (c == 2) {
            color = new Color(165, 42, 42);
        } else if (c == 3) {
            color = new Color(128, 0, 0);
        } else if (c == 4) {
            color = new Color(210, 105, 30);
        } else if (c == 5) {
            color = new Color(205, 133, 63);
        } else if (c == 6) {
            color = new Color(222, 184, 135);
        } else {
            color = Color.GRAY;
        }
        setShape();
    }

    public int getScore() {
        return score;
    }

    public void move() {
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
        radians += rotationSpeed;
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
                tOrf = (o.getMaxX() < x + 25 && o.getMaxX() > x - 25)
                        || (o.getMinX() < x + 25 && o.getMinX() > x - 25);
                tOrf = ((o.getMaxY() < y + 25 && o.getMaxY() > y - 25)
                        || (o.getMinY() < y + 25 && o.getMinY() > y - 25)) && tOrf;
                return tOrf;
            }
            tOrf = other.getX() < x + 25 && other.getX() > x - 25;
            tOrf = other.getY() < y + 25 && other.getY() > y - 25 && tOrf;
            return tOrf;
        }
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
        int numPoints = 12;
        double width = 50;
        double height = 50;
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

    public void updateShape() {
        float angle = 0;
        for (int i = 0; i < 12; i++) {
            shapex[i] = (float) ((float) Math.cos(angle + radians) * dists[i]);
            shapey[i] = (float) ((float) Math.sin(angle + radians) * dists[i]);
            angle += (2 * Math.PI) / 12;
        }
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Asteroid " + super.toString();
    }
}
