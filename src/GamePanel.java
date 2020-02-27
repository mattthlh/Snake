import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener{

    //Variable used in code
    private final int WINDOW_SIZE = 500;
    private final int BOARD_SIZE = 20;
    private final int TILE_SIZE = WINDOW_SIZE / BOARD_SIZE;
    private Random rand;
    private int bodyPartCount;
    private String direction;
    private int ticks;
    private Thread thread;
    private boolean running;
    private Apple apple;
    private BodyPart snakehead;
    private ArrayList<BodyPart> coordinates;

    public GamePanel(){
        setFocusable(true);
        addKeyListener(this);
        init();
    }

    //Initializes everything to starting value;
    public void init(){
        rand = new Random();
        ticks = 0;
        bodyPartCount = 0;
        direction = "right";
        apple = new Apple(rand.nextInt(BOARD_SIZE - 1), rand.nextInt(BOARD_SIZE - 1), TILE_SIZE);
        snakehead = new BodyPart((BOARD_SIZE - 1) / 2, (BOARD_SIZE - 1) / 2, TILE_SIZE);
        coordinates = new ArrayList<>();
        coordinates.add(snakehead);
        running = true;
        thread = new Thread(this);

        thread.start();
    }

    //Updates the position of every entity.
    public void update(){

        //Checks if it is game over
        if(isGameOver()) {
            resetGame();
        }


        coordinates.set(0, new BodyPart(snakehead.getX(), snakehead. getY(), TILE_SIZE));
        coordinates.add(0, snakehead);

        //Checks if the snake ate an apple, and adds a bodypart to the end of it
        if(apple.getX() == snakehead.getX() && apple.getY() == snakehead.getY()) {
            int previousY;
            int previousX;
            if (direction.equals("left")) {
                previousX = snakehead.getX() + 1;
                previousY = snakehead.getY();
            } else if (direction.equals("right")) {
                previousX = snakehead.getX() - 1;
                previousY = snakehead.getY();
            } else if (direction.equals("up")) {
                previousX = snakehead.getX();
                previousY = snakehead.getY() + 1;
            } else {
                previousX = snakehead.getX();
                previousY = snakehead.getY() - 1;
            }

            BodyPart bodyPart = new BodyPart(previousX, previousY, TILE_SIZE);
            coordinates.add(bodyPart);
            bodyPartCount += 1;
        }

        if(bodyPartCount <= coordinates.size()){
            coordinates.remove(coordinates.size() - 1);
        }

        while(doesCollide()) {
            apple.setX(rand.nextInt(BOARD_SIZE - 1));
            apple.setY(rand.nextInt(BOARD_SIZE - 1));
        }

        //Checks if the snake hits the wall
        if(direction.equals("left")) {
            if(snakehead.getX() == 0) {
                resetGame();
            } else {
               snakehead.setX(snakehead.getX() - 1);
            }
        } else if(direction.equals("up")) {
            if(snakehead.getY() == 0) {
                resetGame();
            } else {
                snakehead.setY(snakehead.getY() - 1);
            }
        } else if(direction.equals("right")) {
            if(snakehead.getX() == BOARD_SIZE - 1) {
                resetGame();
            } else {
                snakehead.setX(snakehead.getX() + 1);
            }
        } else if(direction.equals("down")) {
            if(snakehead.getY() == BOARD_SIZE - 1) {
                resetGame();
            } else {
                snakehead.setY(snakehead.getY() + 1);
            }
        }
    }

    //If the snake collides with the apple, then change the apples position
    private boolean doesCollide(){
        for(int i = 0; i < coordinates.size(); i++) {
            BodyPart current = coordinates.get(i);

            if(apple.getX() == current.getX() && apple.getY() == current.getY()) {
                return true;
            }
        }
        return false;
    }

    //Method for checking if game is over
    private boolean isGameOver(){
        for(int i = 1; i < coordinates.size(); i++) {
            BodyPart current = coordinates.get(i);

            if(snakehead.getX() == current.getX() && snakehead.getY() == current.getY()) {
                return true;
            }
        }
        return false;
    }

    //Resets the game if lost
    private void resetGame(){
        snakehead.setX((BOARD_SIZE - 1) / 2);
        snakehead.setY((BOARD_SIZE - 1) / 2);

        apple.setX(rand.nextInt(BOARD_SIZE - 1));
        apple.setY(rand.nextInt(BOARD_SIZE - 1));

        bodyPartCount = 0;

        coordinates.clear();
        coordinates.add(snakehead);
    }

    //Draws the game when it starts
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        for(int i = 0; i < BOARD_SIZE; i++){
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, WINDOW_SIZE);
            g.drawLine(0, i * TILE_SIZE, WINDOW_SIZE, i * TILE_SIZE);
        }

        apple.paint(g);
        for(int i = 0; i < coordinates.size(); i++) {
            coordinates.get(i).paint(g);
        }
    }

    //Keeps the code running and updates and repaints every few 100 milliseconds
    @Override
    public void run(){
        while (running) {
            ticks++;

            if (ticks > 1000000) {
                update();
                ticks = 0;
            }
            repaint();
        }
    }

    //Changes direction of snake when key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(!direction.equals("right"))
                direction = "left";
        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(!direction.equals("down"))
                direction = "up";
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!direction.equals("left"))
                direction = "right";
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(!direction.equals("up"))
                direction = "down";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
