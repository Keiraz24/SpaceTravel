import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//add start screen


public class runGame implements Runnable, KeyListener {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image Start;
    public Image End;
    public Image Spaceship;
    public Image meteor;
    public Image Space;
    public Image Wall;
    public Image Damage;

    private spaceShip player1;
    private spaceRock meteoroid;
    private Barrier rockwall;
    private Barrier2 rockwall2;

    public boolean gameStart=false;
    public boolean gameOver=false;
    public double spawnRate;
    public int counter;
    public int level;
    public int ticks;
    public int bloodTime;

    public Font myfont;
    public SoundFile song;
    public spaceRock[] meteoroids;



    public static void main(String[] args) {
        runGame ex= new runGame();
        new Thread(ex).start();
    }

    public runGame() {

        setUpGraphics();
        Start=Toolkit.getDefaultToolkit().getImage("spacegamestartscreen.png");
        Space=Toolkit.getDefaultToolkit().getImage("Space.jpg");
        Spaceship=Toolkit.getDefaultToolkit().getImage("spaceshiptransparent.png");
        player1 = new spaceShip(700,250);
        Damage=Toolkit.getDefaultToolkit().getImage("spaceshiptransparentRed.png");
        meteor=Toolkit.getDefaultToolkit().getImage("pngaaa.com-849234.png");
        meteoroid = new spaceRock(34,45);
        Wall= Toolkit.getDefaultToolkit().getImage("spacegamebarrier.png");
        rockwall= new Barrier(0,0);
        rockwall2= new Barrier2(0,650);
        End=Toolkit.getDefaultToolkit().getImage("spacegameendscreen.png");

        meteoroids=new spaceRock[50];

       for(int i=0; i<meteoroids.length; i++){
            int randomY = (int) (Math.random() * 700);
            meteoroids[i]= new spaceRock(0, randomY);
           }

       }




    public void run(){
        while (true) {

            moveThings();
            reSpawn();
            crash();
            render();
            pause(20);
        }
    }
    public void moveThings(){
        song= new SoundFile("Arcade Movement 06.wav");
        player1.move();
        player1.rec= new Rectangle((int)player1.xPos,(int)player1.yPos,player1.width-10,player1.height-30);
        if(player1.yPos>800){
            gameOver=true;
        }

        for(int index=0; index<meteoroids.length; index++) {
            meteoroids[index].move();
        }

    }

    public void reSpawn(){
        for (int i = 0; i < meteoroids.length; i++) {
            spawnRate = Math.random();
            if (spawnRate > 0.9996 && meteoroids[i].isAlive == false) {
                int randomY = (int) (Math.random() * 700);
                meteoroids[i] = new spaceRock(0, randomY);
                meteoroids[i].rec = new Rectangle(meteoroids[i].xPos, meteoroids[i].yPos, 30, 20);

                meteoroids[i].isAlive = true;
            }
            if (meteoroids[i].isAlive == true && meteoroids[i].xPos > 1000) {
                meteoroids[i].isAlive = false;

                //add level increase and spawnRate decrease so more meteroids spawn as level increases
            }
            if (gameStart == true) {
                counter++;
                if (counter == 1000) {
                    level++;
                    counter = 0;
                }
            }
        }
    }
    public void pause(int time ){

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    public void crash() {
        if(player1.Hurt==true){
            ticks++;
            if(ticks==25){
                bloodTime++;
                ticks=0;
            }
        }

        for(int i=0; i<meteoroids.length; i++){
            player1.rec= new Rectangle((int)player1.xPos,(int)player1.yPos,player1.width-10,player1.height-30);
            if (player1.rec.intersects(meteoroids[i].rec) && meteoroids[i].isAlive==true) {
                player1.Hurt=true;
                player1.health= player1.health-10;
                //meteoroids[i].hadCollided=true;
                meteoroids[i].isAlive=false;

            }
//
         }
        //System.out.println(player1.health);
        if(player1.health<=0){
            player1.isAlive=false;
            gameOver=true;
        }

        if(player1.rec.intersects(rockwall.rec)){
            player1.Hurt=true;
            player1.health= player1.health-0.1;

        }
        if(player1.rec.intersects(rockwall2.rec)){
            player1.Hurt=true;
            player1.health= player1.health-0.1;

        }

        if(bloodTime==1){
            player1.Hurt=false;
            bloodTime=0;
        }
//        System.out.println(player1.rec);
   }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);


        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);


        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.addKeyListener(this);
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if(gameStart==false && gameOver==false){
            g.drawImage(Start,0,0,1000,700,null);
        }
        else{
            g.drawImage(Space, 0, 0, 1000, 700, null);
            g.drawImage(Wall, 0, 0, 1000, 700, null);
            if (player1.isAlive == true) {
                g.drawImage(Spaceship, (int) player1.xPos, (int) player1.yPos, player1.width, player1.height, null);
                player1.healthBar= new Rectangle((int)player1.xPos+player1.width/5,(int)player1.yPos-5, player1.width, 5);
                g.setColor(Color.white);
                g.drawRect(player1.healthBar.x,player1.healthBar.y, player1.healthBar.width-38, player1.healthBar.height);
                g.setColor(Color.green);
                g.fillRect(player1.healthBar.x,player1.healthBar.y+1,(int)((player1.healthBar.width-38)*(player1.health/100)), player1.healthBar.height);
                g.setFont(myfont);

                g.setColor(new Color(200,140,40));
                g.drawString("Score="+level, 20, 50);
                if(player1.Hurt==true){
                    g.drawImage(Damage, (int) player1.xPos, (int) player1.yPos, player1.width, player1.height, null);
                }
            }
            for (int i = 0; i < meteoroids.length; i++) {
                if (meteoroids[i].isAlive == true) {
                    g.drawImage(meteor, meteoroids[i].xPos, meteoroids[i].yPos, meteoroids[i].width, meteoroids[i].height, null);
                }
                // if(player1.rec.intersects(meteoroids[i].rec)) {
                //Add image of space ship with red overlay layer
                // }
            }
        }
        if(gameOver==true){
            g.drawImage(End,0,0,1000,700,null);
        }
        g.dispose();
        bufferStrategy.show();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //figure out math for this flappy bird
    @Override
    public void keyPressed(KeyEvent e) {
        int key= e.getKeyCode();
        if(key==32){
            gameStart=true;
        }
        if(gameOver==true && key==8){
            player1 = new spaceShip(700,250);
            meteoroid = new spaceRock(34,45);
            for(int i=0; i<meteoroids.length; i++){
                int randomY = (int) (Math.random() * 700);
                meteoroids[i]= new spaceRock(0, randomY);
            }
            gameOver=false;
            gameStart=false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key= e.getKeyCode();
        if(key==87){
            player1.dy= -3;
            player1.falling=true;
            song.play();
        }


    }
}