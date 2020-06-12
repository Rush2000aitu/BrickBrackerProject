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
//     			Connection conn = Database.getDatabase();
// 		try{
// 			String query = "Select * from pvp";
// 			Statement statement = conn.createStatement();
// 			ResultSet resultSet = statement.executeQuery(query);

// 			while(resultSet.next()){
// 				System.out.println(resultSet.getString(1));
// 			}
// 		}catch(Exception e){
// 			System.out.println("Some error appeared during connection.");
// 		}
	    	    
	    
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
    	
    	
    	
//    	System.out.print("Player 1 enter your Nickname: ");
//    	Scanner nn = new Scanner(System.in);
//    	String name1 = nn.nextLine();
//    	System.out.print("Player 2 enter your Nickname: ");
//    	String name2 = nn.nextLine();
//    	
//    	try{  
//			String query1 = "INSERT INTO pvp(player1,player2,winner) VALUE ('"+name1+"','"+name2+"','0')";
//			
//			con = DriverManager.getConnection(url, user, password);
//			stmt = con.createStatement();
//			stmt.executeUpdate(query1);
//			nn.close();
//		}catch(Exception e){
//				System.out.println(e);
//				e.printStackTrace();
//				return;
//			}   
//    	
//    	JFrame obj = new JFrame();
//    	pvp obj1 = new pvp();
//    	obj.setBounds(10,10,700,600);
//    	obj.setTitle("Breakout Ball");
//    	obj.setResizable(false);
//    	obj.setVisible(true);
//    	obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	obj.add(obj1);
//    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//    	obj.setLocation(dim.width/2-obj.getSize().width/2, dim.height/2-obj.getSize().height/2);

    	

    }
}
