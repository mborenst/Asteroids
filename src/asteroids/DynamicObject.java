// Mason
//  S To change this license header, 
//  choose License Headers in Project Properties.
//  S To change this template file, choose Tools | Templates
//  S and open the template in the editor.
// Borenstein
package asteroids;

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
public class DynamicObject extends GameObject {

    public double xSpeed;
    public double ySpeed;

    public DynamicObject() {
        super();
        xSpeed = 0;
        ySpeed = 0;
    }

    public DynamicObject(double xSpeed, double ySpeed) {
        super();
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public DynamicObject(double x, double y, double xSpeed, double ySpeed) {
        super(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public double getDirection() {
        return Math.toDegrees(Math.tan(y / x));
    }
    
    public boolean isTouching(GameObject other) {
        return other.getX() == x && other.getY() == y;
    }

    @Override
    public String toString() {
        return super.toString() + " going at (" + xSpeed + "," + ySpeed + ")";
    }
}
