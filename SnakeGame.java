import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame frame;
    SnakeGame(){
        frame=new JFrame("Snake Game");
        frame.setBounds(10,10,912,710);
        MyPanel panel=new MyPanel();
        panel.setBackground(Color.gray);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SnakeGame sg=new SnakeGame();
    }
}
