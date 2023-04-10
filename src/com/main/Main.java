package com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.userregistration.UserCheck;
import com.userregistration.Validation;

import comjourney.PlanJourney;



public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);	
		try {

			File f = new File(Constants.filePath);
			FileInputStream fis = new FileInputStream(f); 
			int temp;
			while((temp = fis.read())!=-1) {
				System.out.print((char)temp);
			}

			Validation.printMsg("\n\n");
			Connection connection = getDBConnection();
			System.out.println("Connection Estabished");
			Statement statement = getStatement(connection);
			ResultSet resultSet = null;
			int k=0;
			do {

				Validation.printMsg("Choose the option as per your need");
				Validation.printMsg("1.Resgistration\n2.Login\n3.Plan Journey\n4.Booking Confirmation\n5.Edit\n");
				int option = s.nextInt();
				switch(option) {
				case(1):
					UserCheck.userRegistration(statement, resultSet);
				k = continueChoosing(s);
				break;
				case(2):
					UserCheck.userLogin(statement, resultSet);
				k = continueChoosing(s);
				break;
				case(3):
					UserCheck.userLogin(statement, resultSet);
				PlanJourney.booktravel(statement, resultSet);
				k = continueChoosing(s);
				break;
				case(4):
					PlanJourney.confirmation(statement,resultSet);
				k = continueChoosing(s);
				break;
				case(5):
					PlanJourney.editTravelDate(statement,resultSet);
				k = continueChoosing(s);

				}}while(k==1);

		}catch (FileNotFoundException e) {
			Validation.printMsg("File is not present in specified path");

		}catch(IOException e) {
			e.printStackTrace();

		}catch(Exception e) {
			Validation.printMsg("you haven't written catch for this exception");
		}


	}

	private static int continueChoosing(Scanner s) {

		int k;
		k=0;
		System.out.println("enter 1 to continue");
		k = s.nextInt();
		return k;

	}

	private static Connection getDBConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(Constants.DB_URL,Constants.USER,Constants.PASSWORD);
		return connection;
	}

	private static Statement getStatement(Connection con) throws SQLException {
		Statement statement = con.createStatement();
		return statement;
	}
}
