package brickBracker;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/javaproject";
    private static final String user = "root";
    private static final String password = "13579abc";
    private static Connection con;
    private static Statement stmt;
    
    public static void main(String[] args) {
	// write your code here
    	
    	System.out.print("Enter your Nickname: ");
    	Scanner nn = new Scanner(System.in);
		String name = nn.nextLine();
		
    	try{  
			String query1 = "INSERT INTO players(name,score) VALUE ('"+name+"',0)";
			
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
			stmt.executeUpdate(query1);
			nn.close();
		}catch(Exception e){
				System.out.println(e);
				e.printStackTrace();
				return;
			}   
    	
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setLocation(dim.width/2-obj.getSize().width/2, dim.height/2-obj.getSize().height/2);
    }
}

