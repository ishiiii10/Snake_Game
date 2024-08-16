import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame(){
        this.add(new GamePanel());//adding game panel here 
        this.setTitle("Snake");//setting the title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close the app 
        this.setResizable(false);//doesn't allow the frame to be resized
        this.pack();//pack a;ll the components in the frame
        this.setVisible(true);//enable visibility
        this.setLocationRelativeTo(null);
    }

}
