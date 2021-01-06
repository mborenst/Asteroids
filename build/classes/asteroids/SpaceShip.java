/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author borenste_848114
 */
public class SpaceShip extends DynamicObject {

    private double direction;
    private boolean thrust;
    private int state;
    private long timeOfDeath = 0;
    private float hitTimer;
    private final float hitTime;
    private final float[] shapex = new float[4];
    private final float[] shapey = new float[4];
    private Line2D.Float[] hitLines;
    private Point2D.Float[] hitLinesVector;

    public SpaceShip() {
        super();
        x = 320;
        y = 320;
        direction = 90;
        state = 1;
        hitTimer = 0;
        hitTime = 2;
    }

    public SpaceShip(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        direction = 90;
        state = 1;
        hitTimer = 0;
        hitTime = 2;
    }

    public void setState(int s) {
        state = s;
        if (state == 0) {
            hitTimer = 0;
        }
    }

    public int getState() {
        return state;
    }

    public void setDirection(double d) {
        direction = d;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    public void setTimeOfDeath(long d) {
        timeOfDeath = d;
    }

    public double getTimeOfDeath() {
        return timeOfDeath;
    }

    public void rotateRight() {
        direction += 3;
        while (direction >= 360) {
            direction -= 360;
        }
    }

    public void rotateLeft() {
        direction -= 3;
        while (direction <= 0) {
            direction += 360;
        }
    }

    public void thrust() {
        thrust = true;
        xSpeed += -0.1 * Math.cos(Math.toRadians(direction));
        ySpeed += -0.1 * Math.sin(Math.toRadians(direction));
    }

    public void notThrust() {
        thrust = false;
        double i = (double) 1 / 100;
        if (Math.abs(xSpeed)
                - (i * Math.abs(Math.cos(Math.toRadians(
                                        Math.tan((double) ySpeed / xSpeed)))))
                < 0) {
            xSpeed = 0;
        } else if (xSpeed > 0) {
            xSpeed += -i * Math.cos(Math.toRadians(
                    Math.tan((double) ySpeed / xSpeed)));
        } else if (xSpeed < 0) {
            xSpeed += i * Math.cos(Math.toRadians(
                    Math.tan((double) ySpeed / xSpeed)));
        } else {

        }

        i *= 10;
        if (Math.abs(ySpeed)
                - (i * Math.abs(Math.sin(Math.toRadians(
                                        Math.tan((double) ySpeed / xSpeed)))))
                < 0) {
            ySpeed = 0;
        } else if (ySpeed > 0) {
            double yy = ySpeed;
            ySpeed += -i * Math.sin(Math.toRadians(
                    Math.tan((double) ySpeed / xSpeed)));
            if (Double.isNaN(ySpeed)) {
                ySpeed = yy;
                ySpeed += i * Math.sin(Math.toRadians(
                        Math.tan((double) 90)));
            }
        } else if (ySpeed < 0) {
            double yy = ySpeed;
            ySpeed += i * Math.sin(Math.toRadians(
                    Math.tan((double) ySpeed / xSpeed)));
            if (Double.isNaN(ySpeed)) {
                ySpeed = yy;
                ySpeed += -i * Math.sin(Math.toRadians(
                        Math.tan((double) 90)));
            }
        } else {

        }
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
    }

    @Override
    public void draw(Graphics g) {
        Color base = g.getColor();
        if (base == Color.PINK) {
            if (state == 1) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }
        }
        if (state == 1) {
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
                g.drawLine((int) Math.round(maxx),
                        (int) Math.round(maxy),
                        (int) Math.round(minx),
                        (int) Math.round(maxy));
                g.drawLine((int) Math.round(maxx),
                        (int) Math.round(maxy),
                        (int) Math.round(maxx),
                        (int) Math.round(miny));
                g.drawLine((int) Math.round(minx),
                        (int) Math.round(miny),
                        (int) Math.round(minx),
                        (int) Math.round(maxy));
                g.drawLine((int) Math.round(minx),
                        (int) Math.round(miny),
                        (int) Math.round(maxx),
                        (int) Math.round(miny));
            }
            shapex[0] = (float) x;
            shapey[0] = (float) y;

            shapex[1] = (float) (6 * Math.cos(
                    Math.toRadians(direction - 60)) + x);
            shapey[1] = (float) (6 * Math.sin(
                    Math.toRadians(direction - 60)) + y);

            shapex[2] = (float) (6 * Math.cos(
                    Math.toRadians(direction - 180)) + x);
            shapey[2] = (float) (6 * Math.sin(
                    Math.toRadians(direction - 180)) + y);

            shapex[3] = (float) (6 * Math.cos(
                    Math.toRadians(direction + 60)) + x);
            shapey[3] = (float) (6 * Math.sin(
                    Math.toRadians(direction + 60)) + y);

            g.drawLine((int) Math.round(shapex[0]),
                    (int) Math.round(shapey[0]),
                    (int) Math.round(shapex[1]),
                    (int) Math.round(shapey[1]));
            g.drawLine((int) Math.round(shapex[1]),
                    (int) Math.round(shapey[1]),
                    (int) Math.round(shapex[2]),
                    (int) Math.round(shapey[2]));
            g.drawLine((int) Math.round(shapex[2]),
                    (int) Math.round(shapey[2]),
                    (int) Math.round(shapex[3]),
                    (int) Math.round(shapey[3]));
            g.drawLine((int) Math.round(shapex[3]),
                    (int) Math.round(shapey[3]),
                    (int) Math.round(shapex[0]),
                    (int) Math.round(shapey[0]));
            if (thrust) {
                if (base == Color.PINK) {
                    int flame = (int) (Math.random() * 3);
                    if (flame == 0) {
                        g.setColor(Color.RED);
                    } else if (flame == 1) {
                        g.setColor(Color.ORANGE);
                    } else {
                        g.setColor(Color.YELLOW);
                    }
                }
                double x1 = 3 * Math.cos(Math.toRadians(direction - 60)) + x;
                double y1 = 3 * Math.sin(Math.toRadians(direction - 60)) + y;

                double x2 = 6 * Math.cos(Math.toRadians(direction)) + x;
                double y2 = 6 * Math.sin(Math.toRadians(direction)) + y;

                double x3 = 3 * Math.cos(Math.toRadians(direction + 60)) + x;
                double y3 = 3 * Math.sin(Math.toRadians(direction + 60)) + y;
                g.drawLine((int) Math.round(x1), (int) Math.round(y1),
                        (int) Math.round(x2), (int) Math.round(y2));
                g.drawLine((int) Math.round(x2), (int) Math.round(y2),
                        (int) Math.round(x3), (int) Math.round(y3));
            }
        } else if (state == 2) {
            // Dying
            if (hitLines != null) {
                for (Line2D.Float hitLine : hitLines) {
                    if (hitLine != null) {
                        g.drawLine((int) Math.round(hitLine.x1),
                                (int) Math.round(hitLine.y1),
                                (int) Math.round(hitLine.x2),
                                (int) Math.round(hitLine.y2));
                    }
                }
            }
        } else {
            // Awaiting Respawn
        }
    }

    public void printHitBox() {
        double minX = x - 3;
        double maxX = x + 3;
        double minY = y + 3;
        double maxY = y - 3;
        System.out.println("Left X: " + minX);
        System.out.println("Right X: " + maxX);
        System.out.println("Bottom Y: " + minY);
        System.out.println("Top Y: " + maxY);
    }

    @Override
    public boolean isTouching(GameObject go) {
        double minX = x - 3;
        double maxX = x + 3;
        double minY = y + 3;
        double maxY = y - 3;
        return (state == 1) && (go.getX() < maxX && go.getX() > minX)
                && (go.getY() < maxY && go.getY() > minY);
    }

    public double getMaxX() {
        return x + 3;
    }

    public double getMinX() {
        return x - 3;
    }

    public double getMaxY() {
        return y - 3;
    }

    public double getMinY() {
        return y + 3;
    }

    public void updateDeath() {
        hitTimer += 0.01666666666;
        if (hitTimer > hitTime) {
            hitTimer = 0;
            state = 3;
        }
        for (int i = 0; i < hitLines.length; i++) {
            hitLines[i].setLine(
                    hitLines[i].x1 + hitLinesVector[i].x * 10 * 0.01666666666,
                    hitLines[i].y1 + hitLinesVector[i].y * 10 * 0.01666666666,
                    hitLines[i].x2 + hitLinesVector[i].x * 10 * 0.01666666666,
                    hitLines[i].y2 + hitLinesVector[i].y * 10 * 0.01666666666
            );
        }
    }

    public void hit() {
        state = 2;
        xSpeed = 0;
        ySpeed = 0;
        hitLines = new Line2D.Float[4];
        for (int i = 0; i < hitLines.length; i++) {
            int j = i - 1;
            if (j < 0) {
                j = hitLines.length - 1;
            }
            hitLines[i] = new Line2D.Float(shapex[i], shapey[i],
                    shapex[j], shapey[j]);
        }
        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(
                (float) Math.cos(direction + 1.5f),
                (float) Math.sin(direction + 1.5f));
        hitLinesVector[1] = new Point2D.Float(
                (float) Math.cos(direction - 1.5f),
                (float) Math.sin(direction - 1.5f));
        hitLinesVector[2] = new Point2D.Float(
                (float) Math.cos(direction - 2.8f),
                (float) Math.sin(direction - 2.8f));
        hitLinesVector[3] = new Point2D.Float(
                (float) Math.cos(direction + 2.8f),
                (float) Math.sin(direction + 2.8f));
    }

    public void setShape() {
        shapex[0] = (float) x;
        shapey[0] = (float) y;

        shapex[1] = (float) (6 * Math.cos(
                Math.toRadians(direction - 60)) + x);
        shapey[1] = (float) (6 * Math.sin(
                Math.toRadians(direction - 60)) + y);

        shapex[2] = (float) (6 * Math.cos(
                Math.toRadians(direction - 180)) + x);
        shapey[2] = (float) (6 * Math.sin(
                Math.toRadians(direction - 180)) + y);

        shapex[3] = (float) (6 * Math.cos(
                Math.toRadians(direction + 60)) + x);
        shapey[3] = (float) (6 * Math.sin(
                Math.toRadians(direction + 60)) + y);
    }

    @Override
    public String toString() {
        return "Spaceship " + super.toString() + ", or going "
                + direction + " degrees at " + Math.sqrt(
                        Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2))
                + " pixel(s)/cycle";
    }
}
