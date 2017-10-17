package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class runs all the tests.
 * @author Paolo D'Arienzo
 * @version 1.5
 *
 */
public class TestRunner {
	
	/**
	 * Runs all tests; if an error occurs, prints the error report.
	 * @param args parameter passed
	 */
	public static void main(String[] args) {
		
	      Result result = JUnitCore.runClasses(AllTests.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	   }
	}