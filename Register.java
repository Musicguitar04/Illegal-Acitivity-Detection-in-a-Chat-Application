package nhcechat;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username_register;
	private JTextField password_register;
	private JTextField phone_register;
	public static String username;
	public static String password;
	public static String phonenumber;
	private JButton resgister_button;
	private JButton back_button;
	public static String query = " insert into userdetails (uid, username, password,pno)" + "VALUES(?,?,?,?)";
	public static Connection con;
	public static Statement stmt;
	public static int uidc=1;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username_register = new JTextField();
		username_register.setBounds(156, 72, 96, 20);
		contentPane.add(username_register);
		username_register.setColumns(10);
		
		password_register = new JTextField();
		password_register.setBounds(156, 124, 96, 20);
		contentPane.add(password_register);
		password_register.setColumns(10);
		
		phone_register = new JTextField();
		phone_register.setBounds(156, 179, 96, 20);
		contentPane.add(phone_register);
		phone_register.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(56, 75, 64, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lbNewLabel_1 = new JLabel("Password");
		lbNewLabel_1.setBounds(56, 127, 64, 14);
		contentPane.add(lbNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Phone no.");
		lblNewLabel_2.setBounds(56, 182, 64, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Please enter your details.");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblNewLabel_3.setBounds(56, 11, 323, 14);
		contentPane.add(lblNewLabel_3);
		
		resgister_button = new JButton("Register");
		resgister_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				establishsqlconnection();
				get();
				disconnectsqlconnection();
				
			}
			
		});
		resgister_button.setBounds(163, 232, 89, 23);
		contentPane.add(resgister_button);
		
		back_button = new JButton("Back");
		back_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FrontPage frame = new FrontPage();
				frame.setVisible(true);
			}
		});
		back_button.setBounds(10, 232, 89, 23);
		contentPane.add(back_button);
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
		username = username_register.getText();
		password= password_register.getText();
		phonenumber = phone_register.getText();
		
		System.out.println(username+" "+password+" "+phonenumber);
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, uidc);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, phonenumber);
			preparedStatement.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
