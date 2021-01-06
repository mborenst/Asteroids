// Mason
//  To change this license header, choose License Headers in Project Properties.
//  To change this template file, choose Tools | Templates
//  and open the template in the editor.
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
public class Alien_Laser extends Laser {

    public Alien_Laser() {
        super();
    }

    public Alien_Laser(Alien a) {
        super(a);
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) Math.round(x - 1), (int) Math.round(y - 1), 2, 2);
    }

    @Override
    public String toString() {
        return "Alien " + super.toString();
    }
}
