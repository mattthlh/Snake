public class Entity {

    private int x;
    private int y;
    private static int tileSize;

    public Entity(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
    }

    //Methods to update the entities
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getTileSize() {
        return tileSize;
    }

}
