package nhcechat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;


public class Client extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField msg_field;
	static JTextArea msg_area = new JTextArea();
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	public static int k=-1;
	
	public static String flagged[] = {"bomb","terrorist","gun"};
	
	public static String query = " insert into flagged (username,date,time,message)" + "VALUES(?,?,?,?)";
	public static Connection con;
	public static Statement stmt;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		try {
			String msgin ="";
			s= new Socket("127.0.0.1",5000);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			while(!msgin.equals("exit"))
			{
				msgin=din.readUTF();
				//dout.writeUTF(msgin);
				msg_area.setText(msg_area.getText()+"\n"+ "Server: " + msgin);	
				//System.out.println("\nServer: "+ msgin);
				

			}
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		msg_area.setEditable(false);
		
	//	JTextArea msg_area = new JTextArea();
		msg_area.setBounds(10, 57, 418, 142);
		contentPane.add(msg_area);
		
		
		JButton send_button = new JButton("Send");
		send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String msg="";
				msg = msg_field.getText();
				try {
					dout.writeUTF(msg);
					msg_area .setText(msg_area.getText()+ "\n"+ Login.loginusername +": " + msg);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				msg_field.setText("");
				
				
				establishsqlconnection();
				for(int p=0;p<flagged.length;p++)
				{
					k = KMPSearch(flagged[p], msg);
				//	System.out.println(flagged[p]+"\n");
					if(k==1)
					{
						PreparedStatement preparedStatement;
						java.util.Date date=new java.util.Date();
						java.sql.Date sqlDate=new java.sql.Date(date.getTime());
						java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
						
						try {
							preparedStatement = con.prepareStatement(query);
							preparedStatement.setString(1, Login.loginusername);
							preparedStatement.setDate(2, sqlDate);
							preparedStatement.setTimestamp(3, sqlTime);
							preparedStatement.setString(4, msg);
							preparedStatement.executeUpdate(); 
						}
						catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("Illegal\n");
						break;
					}
					
				}
				disconnectsqlconnection();
			
			
			
			}
		});
		send_button.setBounds(339, 210, 89, 45);
		contentPane.add(send_button);
		
		msg_field = new JTextField();
		msg_field.setBounds(10, 210, 319, 45);
		contentPane.add(msg_field);
		msg_field.setColumns(10);
		
		JLabel client_username = new JLabel("");
		client_username.setFont(new Font("Times New Roman", Font.BOLD, 14));
		client_username.setBounds(10, 11, 98, 14);
		contentPane.add(client_username);
		client_username.setText(Login.loginusername);
		
		JButton btnNewButton_1 = new JButton("Connect");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				connect c = new connect();
				Thread t1= new Thread(c);  
		        t1.start();  
				
			}
		});
		btnNewButton_1.setBounds(215, 11, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton disconnect = new JButton("Disconnect");
		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Closing Socket\n");
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		disconnect.setBounds(314, 11, 114, 23);
		contentPane.add(disconnect);
		
		
		
	}
	
	
	public static int KMPSearch(String pat, String txt)
	{
		
		int M = pat.length(); 
        int N = txt.length(); 
  
        // create lps[] that will hold the longest 
        // prefix suffix values for pattern 
        int lps[] = new int[M]; 
        int j = 0; // index for pat[] 
  
        // Preprocess the pattern (calculate lps[] 
        // array) 
        computeLPSArray(pat, M, lps); 
  
        int i = 0; // index for txt[] 
        while (i < N) { 
            if (pat.charAt(j) == txt.charAt(i)) { 
                j++; 
                i++; 
            } 
            if (j == M) { 
                System.out.println("Found pattern "
                                   + "at index " + (i - j)); 
                j = lps[j - 1]; 
                return 1;
            } 
  
            // mismatch after j matches 
            else if (i < N && pat.charAt(j) != txt.charAt(i)) { 
                // Do not match lps[0..lps[j-1]] characters, 
                // they will match anyway 
                if (j != 0) 
                    j = lps[j - 1]; 
                else
                    i = i + 1; 
            } 
        }
		return -1; 
	}
	
	public static void computeLPSArray(String pat, int M, int lps[]) 
	{
		// length of the previous longest prefix suffix 
        int len = 0; 
        int i = 1; 
        lps[0] = 0; // lps[0] is always 0 
  
        // the loop calculates lps[i] for i = 1 to M-1 
        while (i < M) { 
            if (pat.charAt(i) == pat.charAt(len)) { 
                len++; 
                lps[i] = len; 
                i++; 
            } 
            else // (pat[i] != pat[len]) 
            { 
                // This is tricky. Consider the example. 
                // AAACAAAA and i = 7. The idea is similar 
                // to search step. 
                if (len != 0) { 
                    len = lps[len - 1]; 
  
                    // Also, note that we do not increment 
                    // i here 
                } 
                else // if (len == 0) 
                { 
                    lps[i] = len; 
                    i++; 
                } 
            } 
        } 
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



}

class connect implements Runnable


{
	
	 @Override  
	    public void run() {  
		 
		 try {
				String msgin ="";
				Client.s= new Socket("127.0.0.1",5000);
				Client.din = new DataInputStream(Client.s.getInputStream());
				Client.dout = new DataOutputStream(Client.s.getOutputStream());
				
				while(!msgin.equals("exit"))
				{
					msgin=Client.din.readUTF();
					//dout.writeUTF(msgin);
					Client.msg_area.setText(Client.msg_area.getText()+"\nServer :" + msgin);	
					//System.out.println("\nServer: "+ msgin);
					System.out.println();

				}
			} catch (Exception ex) {
				// TODO: handle exception
			}
	       
	    }  
}



