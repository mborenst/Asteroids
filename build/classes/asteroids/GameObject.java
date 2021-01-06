// Mason
//  S To change this license header, 
//  choose License Headers in Project Properties.
//  S To change this template file, choose Tools | Templates
//  S and open the template in the editor.
// Borenstein
package asteroids;

import java.awt.Graphics;

/**
 * The Purpose of Class $(name) is to
 * <p>
 * Teacher :: $(teacher)
 * <p>
 * Class :: $(class)
 * <p>
 * Author :: $(user)
 *
 * @author Mason Borenstein
 */
public class GameObject {
    public double x;
    public double y;

    public GameObject() {
        x = 0;
        y = 0;
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getX() {
        return x;
    }
        
    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.fillRect((int) Math.round(x), (int) Math.round(y), 1, 1);
    }

    @Override
    public String toString() {
        return "Object at (" + x + "," + y + ")";
    }
}
