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
public class Particle extends DynamicObject {

    private float timer;
    private final float time;
    private boolean remove;
    private final float speed;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        speed = (float) (Math.random() * 1);
        double d = Math.random() * 360;
        xSpeed = speed * Math.cos(d);
        ySpeed = speed * Math.sin(d);
        timer = 0;
        time = 1;
    }

    public Particle(float x, float y, float t) {
        this.x = x;
        this.y = y;
        speed = (float) (Math.random() * 1);
        double d = Math.random() * 360;
        xSpeed = speed * Math.cos(d);
        ySpeed = speed * Math.sin(d);
        timer = 0;
        time = t;
    }

    public boolean getRemove() {
        return remove;
    }

    public void move() {
        timer += 0.016666666666666;
        x += xSpeed;
        y += ySpeed;
        if (timer > time) {
            remove = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.fillOval((int) Math.round(x - 1), (int) Math.round(y - 1), 2, 2);
    }
}
