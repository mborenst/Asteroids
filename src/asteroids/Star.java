/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Creation Time: Sep 1, 2020, 1:25:16 PM
 *
 * @author Mason Borenstein The Purpose of Star is to
 */
public class Star extends DynamicObject {

    public double showTime;
    public boolean show;
    public Color color;

    public Star() {
        super(100, 0);
        x = Math.random() * Space.BOARD_WIDTH;
        y = Math.random() * Space.BOARD_HEIGHT;
        xSpeed = 0;
        ySpeed = 1;
        show = Math.random() < .5;
        showTime = Math.random();
        int tmp = (int) Math.round(Math.random() * 6);
        switch (tmp) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.ORANGE;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.BLUE;
                break;
            case 5:
                color = Color.MAGENTA;
                break;
            default:
                color = Color.CYAN;
                break;
        }
    }

    public void reset(int i) {
        x = Math.random() * Space.BOARD_WIDTH;
        y = i;
        xSpeed = 0;
        ySpeed = 1;
        show = Math.random() < .5;
        showTime = Math.random();
        int tmp = (int) Math.round(Math.random() * 6);
        switch (tmp) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.ORANGE;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.BLUE;
                break;
            case 5:
                color = Color.MAGENTA;
                break;
            default:
                color = Color.CYAN;
                break;
        }
    }

    public void timeStep() {
        y += ySpeed;
        if (isOffScreen()) {
            reset(0);
        }
    }

    public boolean isOffScreen() {
        return (y < 0 || y > Space.BOARD_HEIGHT)
                || (x < 0 || x > Space.BOARD_WIDTH);
    }

    @Override
    public void draw(Graphics g) {
        showTime -= 1.0 / 60;
        if (showTime < 0) {
            show = !show;
            showTime = Math.random();
        }
        if (show) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
            Color tmp = g2.getColor();
            if (tmp.getRGB() == Color.PINK.getRGB()) {
                g2.setColor(color);
            }
            g2.fillRect((int) Math.round(x), (int) Math.round(y), 2, 2);
            g2.setColor(tmp);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
