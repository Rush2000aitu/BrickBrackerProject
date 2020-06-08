package brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    private Timer timer;
    private int delay = 8;

    private int playerX =310;

    private int ballposX = random.nextInt(50,650);
    private int ballposY = random.nextInt(250,500);
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    
    private static final String url = "jdbc:mysql://localhost:3306/javaproject?useSSL=false";
    private static final String user = "root";
    private static final String password = "13579abc";
    private static Connection con;
    private static Statement stmt;

    
    public Gameplay() {
		
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        //backgr
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //map drawing
        map.draw((Graphics2D)g);

        //border
        g.setColor((Color.yellow));
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <=0){
        	
        	
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You won ",260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
           
        }

        if (ballposY > 570) {
        	
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: ",190,300);

            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart",230,350);

        }
        if(!play) {
        	try{  
    			String query1 = "UPDATE players SET score = "+score+" ORDER BY id DESC LIMIT 1";
    			
    			con = DriverManager.getConnection(url, user, password);
    			stmt = con.createStatement();
    			stmt.executeUpdate(query1);
    			if(con!=null) {
    				  con.close();
    				}
    		}catch(Exception e){
    				System.out.println(e);
    				e.printStackTrace();
    				return;
    			}
        }
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects((new Rectangle(playerX,550,100,8)))) {
                ballYdir = -ballYdir;
            }

            for(int i = 0;i < map.map.length;i++){
                for(int j = 0;j<map.map[0].length;j++){
                    if(map.map[i][j] > 0){
                        int brickX = j* map.brickWidth + 80;
                        int brickY = i *map.brickHeight +50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickWidth;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;

                            if(ballposX + 19 <= brickRect.x || ballposX+1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                        }



                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }if(ballposY < 0){
                ballYdir = -ballYdir;
            }if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if (keyEvent.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = random.nextInt(50,650);
                ballposY = random.nextInt(250,500);
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
