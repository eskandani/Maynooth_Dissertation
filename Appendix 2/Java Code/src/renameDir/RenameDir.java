package renameDir;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//Used for random number generator
import java.util.concurrent.ThreadLocalRandom;

/**
 * Script to automatically rename a list of directories.
 * Configure the path to the folder that contains all the directories that you want to be renamed.
 * The names of these directories will be switched randomly between each other.
 * 
 *  IMPORTANT:
 *  Do not store any other directories in the folder from the path that you don't want to be renamed. 
 *  
 *  BEST PRACTICE:
 *  Name the directories that you want renamed as follows:
 *  01, 02, 03, 04, 05, 06, etc.
 */
public class RenameDir {
	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		String dir_path = "C:\\Users\\tomek\\OneDrive\\Documents\\College\\Year 4\\CS440 Final Year Project\\Experiment\\folder_name\\"; //Change the path to your folder that contains all directories to be renamed.
		
		File parent = new File(dir_path);
		File dir = new File("");
		//Looks into dir_path and puts all files AND folders into an array
		File[] files = parent.listFiles();
		//final list of folder names (excluding files!, ONLY FOLDERS!)
		List <String> folder_names = new ArrayList<String>();
		
		List <File> folder_paths = new ArrayList<File>();
		
		//Checks the files array and only takes the names of FOLDERS. It ignores the files.
		for (File f : files){
	        if (f.isDirectory()) {
	            folder_names.add(f.getName());
	            folder_paths.add(f);
	        }
	    }
		
		//Randomly mixes the folders. 
		System.out.println("Mixing started...");
		while(!folder_names.isEmpty()) {
			int n = ThreadLocalRandom.current().nextInt(0, folder_names.size());
			
			String first_folder = dir_path + folder_names.get(0);
			System.out.println("folder at 0: " + first_folder);
			String random_folder = dir_path + folder_names.get(n);
			System.out.println("random folder at " + n + ": " + random_folder);
						
			dir = new File(first_folder);
			dir.renameTo(new File(dir_path+"temp"));
			
			dir = new File(random_folder);
			dir.renameTo(new File(first_folder));
			
			dir = new File(dir_path+"temp");
			dir.renameTo(new File(random_folder));		
			
			/*
			 * There can be 2 ways of randomly switching names:
			 * 1. removing only the first folder that got switched but leave the other one as it might get switched again with another folder.
			 * 2. once two folders have been switched, remove them both.
			 * 
			 * For the 2nd method to work, uncomment the below line.
			 */
			//folder_names.remove(n);
			folder_names.remove(0);
			
		}
		System.out.println("Mixing finished.");
		
		System.out.println("Generating complete info file...");
		//Generate the information file
		generateInfoFile(dir_path, folder_paths);
		System.out.println("Completed info file generated.");
	}
	
	/**
	 * Generates a text file with lines of information. Every line corresponds to a folder that was renamed.
	 * The line contains: biometric extraction technique, value of the peak, whether the peak was upper or lower.
	 * 
	 * @param dir_path path where the file should be created (same as the path to the folder that contains the directories)
	 * @param folder_paths paths to all the folders that were renamed.
	 * @throws IOException 
	 */
	public static void generateInfoFile(String dir_path, List <File> folder_paths) throws IOException {
		List<String> lines = new ArrayList<String>();
		
		for(File f : folder_paths) {
			f = new File(f + "\\info.txt");
			lines.add(new Scanner(new File(f.toString())).useDelimiter("\\Z").next());
		}
		
		List<String[]> linesAr = new ArrayList<String[]>();
		List<String> finalLines = new ArrayList<String>();
		
		for(String line : lines) {
			line = line.replace("\"", "");
			linesAr.add(line.split("\t"));
		}
		
		for(String[] linear : linesAr) {
			finalLines.add(linear[0] + "\t" + linear[3]);
		}
		
		Path file = Paths.get(dir_path+"InfoComplete.txt");
		Files.write(file, finalLines, Charset.forName("UTF-8"));
	}
}
