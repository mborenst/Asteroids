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
public class Alien_Small extends Alien {

    public Alien_Small() {
        super();
        xSpeed *= 2;
        score = 1000;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public double shoot(SpaceShip player) {
        if (player.getState() != 1) {
            return -1;
        } else {
            fireTimer += 0.01666666666;
            if (fireTimer > fireTime) {
                if (fires > 0) {
                    fireTimer = 0;
                    double yy = (player.y - y);
                    double xx = (player.x - x);
                    double ddd = yy / xx;
                    laserDirection = Math.atan((ddd));
                    laserDirection += Math.toRadians(
                            (Math.random() * .2) - .1);
                } else {
                    fireTimer = 0;
                    laserDirection = Math.toRadians(Math.random() * 360);
                }
                fires++;
                return 0;
            }
            return -1;
        }
    }

    @Override
    public boolean isTouching(GameObject go) {
        if (go instanceof SpaceShip) {
            SpaceShip c = (SpaceShip) go;
            return ((c.getMaxX() < x + ((double) 15 / 2)
                    && c.getMaxX() > x - ((double) 15 / 2))
                    || (c.getMinX() < x + ((double) 15 / 2)
                    && c.getMinX() > x - ((double) 15 / 2)))
                    && ((c.getMaxY() < y + ((double) 7.5 / 2)
                    && c.getMaxY() > y - ((double) 7.5 / 2))
                    || (c.getMinY() < y + ((double) 7.5 / 2)
                    && c.getMinY() > y - ((double) 7.5 / 2)));
        }
        return (go.getX() < x + ((double) 15 / 2) && go.getX()
                > x - ((double) 15 / 2))
                && (go.getY() < y + ((double) 7.5 / 2) && go.getY()
                > y - ((double) 7.5 / 2));
    }

    @Override
    public void draw(Graphics g) {
        Color base = g.getColor();

        if (Space.showHitBoxes) {
            g.drawLine((int) Math.round(x - 15 / 2),
                    (int) Math.round(y - 7.5 / 2),
                    (int) Math.round(x + 15 / 2),
                    (int) Math.round(y - 7.5 / 2));
            g.drawLine((int) Math.round(x - 15 / 2),
                    (int) Math.round(y - 7.5 / 2),
                    (int) Math.round(x - 15 / 2),
                    (int) Math.round(y + 7.5 / 2));
            g.drawLine((int) Math.round(x + 15 / 2),
                    (int) Math.round(y + 7.5 / 2),
                    (int) Math.round(x - 15 / 2),
                    (int) Math.round(y + 7.5 / 2));
            g.drawLine((int) Math.round(x + 15 / 2),
                    (int) Math.round(y + 7.5 / 2),
                    (int) Math.round(x + 15 / 2),
                    (int) Math.round(y - 7.5 / 2));
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
        g.drawLine((int) Math.round(x - (15 / 2)),
                (int) Math.round(y + (2.5 / 2)),
                (int) Math.round(x + (15 / 2)),
                (int) Math.round(y + (2.5 / 2)));

        if (base == Color.PINK) {
            g.setColor(Color.WHITE);
        }
        g.drawLine((int) Math.round(x - (15 / 2)),
                (int) Math.round(y + (2.5 / 2)),
                (int) Math.round(x - (5 / 2)),
                (int) Math.round(y + (7.5 / 2)));
        g.drawLine((int) Math.round(x + (15 / 2)),
                (int) Math.round(y + (2.5 / 2)),
                (int) Math.round(x + (5 / 2)),
                (int) Math.round(y + (7.5) / 2));
        g.drawLine((int) Math.round(x - (5 / 2)),
                (int) Math.round(y + (7.5 / 2)),
                (int) Math.round(x + (5 / 2)),
                (int) Math.round(y + (7.5 / 2)));

        if (base == Color.PINK) {
            g.setColor(Color.WHITE);
        }
        g.drawLine((int) Math.round(x - (15 / 2)),
                (int) Math.round(y + (2.5 / 2)),
                (int) Math.round(x - (5 / 2)),
                (int) Math.round(y - (2.5 / 2)));
        g.drawLine((int) Math.round(x + (15 / 2)),
                (int) Math.round(y + (2.5 / 2)),
                (int) Math.round(x + (5 / 2)),
                (int) Math.round(y - (2.5 / 2)));
        g.drawLine((int) Math.round(x - (5 / 2)),
                (int) Math.round(y - (2.5 / 2)),
                (int) Math.round(x + (5 / 2)),
                (int) Math.round(y - (2.5 / 2)));

        if (base == Color.PINK) {
            g.setColor(Color.CYAN);
        }
        g.drawLine((int) Math.round(x - (5 / 2)),
                (int) Math.round(y - (2.5 / 2)),
                (int) Math.round(x - (3 / 2)),
                (int) Math.round(y - (7.5 / 2)));
        g.drawLine((int) Math.round(x + (5 / 2)),
                (int) Math.round(y - (2.5 / 2)),
                (int) Math.round(x + (3 / 2)),
                (int) Math.round(y - (7.5 / 2)));
        g.drawLine((int) Math.round(x - (3 / 2)),
                (int) Math.round(y - (7.5 / 2)),
                (int) Math.round(x + (3 / 2)),
                (int) Math.round(y - (7.5 / 2)));
    }

    @Override
    public String toString() {
        return "Small " + super.toString();
    }
}
