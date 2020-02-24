import java.awt.*;

//BodyPart includes the head of the snake
public class BodyPart extends Entity {
    public BodyPart(int x, int y, int tileSize){
        super(x, y, tileSize);
    }

    //paints the body part of the snake whenever it eats an apple
    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(getX() * getTileSize(), getY() * getTileSize(), getTileSize(), getTileSize());
    }
}
