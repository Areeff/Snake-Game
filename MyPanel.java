import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon snaketitle =new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon right=new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon snakeImage=new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon left=new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon up=new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down=new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon food=new ImageIcon(getClass().getResource("enemy.png"));
    boolean isUp=false;
    boolean isDown=false;
    boolean isRight=true;
    boolean isLeft=false;
    int xpos []= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int ypos []={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    Random random=new Random();
    int foodx=150;
    int foody=150;
    int score=0;
    int snakeX[] = new int [750];
    int snakeY []=new int[750];
    int move=0;
    int lengthOfSnake=3;
    Timer time;
    boolean GameOver=false;
    MyPanel(){
        addKeyListener(this);
        setFocusable(true);
        time=new Timer(150,this);
        time.start();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect( 24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);
        if(move==0){
            snakeX[0]=100;
            snakeX[1]=75;
            snakeX[2]=50;
            snakeY[0]=100;
            snakeY[1]=100;
            snakeY[2]=100;
        }
        if(isRight)
            right.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isLeft)
            left.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isUp)
            up.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isDown)
            down.paintIcon(this,g,snakeX[0],snakeY[0]);
        for(int i=1;i<lengthOfSnake;i++){
            snakeImage.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        food.paintIcon(this,g,foodx,foody);
        if(GameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
            g.drawString("Press the Space to Restart",330,360);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("ITALIC",Font.PLAIN,15));
        g.drawString("Score : "+score,750,30);
        g.drawString("Length: "+lengthOfSnake,750,50);
        g.dispose();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE&&GameOver){
            restart();
        }
          if(e.getKeyCode()==KeyEvent.VK_RIGHT&&(!isLeft)){
              isUp=false;
              isDown=false;
              isRight=true;
              isLeft=false;
              move++;
          }
        if(e.getKeyCode()==KeyEvent.VK_LEFT&&(!isRight)){
            isUp=false;
            isDown=false;
            isRight=false;
            isLeft=true;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP&&(!isDown)){
            isUp=true;
            isDown=false;
            isRight=false;
            isLeft=false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN&&(!isUp)){
            isUp=false;
            isDown=true;
            isRight=false;
            isLeft=false;
            move++;
        }
    }

    private void restart() {
        GameOver=false;
        score=0;
        move=0;
        lengthOfSnake=3;
        isLeft=false;
        isRight=true;
        isUp=false;
        isDown=false;
        time.start();
        newFood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--){
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }
        if(isLeft)
            snakeX[0]=snakeX[0]-25;
        if(isRight)
            snakeX[0]=snakeX[0]+25;
        if(isUp)
            snakeY[0]=snakeY[0]-25;
        if(isDown)
            snakeY[0]=snakeY[0]+25;
        if(snakeX[0]>850) snakeX[0]=25;
        if(snakeX[0]<25)  snakeX[0]=850;
        if(snakeY[0]>625)  snakeY[0]=75;
        if(snakeY[0]<75)  snakeY[0]=625;
        CollisionWithFood();
        CollisionWithBody();
        repaint();
    }

    private void CollisionWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakeX[i]==snakeX[0]&&snakeY[i]==snakeY[0]){
                time.stop();
                GameOver=true;
            }
        }
    }

    private void CollisionWithFood() {
        if(snakeX[0]==foodx&&snakeY[0]==foody){
            newFood();
            lengthOfSnake++;
            score++;
            snakeX[lengthOfSnake-1]=snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake-1]=snakeY[lengthOfSnake-2];
        }
    }

    private void newFood() {
        foodx=xpos[random.nextInt(xpos.length-1)];
        foody=ypos[random.nextInt(ypos.length-1)];
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakeX[i]==foodx&&snakeY[i]==foody){
                newFood();
            }
        }
    }
}
