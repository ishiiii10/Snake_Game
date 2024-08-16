

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75; //the higher the delay the slower the game

    final int x[] = new int[GAME_UNITS]; // array to store the x coordinates including the snake head
    final int y[] = new int[GAME_UNITS]; // array to store the y coordinates
    int bodyParts = 6; // initial count of body parts of the snake
    int applesEaten; // variable to count the no: of apples eaten
    int appleX; // stores the x coordinate of the apple generated randomly
    int appleY; // stores the y coordinates of the apple
    char direction = 'R'; // initial direction of the snake (right)
    boolean running = false; // shows that the game is not running
    Timer timer; // variable for timer
    Random random;

    public GamePanel() { //Constructor for the Game Panel

        random = new Random(); // randomize
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); 
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame(); // starts the game
    }

    public void startGame(){ //Function to start the game

        newApple(); // creates a new apple
        running = true; // changes the state of the game from not running to running
        timer = new Timer(DELAY,this); // creates a new timer
        timer.start(); // timer starts
    }

    public void paintComponent(Graphics g){ //Function to paint the components of the game , takes Graphics object g as an input

        super.paintComponent(g); // calls the parent paintComponent method
        draw(g);
    }

    public void draw(Graphics g){ //Function to draw 

        if(running == true)
        {
       /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) // loop to create matrix inside the panel
        {
            g.setColor(Color.darkGray);
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT); //draws the horizontal lines SCREEN_WIDTH/UNIT_SIZE times
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE); //draws the vertical lines SCREEN_HEIGHT/UNIT_SIZE times 
            
        }*/
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // Draws an apple 

        for(int i = 0;i<bodyParts;i++)
        {
            if(i == 0)
            {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

            }
            else
            {
                g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
        }
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
    }
    else{
        gameOver(g);
    }
    }

    public void newApple() {

        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //randomize the x coordinate of the apple
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // randomize the y coordinate of the apple

    }

    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        switch(direction){
            case 'U' :
            y[0] = y[0] - UNIT_SIZE;
            break;
            case 'D' :
            y[0] = y[0] + UNIT_SIZE;
            break;
            case 'L' :
            x[0] = x[0] - UNIT_SIZE;
            break;
            case 'R' :
            x[0] = x[0] + UNIT_SIZE;
            break;
        }
    }

    public void checkApples(){
        if((x[0] == appleX && y[0] == appleY))
        {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions(){ 
        //to check if heads collides with body
        for(int i=bodyParts;i>0;i--){
            if(x[0]==x[i] && y[0]==y[i]){
                running = false;

            }
        }
        if(x[0] < 0){
            running=false;
        }
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        if(y[0] < 0){
            running=false;
        }
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(running==false){
            timer.stop();
        }

    
    }

    public void gameOver(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,35));
        FontMetrics metrics1 =getFontMetrics(getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        // text
        g.setColor(Color.pink);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }

    public void actionPerformed(ActionEvent e){
       if(running){
        move();
        checkApples();
        checkCollisions();
       }
       repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT: //changes direction of the snake moving in top/down direction to left 
                if(direction != 'R') //doesnt work if the snake already moving in left/right direction
                {
                    direction = 'L';
                }
                break;

                case KeyEvent.VK_RIGHT: //changes direction of the snake moving in top/down direction to right
                if(direction != 'L') //doesnt work if the snake already moving in left/right direction
                {
                    direction = 'R';
                }
                break;

                case KeyEvent.VK_UP:
                if(direction != 'D')
                {
                    direction = 'U';
                }
                break;

                case KeyEvent.VK_DOWN:
                if(direction != 'U')
                {
                    direction = 'D';
                }
                break;
            }
        }

    }
}


