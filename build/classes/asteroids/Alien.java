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
public class Alien extends DynamicObject {

    public double laserDirection;
    public int score = 200;
    public long startTime;
    public float fireTimer;
    public float fireTime;
    public float pathTimer;
    public float pathTime1;
    public boolean pt1;
    public float pathTime2;
    public boolean pt2;
    public float pathTime3;
    public boolean pt3;
    public float pathTime4;
    public boolean pt4;
    public boolean xChange;
    public int fires;

    public Alien() {
        super();
        fires = 0;
        x = 1;
        y = Math.random() * Space.BOARD_HEIGHT;
        xSpeed = 1;
        int i = (int) Math.round(Math.random());
        if (i != 0) {
            xSpeed *= -1;
            x = Space.BOARD_WIDTH - 1;
        }
        ySpeed = 0;
        fireTimer = 0;
        fireTime = 1;
        resetMoves();
    }

    public int getScore() {
        return score;
    }

    public boolean move() {
        y += ySpeed;
        if (y > Space.BOARD_HEIGHT) {
            y = 0;
        }
        if (y < 0) {
            y = Space.BOARD_HEIGHT;
        }
        x += xSpeed;
        if (x > Space.BOARD_WIDTH && xSpeed > 0 && !xChange) {
            return true;
        } else if (x < Space.BOARD_WIDTH) {

        } else {
            x = 1;
        }
        if (x < 0 && xSpeed < 0 && !xChange) {
            return true;
        } else if (x > 0) {

        } else {
            x = Space.BOARD_WIDTH - 1;
        }
        return false;
    }

    public void decideDirection() {
        pathTimer += 0.01666666666;
        if (pathTimer < pathTime1) {
            ySpeed = 0;
        }
        if (pathTimer > pathTime1 && pathTimer < pathTime2 && !pt1) {
            ySpeed = Math.random() * 3 - 1.5;
            pt1 = true;
        }
        if (pathTimer > pathTime1 && pathTimer > pathTime2 && !pt2
                && pathTimer < pathTime3 && pathTimer < pathTime4) {
            ySpeed = 0;
            if (xSpeed > 0) {
                xSpeed = -xSpeed;
                xChange = !xChange;
            }
            pt2 = true;
        }
        if (pathTimer < pathTime3 && pathTimer > pathTime2) {
            ySpeed = 0;
        }
        if (pathTimer > pathTime3 && pathTimer < pathTime4 && !pt3) {
            if (xChange) {
                xSpeed = -xSpeed;
                xChange = !xChange;
            }
            ySpeed = Math.random() * 3 - 1.5;
            pt3 = true;
        }
        if (pathTimer > pathTime3 && pathTimer > pathTime4 && !pt4) {
            resetMoves();
        }
    }

    public double getLaserDirection() {
        return laserDirection;
    }

    public double shoot(SpaceShip player) {
        if (player.getState() != 1) {
            return -1;
        } else {
            fireTimer += 0.01666666666;
            if (fireTimer > fireTime) {
                fireTimer = 0;
                laserDirection = Math.random() * 360;
                return 0;
            }
            return -1;
        }
    }

    public void setFire(int i) {
        fires = i;
    }

