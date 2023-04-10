package com.userregistration;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CredOperations {
	static Scanner s = new Scanner(System.in);
	static String firstName;
	static String lastName;
	static int mobileNumber;
	static String gender;
	static String password;
	public static int allowbooking = 0;
	static void insertRow(Statement statement, String email)  {
		runTimeData();
		String sqlQuery = "insert into user values("+"'"+firstName+"',"+"'"+lastName+"',"+mobileNumber+","+"'"+gender+"',"+"'"+email+"',"+"'"+password+"',0,1)";
		int noOfRows;
		try {
			noOfRows = statement.executeUpdate(sqlQuery);
			Validation.printMsg("Registration is successful, Please login for booking");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static boolean isPresent(String email,Statement statement,ResultSet resultSet) {
		String sqlQuery = "select count(*) from user where email='"+email+"'";
		try {
			resultSet = statement.executeQuery(sqlQuery);
			resultSet.next();
			int res = resultSet.getInt(1);
			if(res>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	private static void runTimeData() {

		Validation.printMsg("Enter FirstName");
		firstName = s.next();
		Validation.printMsg("Enter LastName");
		lastName = s.next();
		Validation.printMsg("Enter MobileNumber");
		mobileNumber = s.nextInt();
		Validation.printMsg("Enter Gender");
		gender = s.next();

		Validation.printMsg("Enter Passwod");
		password = s.next();

	}

	public static boolean isLoginOk(String email, String password2, Statement statement, ResultSet resultSet) {

		String sqlQuery = "select count(*) from user where email='"+email+"'and password='"+password2+"'";

		try {
			//System.out.println(sqlQuery);
			resultSet = statement.executeQuery(sqlQuery); 
			resultSet.next();
			int res = resultSet.getInt(1);
			if(res>0) {
				allowbooking = 1;
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;


	}

	public static void updateFailed(String email,Statement statement) {
		String sqlQuery = "update user set failedCount=failedCount+1 where email=" +"'" +email+"'";
		try {
			int noOfRows = statement.executeUpdate(sqlQuery);
			//			System.out.println("updated");
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

	public static boolean checkFailed(String email,Statement statement,ResultSet resultSet) {
		String Query = "select failedCount from user where email='"+email+"'";
		try {
			ResultSet res = statement.executeQuery(Query);
			res.next();
			int result = res.getInt(1);
			if(result<5)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void updateLocked(String email, Statement statement) {
		String sqlQuery = "update user set accountStatus=0 where email=" +"'" +email+"'";
		try {
			int noOfRows = statement.executeUpdate(sqlQuery);
			Validation.printMsg("Account has been locked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkLocked(String email, Statement statement) {
		String Query = "select accountStatus from user where email='"+email+"'";

		try {
			ResultSet res = statement.executeQuery(Query);
			res.next();
			int result = res.getInt(1);
			if(result==1)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
