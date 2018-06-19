package inserting;
import java.io.*;

/**
 * This class is used for obtaining paths to the contents of the folders.
 */
public class FileReading {
	private String rootPath;
	
	/**
	 * Constructor
	 */
	public FileReading() {
		rootPath = "";
	}

	/**
	 * This method returns the path to all folders/files contained within the specified folder.
	 * @param root the value of the root path. note paths should end with a '\'.
	 * @return a list of the paths to all folders/files contained within the specified folder. Note this method does not append '\' to the end of returned folder paths.
	 */
	public String[] getPaths(String root) {
		rootPath = root;
		File dir = new File(rootPath);

		String[] contents = dir.list();
		if(contents == null) {
			System.out.println("There are no folders/files in: "+rootPath);
		}
		else {
			for( int i = 0; i < contents.length; i++) {
				contents[i] = rootPath+contents[i];
			}
		}
		return contents;
	}
}