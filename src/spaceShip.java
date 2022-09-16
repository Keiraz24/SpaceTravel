import java.awt.*;

public class spaceShip {
    public double xPos;
    public double yPos;
    public double dx;
    public double dy;
    public int width;
    public int height;
    public double health;
    public double gravity;
    public boolean falling;
    public boolean isAlive;
    public boolean Hurt;
    public boolean hasLaser;
    public Rectangle rec;
    public Rectangle healthBar;

    public spaceShip(int pXpos, int pYpos) {
        xPos = pXpos;
        yPos = pYpos;
        dx =1;
        dy =1;
        width = 100;
        height = 100;
        health=100;
        gravity=-0.05;
        falling=false;
        isAlive = true;
        Hurt=false;
        rec=new Rectangle((int)xPos,(int)yPos,width-10,height-30);
        healthBar=new Rectangle((int)xPos+width/5,(int)yPos-5, width, 5);

    }

    public void move(){
        if(falling==true){
            dy=dy-gravity;
        }
        yPos=yPos+dy;
        rec=new Rectangle((int)xPos,(int)yPos,width,height);
    }
}
