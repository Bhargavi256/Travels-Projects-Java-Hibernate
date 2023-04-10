package comjourney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.userregistration.Validation;

public class Operations {

	static Scanner s = new Scanner(System.in);
	static String email;
	static String source;
	static String destination;
	static String emailCheck;
	static LocalDate ondate;
	static int passengers;

	public static void runTimeData() {

		Validation.printMsg("Enter Email");
		email = s.next();
		Validation.printMsg("Enter Source");
		source = s.next();
		Validation.printMsg("Enter Destination");
		destination = s.next();
		try{

			Validation.printMsg("Enter Date");
			String string = s.next();
			ondate = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);

		}
		catch(Exception e) {

			Validation.printMsg("Enter correct data");
		}

		Validation.printMsg("Enter No of Passangers");
		passengers = s.nextInt();
	}

	public static boolean checkAvailabilityOfSeats(Statement statement, ResultSet resultSet) {

		String sqlQuery = "select vacancies from journey where source='"+source+"' and destination="+"'"+destination+"' and ondate="+"'"+ondate+"'";

		try {

			resultSet = statement.executeQuery(sqlQuery);
			resultSet.next();
			int res = resultSet.getInt(1);
			if(res>=passengers)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void bookJourney(Statement statement) {

		String sqlQuery = "insert into booked values("+"'"+email+"',"+"'"+source+"',"+"'"+destination+"',"+"'"+ondate+"',"+passengers+")";
		int noOfRows;

		try {

			noOfRows = statement.executeUpdate(sqlQuery);
			Validation.printMsg("Booking successful");
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static boolean checkAvailabilityOfBuses(Statement statement, ResultSet resultSet) {

		String sqlQuery = "select count(*) from journey where source='"+source+"' and destination='"+destination+"' and ondate='"+ondate+"'";

		try {

			resultSet = statement.executeQuery(sqlQuery);
			resultSet.next();
			int res = resultSet.getInt(1);
			if(res>0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean checkBooking(Statement statement, ResultSet resultSet) {

		Validation.printMsg("Enter email");
		emailCheck = s.next();

		String sqlQuery = "select count(*) from booked where email='"+emailCheck+"'";
		try {

			resultSet = statement.executeQuery(sqlQuery);
			resultSet.next();
			int res = resultSet.getInt(1);
			if(res>0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void showBookingConfirmation(Statement statement, ResultSet resultSet) {

		String sqlQuery = "select source,destination,ondate from booked where email='"+emailCheck+"'";
		Validation.printMsg("Congratulations your booking is confirmed");

		try {

			resultSet = statement.executeQuery(sqlQuery);

			while (resultSet.next()) {
				System.out.println(
						resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void editDate(Statement statement, ResultSet resultSet) {
		LocalDate changedate;
		try{

			Validation.printMsg("Enter Date");
			String string = s.next();
			changedate = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
			String sqlQuery = "update booked set ondate='"+changedate+"' where email=" +"'" +emailCheck+"'";

			int noOfRows = statement.executeUpdate(sqlQuery);
            Validation.printMsg("Date has been changed, Happy Journey");            
		}
		catch(DateTimeParseException e) {

			Validation.printMsg("Enter correct data");
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
