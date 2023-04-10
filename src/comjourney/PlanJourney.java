package comjourney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.userregistration.CredOperations;
import com.userregistration.UserCheck;
import com.userregistration.Validation;

public class PlanJourney {
	
	public static void booktravel(Statement statement,ResultSet resultSet) {

		Operations.runTimeData();
		if(Operations.checkAvailabilityOfBuses(statement,resultSet)) {
			
			if(Operations.checkAvailabilityOfSeats(statement,resultSet)) {
				
				  Operations.bookJourney(statement);
			}
			else {
				  Validation.printMsg("Seats are not Available");
			}
		}
		else{
			   Validation.printMsg("Buses are not available for the given source and destination");
		}

	}
	public static void confirmation(Statement statement, ResultSet resultSet) {

		if(Operations.checkBooking(statement,resultSet)) {
			Operations.showBookingConfirmation(statement,resultSet);
		}
		else {
			Validation.printMsg("Please book the bus");
		}
	}
	public static void editTravelDate(Statement statement, ResultSet resultSet) {
		
		if(Operations.checkBooking(statement,resultSet)) {
			Operations.editDate(statement,resultSet);
		}
		else {
			Validation.printMsg("Please book the bus");
		}
	}
	

}


