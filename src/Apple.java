import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Apple extends Entity{

    public Apple(int x, int y, int tileSize){
        super(x, y, tileSize);
    }

    //paints the new location of the apple whenever the snake eats it
    public void paint(Graphics g){
        try {
            //Tries to paint an image of a downloaded apple
            BufferedImage img = ImageIO.read(new File("/Users/matthe/IdeaProjects/Snake Game/src/apple.png"));
            g.drawImage(img, getX() * getTileSize(), getY() * getTileSize(), getTileSize(), getTileSize(), null);
            //If it fails, print the error and then just set the apple to a red square.
        } catch(IOException e) {
            e.printStackTrace();
            g.setColor(Color.RED);
            g.fillRect(getX() * getTileSize(), getY() * getTileSize(), getTileSize(), getTileSize());
        }
    }
}
