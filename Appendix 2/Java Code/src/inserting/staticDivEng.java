package inserting;
import java.util.HashMap;


/**
 * Code for reading biometric data (derived from the BodyMedia SenseWear Pro 2 armband) to sqlite database.
 * In this process missing readings are filled in + data is removed from the beginning of each session.
 * 
 * REQUIREMENTS:
 * 1: you need to set the 1 root path in this class, as highlighted below. This path is to the folder that contains the CSV file with biometric data.
 * 2: this code assumes that a database exists (you can set the name and path to the database in WriteToDBNEW.java)
 */
public class staticDivEng {

	public staticDivEng(String root, String dbPath) {
		bioRootPath = root;
		allData = new HashMap<String, AllDataNEW>();

		//(1) Get the paths to the individual .csv files
		FileReading fr = new FileReading();
		String[] filenames = fr.getPaths(bioRootPath);

		//(2) Read the biometric data from all .csv files and put it in a HashMap called allData
		for(int i=0; i<filenames.length; i++) {
			//we are dealing with 1 file (at location filenames[i]) containing paths in the following code:
			if (!filenames[i].endsWith("csv"))
				continue;
			System.out.println(filenames[i]);
			ExtractBiometricNEW eb = new ExtractBiometricNEW(filenames[i]);
			eb.getbiometric();   //this function will update the bioData hashmap with all of the subjects details.
		}

		System.out.println("allData size:"+allData.size());

		//(3) Remove the biometric readings from beginning of each time the Biometric device was worn from the allData HashMap
		//For testing purposes (with small amounts of data) the below 2 lines can be commented out as it speeds up the process.
		//	removeBeginSessionsNEW rbs = new removeBeginSessionsNEW();
		//	rbs.remove(allData);

		//(4) Write all data to db table - now have copy of data in db table + can use it in whatever ways want.
		System.out.println("Writing all data to DB");
		WriteToDBNEW wdb = new WriteToDBNEW(dbPath);
		wdb.write(allData);
		System.out.println("allData size:"+allData.size());

		//Print successful confirmation
		System.out.println("Successfully reached end of code!");
	}
	/**
	 * The bioRootPath needs to be set to the folder which contains your csv biometric files
	 */
	public static String bioRootPath;

	/**
	 * HashMap for holding all the biometric data before it is written to database (DB)
	 */
	public static HashMap<String, AllDataNEW> allData;

}
