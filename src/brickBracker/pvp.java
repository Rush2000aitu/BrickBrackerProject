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

public class pvp extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	ThreadLocalRandom random = ThreadLocalRandom.current();
    private Timer timer;
    private int delay = 8;
    
    private int playerX =310;
    private int playerY =310;

    private int ballposX = random.nextInt(50,650);
    private int ballposY = random.nextInt(250,500);
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private static final String url = "jdbc:mysql://localhost:3306/javaproject?useSSL=false";
    private static final String user = "root";
    private static final String password = "13579abc";
    private static Connection con;
    private static Statement stmt;

    public pvp() {
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

        //border
        g.setColor((Color.yellow));
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);
        g.setColor(Color.green);
        g.fillRect(playerY,30,100,8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if (ballposY > 570) {
        	
            play = false;            
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Player 2 wins! ",190,300);

            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart",230,350);
            
        	try{  
    			String query1 = "UPDATE pvp SET winner = 'Player2' ORDER BY id DESC LIMIT 1";
    			
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
        
        if(ballposY < 0) {
        	
        	play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Player 1 wins! ",190,300);

            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart",230,350);
            
        	try{  
    			String query1 = "UPDATE pvp SET winner = 'Player1' ORDER BY id DESC LIMIT 1";
    			
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
		// TODO Auto-generated method stub
        timer.start();
        
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects((new Rectangle(playerX,550,100,8)))) {
                ballYdir = -ballYdir;
            }
            if(new Rectangle(ballposX,ballposY,20,20).intersects((new Rectangle(playerY,30,100,8)))) {
                ballYdir = -ballYdir;
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
	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
        if (keyEvent.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        
        if (keyEvent.getKeyCode()== KeyEvent.VK_D){
            if(playerY >= 600){
                playerY = 600;
            }else{
                moveRight1();
            }
        }
        
        if (keyEvent.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        
        if (keyEvent.getKeyCode()== KeyEvent.VK_A){
            if(playerY < 10){
                playerY = 10;
            }else{
                moveLeft1();
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
                playerY = 310;
                	
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
    public void moveRight1(){
        play = true;
        playerY += 20;
    }
    public void moveLeft1(){
        play = true;
        playerY -= 20;
    }

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}

