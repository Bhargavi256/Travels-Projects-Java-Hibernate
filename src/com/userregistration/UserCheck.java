package com.userregistration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//import com.User;
//import com.Validation;

public class UserCheck {
	
	static Scanner s = new Scanner(System.in);
	public static void userRegistration(Statement statement,ResultSet resultSet) throws SQLException {


		Validation.printMsg("Enter Email");
		String email = s.next();
		boolean allow = Validation.isValid(email);

		if(allow) {

			if(CredOperations.isPresent(email, statement, resultSet)) {
				Validation.printMsg(email+" is already registered");
			}
			else {
				CredOperations.insertRow(statement,email);
			}
		}
	}

	public static void userLogin(Statement statement,ResultSet resultSet) {
		Validation.printMsg("Enter Email");
		String email = s.next();

		if(CredOperations.isPresent(email, statement, resultSet)) {

			if(CredOperations.checkLocked(email,statement)) {

				Validation.printMsg("Enter Password");
				String password = s.next();

				try {

					if(CredOperations.isLoginOk(email,password, statement, resultSet)) {
						Validation.printMsg("login is success");
						
					}
					else {
						System.out.println("Please enter valid credentials");
						if(CredOperations.checkFailed(email, statement, resultSet))
							CredOperations.updateFailed(email, statement);
						else {
							CredOperations.updateLocked(email, statement);
						}
					}
				}  

				catch (Exception e) {
					e.printStackTrace();
				}} 
			else {
				Validation.printMsg("your account is locked");
			}}

		else {
			Validation.printMsg("Enter Valid email address");
		}
	}
}




