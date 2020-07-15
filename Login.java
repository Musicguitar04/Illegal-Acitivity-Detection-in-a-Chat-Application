package nhcechat;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username_entry;
	private JTextField password_entry;
	public static  String loginusername="Sav";
	public static  String loginpassword="";
	public static Connection con;
	public static Statement stmt;
	public static String query = " select username,password from userdetails";
	public static boolean check=false;
	public static final int k=10;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Enter Login details please");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD, 13));
		title.setBounds(115, 11, 198, 14);
		contentPane.add(title);
		
		username_entry = new JTextField();
		username_entry.setBounds(169, 101, 96, 20);
		contentPane.add(username_entry);
		username_entry.setColumns(10);
		
		password_entry = new JTextField();
		password_entry.setBounds(169, 166, 96, 20);
		contentPane.add(password_entry);
		password_entry.setColumns(10);
		
		JLabel username_field = new JLabel("Username");
		username_field.setFont(new Font("Times New Roman", Font.BOLD, 11));
		username_field.setBounds(88, 104, 71, 14);
		contentPane.add(username_field);
		
		JLabel password_field = new JLabel("Password");
		password_field.setFont(new Font("Times New Roman", Font.BOLD, 11));
		password_field.setBounds(88, 169, 47, 14);
		contentPane.add(password_field);
		
		JButton login_button = new JButton("Login");
		login_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				establishsqlconnection();
				get();
				disconnectsqlconnection();
				
				if (check)
				{
				Client frame = new Client();
				frame.setVisible(true);
				
					
				}
				else
					System.out.println("Wrong input");
				
				
			}
		});
		login_button.setBounds(169, 232, 89, 23);
		contentPane.add(login_button);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FrontPage frame = new FrontPage();
				frame.setVisible(true);
			}
		});
		backButton.setBounds(10, 232, 89, 23);
		contentPane.add(backButton);
	}
	
	public static void establishsqlconnection()
	{
	try{  
			
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/savion","root","root@0412");   
			stmt=con.createStatement();  
		}
		catch(Exception e){
			
		
	}
	}
	public void disconnectsqlconnection() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void get()
	{
		loginusername=username_entry.getText();
		loginpassword=password_entry.getText();
		String usecheck = null,passcheck = null;
	//	System.out.println(loginusername +" "+ loginpassword);
		
		try {
			ResultSet rs = stmt.executeQuery("select username,password from userdetails");
			while(rs.next())  
			{
			usecheck=rs.getString(1);
			passcheck=rs.getString(2);
	//		System.out.println(rs.getString(1)+"  "+rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	//	System.out.println(usecheck+" "+passcheck);
		if(loginusername.equals(usecheck) && loginpassword.equals(passcheck))
		{
			check=true;
		}
		
	}

}
