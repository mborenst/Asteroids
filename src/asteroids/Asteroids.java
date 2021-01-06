/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * This game is a copy of 1979 Atari Asteroids
 *
 * @author borenste_848114
 */
public class Asteroids extends JFrame {

    /**
     * This creates the Java Window and Initializes the initUI() method
     */
    public Asteroids() {
        try {
            initUI();
        } catch (IOException ex) {
            Logger.getLogger(Asteroids.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method creates Table and puts the Game into action
     */
    private void initUI() throws IOException {
        Space space = new Space(this);
        add(space);

        setTitle("Asteroids");
        setSize(Space.BOARD_WIDTH + 20, Space.BOARD_HEIGHT + 40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * This is the main method that invokes the rest of the code
     *
     * @param args
     */
    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        game.setVisible(true);
    }
}
