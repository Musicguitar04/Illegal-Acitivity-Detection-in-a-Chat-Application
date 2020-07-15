package nhcechat;
import java.sql.*; 
public class Main {

	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
try{  
			
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/savion","root","root@0412");   
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select uid,username from userdetails");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
		//	stmt.executeUpdate("INSERT INTO userdetails "  + "VALUES (2,'Savio','1243',48448)");
		//	con.commit();

}
		catch(Exception e){
			
		
	}
	

	}

	
	
	
}
