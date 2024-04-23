/* Submission file checker for cis594 group project. This program will confirm
 * that your submission file is a zip archive and that Main.java is in the
 * expected location.  This is not an exhaustive check of everything that
 * you are expected to include, but should help reduce confusion.
 */
import java.util.List;
import java.util.zip.ZipFile;
import java.util.stream.Collectors;

public class CheckSubmissionZip {

	public static void main(String [] argv) throws Exception {
		String main_location_pattern = "([^/]+/)?src/edu/upenn/cit594/Main.java";
		ZipFile zf = new ZipFile(argv[0]);
		List<String> possible_mains = zf
			.stream()
			.map(ze -> ze.toString())
			.filter(f -> f.matches(main_location_pattern)).
			collect(Collectors.toList());

		if(possible_mains.size() < 1)
			System.out.println("Main.java not found in the expected location.");
		else if(possible_mains.size() > 1)
			System.out.println("Multiple Main.java candidates discovered, please only submit one source tree");
		else {
			System.out.println("zip archive organization seems correct, Main.java found at:\n\t" + possible_mains.get(0));
		}
	}
}
