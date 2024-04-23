package edu.upenn.cit594.studenttests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BasicTests {

	public static boolean triedToExit = false;

	/*
	 * Student code should exit by returning from main(), not by calling System.exit
	 */
	@Before
	public void blockExit() {
		System.setSecurityManager(new SecurityManager() {
			public void checkExit(int status) {
				SecurityException se = new SecurityException("Student code called System.exit");
				// se.printStackTrace();
				throw se;
			}

			public void checkPermission(java.security.Permission perm) {
			}
		});
	}

	@After
	public void resetExit() {
		System.setSecurityManager(null);
	}

	/*
	 * Note no safety is provided. This routine is expected to fail with any error
	 * or exception in the student code.
	 */
	public String runMain(String[] args, String input) throws Exception {
		PrintStream realout = System.out;
		InputStream realin = System.in;

		/* Redirect stdin and stdout */
		ByteArrayOutputStream test_output = new ByteArrayOutputStream();
		ByteArrayInputStream test_input = new ByteArrayInputStream(input.getBytes());
		System.setOut(new PrintStream(test_output));
		System.setIn(test_input);

		/* run the student main method */
		edu.upenn.cit594.Main.main(args);

		/* Restore the actual input/output */
		System.setOut(realout);
		System.setIn(realin);

		return test_output.toString();
	}

	public List<String> extractResults(String output) throws Exception {
		BufferedReader output_reader = new BufferedReader(new StringReader(output));
		List<String> items = new ArrayList<>();

		int state = 0;
		String line;
		while ((line = output_reader.readLine()) != null) {
			if (state == 0 || state == 2) {
				if (line.equals("BEGIN OUTPUT")) {
					state = 1;
				}
			} else if (state == 1) {
				if (line.equals("END OUTPUT")) {
					state = 2;
				} else
					items.add(line);
			}
		}
		if (state != 2) {
			System.err.println("No OUTPUT blocks detected");
			return null;
		}
		return items;
	}

	/* Application must be able to run basic operations in under 2 minutes */
	@Test(timeout = 120000)
	public void testSpeed() throws Exception {
		String results = runMain(new String[] { "--log=speed_test.log", "--covid=covid_data.json",
				"--properties=properties.csv", "--population=population.csv" }, "2\n0\n");
		// System.out.println("raw output:\n" + results +"end of raw output\n");
		List<String> lResults = extractResults(results);
		assertFalse("No assessable output detected", lResults == null);
		assertTrue("Expected exactly one line of output", lResults.size() == 1);
		assertTrue("Out does not match format for operation 1", lResults.get(0).matches("^\\d+$"));
	}

	public List<List<String>> extractResultsMulti(String output) throws Exception {
		BufferedReader output_reader = new BufferedReader(new StringReader(output));
		List<List<String>> listOfItems = new ArrayList<>();
		List<String> items = new ArrayList<>();

		int state = 0;
		String line;
		while ((line = output_reader.readLine()) != null) {
			if (state == 0 || state == 2) {
				if (line.equals("BEGIN OUTPUT"))
					state = 1;
			} else if (state == 1) {
				if (line.equals("END OUTPUT")) {
					state = 2;
					listOfItems.add(items);
					items = new ArrayList<>();
				} else
					items.add(line);
			}
		}
		if (state != 2) {
			System.err.println("No OUTPUT blocks detected");
			return null;
		}
		return listOfItems;
	}

	/* check for errors when running multiple times */
	@Test(timeout = 20000)
	public void testTwice() throws Exception {
		String result1 = runMain(
				new String[] { "--covid=covid_data.json", "--population=population.csv", "--log=small_test1.log" },
				"3\nfull\n2021-11-05\n0\n");
		String result2 = runMain(new String[] { "--covid=covid_data.csv", "--properties=downsampled_properties.csv",
				"--population=population.csv" }, "3\nfull\n2021-11-05\n0\n");

		Set<String> sResult1 = new HashSet<>(extractResults(result1));
		Set<String> sResult2 = new HashSet<>(extractResults(result2));

		assertTrue("Repeated execution failed", sResult1.equals(sResult2));

		/*
		 * This only checks the rough line formatting, not the exact format and not the
		 * values be sure to write more tests of your own
		 */
		for (String line : sResult1) {
			assertTrue("bad line " + line, line.matches("^\\d+ (0|[\\d\\.]+)$"));
		}
	}

	/* This one invokes main 7 times and might take a while. */
	@Test(timeout = 600000)
	public void testActivities() throws Exception {
		System.gc();
		System.out.println("Current memory used (MiB): " + (Runtime.getRuntime().totalMemory() >> 20));
		String[] args = new String[] { "--log=activities.test.log.txt", "--covid=covid_data.csv",
				"--properties=properties.csv", "--population=population.csv" };
		String[] activities = new String[] { "1", "2", "3\nfull\n2021-05-01", "4\n19149", "5\n19149", "6\n19149" };
		String results = runMain(args, Stream.of(activities).collect(Collectors.joining("\n")) + "\n0\n");
		var mResults1 = extractResultsMulti(results);
		List<List<String>> mResults2 = new ArrayList<>();
		for (String act : activities) {
			mResults2.add(extractResults(runMain(args, act + "\n0\n")));
		}
		assertTrue("Output differed", mResults1.equals(mResults2));
		System.out.println("Current memory used (MiB): " + (Runtime.getRuntime().totalMemory() >> 20));
		System.out.println("Max memory used (MiB): " + (Runtime.getRuntime().maxMemory() >> 20));
	}
}