    @Override
    public void draw(Graphics g) {
        Color base = g.getColor();
        if (Space.showHitBoxes) {
            g.drawLine((int) Math.round(x - 15),
                    (int) Math.round(y - 7.5),
                    (int) Math.round(x + 15),
                    (int) Math.round(y - 7.5));
            g.drawLine((int) Math.round(x - 15),
                    (int) Math.round(y - 7.5),
                    (int) Math.round(x - 15),
                    (int) Math.round(y + 7.5));
            g.drawLine((int) Math.round(x + 15),
                    (int) Math.round(y + 7.5),
                    (int) Math.round(x - 15),
                    (int) Math.round(y + 7.5));
            g.drawLine((int) Math.round(x + 15),
                    (int) Math.round(y + 7.5),
                    (int) Math.round(x + 15),
                    (int) Math.round(y - 7.5));
        }
        if (base == Color.PINK) {
            int color = (int) (Math.random() * 6);
            if (color == 0) {
                g.setColor(Color.YELLOW);
            } else if (color == 1) {
                g.setColor(Color.RED);
            } else if (color == 2) {
                g.setColor(Color.ORANGE);
            } else if (color == 3) {
                g.setColor(Color.CYAN);
            } else if (color == 4) {
                g.setColor(Color.MAGENTA);
            } else {
                g.setColor(Color.GREEN);
            }
        }
        g.drawLine((int) Math.round(x - 15),
                (int) Math.round(y + 2.5),
                (int) Math.round(x + 15),
                (int) Math.round(y + 2.5));

        if (base == Color.PINK) {
            g.setColor(Color.WHITE);
        }
        g.drawLine((int) Math.round(x - 15),
                (int) Math.round(y + 2.5),
                (int) Math.round(x - 5),
                (int) Math.round(y + 7.5));
        g.drawLine((int) Math.round(x + 15),
                (int) Math.round(y + 2.5),
                (int) Math.round(x + 5),
                (int) Math.round(y + 7.5));
        g.drawLine((int) Math.round(x - 5),
                (int) Math.round(y + 7.5),
                (int) Math.round(x + 5),
                (int) Math.round(y + 7.5));

        if (base == Color.PINK) {
            g.setColor(Color.WHITE);
        }
        g.drawLine((int) Math.round(x - 15),
                (int) Math.round(y + 2.5),
                (int) Math.round(x - 5),
                (int) Math.round(y - 2.5));
        g.drawLine((int) Math.round(x + 15),
                (int) Math.round(y + 2.5),
                (int) Math.round(x + 5),
                (int) Math.round(y - 2.5));
        g.drawLine((int) Math.round(x - 5),
                (int) Math.round(y - 2.5),
                (int) Math.round(x + 5),
                (int) Math.round(y - 2.5));

        if (base == Color.PINK) {
            g.setColor(Color.CYAN);
        }
        g.drawLine((int) Math.round(x - 5),
                (int) Math.round(y - 2.5),
                (int) Math.round(x - 3),
                (int) Math.round(y - 7.5));
        g.drawLine((int) Math.round(x + 5),
                (int) Math.round(y - 2.5),
                (int) Math.round(x + 3),
                (int) Math.round(y - 7.5));
        g.drawLine((int) Math.round(x - 3),
                (int) Math.round(y - 7.5),
                (int) Math.round(x + 3),
                (int) Math.round(y - 7.5));
    }

    @Override
    public boolean isTouching(GameObject go) {
        if (go instanceof SpaceShip) {
            SpaceShip c = (SpaceShip) go;
            return ((c.getMaxX() < x + 15 && c.getMaxX() > x - 15)
                    || (c.getMinX() < x + 15 && c.getMinX() > x - 15))
                    && ((c.getMaxY() < y + 7.5 && c.getMaxY() > y - 7.5)
                    || (c.getMinY() < y + 7.5 && c.getMinY() > y - 7.5));
        }
        return (go.getX() < x + 15 && go.getX() > x - 15)
                && (go.getY() < y + 7.5 && go.getY() > y - 7.5);
    }

    private void resetMoves() {
        pathTimer = 0;
        pathTime1 = (float) (Math.random() * 2) + 1;
        pt1 = false;
        pathTime2 = (float) (pathTime1 + (Math.random() * 3));
        pt2 = false;
        pathTime3 = (float) (pathTime2 + (Math.random() * 3));
        pt3 = false;
        pathTime4 = (float) (pathTime3 + (Math.random() * 3));
        pt4 = false;
    }

    @Override
    public String toString() {
        return "Alien " + super.toString();
    }
}
