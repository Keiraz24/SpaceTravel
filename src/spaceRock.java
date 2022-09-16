import java.awt.*;

public class spaceRock {
    public int xPos;
    public int yPos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean hadCollided;
    public boolean isAlive=false;
    public Rectangle rec;

    public spaceRock(int pxPos, int pyPos){
        xPos = pxPos;
        yPos = pyPos;
        dx=4;
        width=50;
        height =30;
        hadCollided=false;
        rec=new Rectangle(xPos,yPos,width-10,height-10);


    }
    public void move(){
        xPos = xPos + dx;
        rec=new Rectangle(xPos,yPos,width,height);


    }
}
