package jdbc;
import java.sql.*;

public class ProfessorDAO_JDBC implements ProfessorDAO {
	
	Connection dbConnection;

	public ProfessorDAO_JDBC(Connection dbconn){
		// JDBC driver name and database URL
 		//  Database credentials
		dbConnection = dbconn;
	}

	@Override
	public Researcher getresearcherByKey(int studentId) {
		Researcher r = new Researcher();
		String sql;
		Statement stmt = null;
		
		try{
			stmt = dbConnection.createStatement();
			sql = "select * from researcher";
			ResultSet rs = stmt.executeQuery(sql);
																																																																																																																																																																																			
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				int rollno  = rs.getInt("studentId");
				if(rollno!=studentId)
					continue;
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				//String loginid = rs.getString("loginid");
				String address = rs.getString("address");
				String sex = rs.getString("sex");
				int dept_num=rs.getInt("dept_num");
				r.setStudentId(studentId);
				r.setFname(fname);
				r.setLname(lname);
				r.setAddress(address);
				r.setSex(sex.charAt(0));
				r.setDeptNo(dept_num);
				// Add exception handling here if more than one row is returned
			}
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		// Add exception handling when there is no matching record
		return r;
	}
	public void updateProjectKey(int studentId,int projectId,String key)
	{
		String sql;
		Statement stmt = null;
		int projectCheck=0;
		try{
			stmt = dbConnection.createStatement();
			sql = "select projNo from works_on where resId="+studentId;
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				int projId = rs.getInt("projNo");
				if(projId==projectId)
				{
					projectCheck = 1;
					break;
				}
			}
		}catch (SQLException ex) {
		    // handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
		}
		if(projectCheck==0)
		{
			System.out.println("You are not working on this project\n");
			return;
		}
		else
		{
		    PreparedStatement preparedStatement=null;
			sql = "insert into projectkey(projectId,keyword) values (?,?)";

			try {
				preparedStatement = dbConnection.prepareStatement(sql);
	 
				preparedStatement.setInt(1, projectId);
				preparedStatement.setString(2, key);
	 
				// execute insert SQL stetement
				preparedStatement.executeUpdate();
	 
				System.out.println("Keyword: " + key + " added to the projectId: "+ projectId);
			} catch (SQLException e) {
	 			System.out.println(e.getMessage());
	 		}

			try{
				if (preparedStatement != null) {
					preparedStatement.close();
					return;
				}
			} catch (SQLException e) {
	 			System.out.println(e.getMessage());
	 		}
		}
		return;
	}
	
	@Override
	
	public void searchProj(String key)
	{
		String sql;
		Statement stmt = null;
		//int projectCheck=0;
		try{
			stmt = dbConnection.createStatement();
			sql = "select distinct projectId from projectkey where keyword='"+key+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs==null){
				System.out.println("No project with such keyword found");
			}
			while(rs.next())
			{
				int id = rs.getInt("projectId");
				//System.out.println(id);
				Statement stmt2=null;
				String sql2;
				try
				{
					stmt2 = dbConnection.createStatement();
					sql2 = "select * from project where projId="+id;
					ResultSet rs2 = stmt2.executeQuery(sql2);
					//ResultSetMetaData rsmd = rs2.getMetaData();
					//int columnsNumber = rsmd.getColumnCount();
					//System.out.println("Length="+columnsNumber+rsmd.getColumnName(1));
					String name="";
					while(rs2.next())
						name = rs2.getString("name");
					System.out.println("Project id: "+id+"\nName: "+name);
				}catch (SQLException ex) 
				{
		    // handle any errors
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}
			}
		}
		catch (SQLException ex) {
		    // handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
			
	public void addResearcher(Researcher r1) {
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		sql = "insert into researcher(studentID,fname, lname, loginid, address,sex, password, dept_num) values (?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 
			preparedStatement.setInt(1, r1.getStudentId());
			preparedStatement.setString(2, r1.getFname());
			preparedStatement.setString(3, r1.getLname());
			preparedStatement.setString(4, r1.getLoginid());
			preparedStatement.setString(5, r1.getAddress());
			preparedStatement.setString(6, String.valueOf(r1.getSex()));
			preparedStatement.setString(7, r1.getPassword());
			preparedStatement.setInt(8, r1.getDeptNo());
			
 
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
 
			System.out.println("Researcher: Student ID " + r1.getStudentId() 
				+ ", added to the database");
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
	}
	public void deleteResearcher(Researcher r1) {
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		sql = "delete from researcher where studentID = "+r1.getStudentId();

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
 
			System.out.println("Researcher: Student ID " + r1.getStudentId() 
				+ ", deleted from database");
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
	}	
	
}
