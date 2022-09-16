import java.awt.*;


public class Barrier {
    public int xPos;
    public int yPos;
    public int width;
    public int height;
    public Rectangle rec;

    public Barrier(int pxPos, int pyPos){
        xPos = pxPos;
        yPos = pyPos;
        width=1000;
        height =10;
        rec= new Rectangle(xPos,yPos, width, height);
    }
}
