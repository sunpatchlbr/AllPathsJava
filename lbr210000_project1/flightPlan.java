import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class flightPlan
{
	public static void main(String[] args) {
		//System.out.println("Hello World");
		FlightSystem f = new FlightSystem(args[0], args[1], args[2]);
	}
}