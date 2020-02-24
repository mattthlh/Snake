import javax.swing.*;
import java.awt.*;

public class Main {

    //Runs the game
    public static void main(String args[]){
        //Makes a JFrame and adds and runs all the snake components into this frame
        JFrame frame;

        GamePanel gamePanel = new GamePanel();

        frame = new JFrame("Snake");
        frame.add(gamePanel);
        frame.setPreferredSize(new Dimension(500, 522));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
