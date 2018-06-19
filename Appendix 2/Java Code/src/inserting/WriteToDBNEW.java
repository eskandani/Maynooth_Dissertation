package inserting;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Note: this code writes to sqlite database
 *  
 *  Note: this code assumes that a database exists (you can give the database any name you wish at the beginning of the below code) and it further assumes that this database contains a table called ''AllBio''.
 */
public class WriteToDBNEW {
	/*
	 * Path to the database that you created (the database can be empty, tables and columns will be automatically created)
	 * Must be an SQLite database.
	 */
	private static String connectionUrl="C:/Users/razav/IdeaProjects/NafiseDissertation/data/pll.db"; //This should be changed to the name and location of the DB you want to use

	// Declare the JDBC objects.
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	/**
	 * Constructor
	 */
	public WriteToDBNEW(String url) {
		connectionUrl = url;
		// Declare the JDBC objects.
		con = null;
		stmt = null;
		rs = null;
	}
	
	/**
	 * Code to write all the original data to a db table
	 * @param allData
	 */
	public void write(HashMap <String, AllDataNEW> allData) {
		try {
			// Establish the connection - sqllite server:
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+connectionUrl);  

			//SQLite query to create table:			
			String sql="CREATE TABLE if not exists AllBio(date STRING, time STRING, gsr STRING, skintemp STRING, heatflux STRING, heartrate STRING, energy STRING, gsr_div_energy STRING, hf_div_energy STRING, st_div_energy STRING, sd_gsr STRING, sd_hf STRING, sd_st STRING, sd_gsr_div_energy STRING, sd_st_div_energy STRING, sd_hf_div_energy STRING, gsr_div_energy_Info STRING, hf_div_energy_Info STRING, st_div_energy_Info STRING, sd_gsr_Info STRING, sd_hf_Info STRING, sd_st_Info STRING, sd_gsr_div_energy_Info STRING, sd_st_div_energy_Info STRING, sd_hf_div_energy_Info STRING)";	

			Statement stat=con.createStatement();

			/*
			 * FOR TESTING PURPOSES USE BELOW 2 LINES.
			 * BUT IF NOT TESTING, THEN DELETE THE LINES.
			 */
			//String sql_delete_table = "DROP TABLE if exists AllBio";
			//stat.execute(sql_delete_table);

			stat.execute(sql);

			Collection c = allData.values();     
			//obtain an Iterator for Collection    
			Iterator itr = c.iterator();     

			//Specifying a counter to see if the data is actually adding
			int counter = 0;
			//iterate through HashMap values iterator    
			while(itr.hasNext()) {
				AllDataNEW ad = (AllDataNEW)itr.next();  
				String d = ad.getDate();

				String t = ad.getTime();

				String g = ad.getGsr();
				String s = ad.getSkintemp();
				String h = ad.getHeartrate();
				String e = ad.getEnergyLevel();
				String hf = ad.getHeatflux();

				String gdive = ad.getGsr_div_energy();
				String hfdive = ad.getHf_div_energy();
				String sdive = ad.getSt_div_energy();
				String sdg = ad.getSd_gsr();
				String sdhf = ad.getSd_hf();
				String sds = ad.getSd_st();
				String sdgdive = ad.getSd_gsr_div_energy();
				String sdsdive = ad.getSd_st_div_energy();
				String sdhfdive = ad.getSd_hf_div_energy();

				String gdive_Info = ad.getGsr_div_energy_Info();
				String hfdive_Info = ad.getHf_div_energy_Info();
				String sdive_Info = ad.getSt_div_energy_Info();
				String sdg_Info = ad.getSd_gsr_Info();
				String sdhf_Info = ad.getSd_hf_Info();
				String sds_Info = ad.getSd_st_Info();
				String sdgdive_Info = ad.getSd_gsr_div_energy_Info();
				String sdsdive_Info = ad.getSd_st_div_energy_Info();
				String sdhfdive_Info = ad.getSd_hf_div_energy_Info();

				//Printing the counter and increasing it by 1 every time a row of data has been added.
				System.out.println(counter);
				counter++;

				//SQLite query to insert the data
				String SQL = "INSERT INTO AllBio"+"(date, time, gsr, skintemp, heatflux, heartrate, energy, gsr_div_energy, hf_div_energy, st_div_energy, sd_gsr, sd_hf, sd_st, sd_gsr_div_energy, sd_st_div_energy, sd_hf_div_energy, gsr_div_energy_Info, hf_div_energy_Info, st_div_energy_Info, sd_gsr_Info, sd_hf_Info, sd_st_Info, sd_gsr_div_energy_Info, sd_st_div_energy_Info, sd_hf_div_energy_Info) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement pstmt = con.prepareStatement(SQL); // create a statement

				pstmt.setString(1, d);  // set input parameter 1
				pstmt.setString(2, t);  // set input parameter 1
				pstmt.setString(3, g); // set input parameter 9
				pstmt.setString(4, s); //title set input parameter 2
				pstmt.setString(5, hf);
				pstmt.setString(6, h); //name set input parameter 1
				pstmt.setString(7, e); // set input parameter 3

				pstmt.setString(8, gdive);
				pstmt.setString(9, hfdive);
				pstmt.setString(10, sdive);
				pstmt.setString(11, sdg);
				pstmt.setString(12, sdhf);
				pstmt.setString(13, sds);
				pstmt.setString(14, sdgdive);
				pstmt.setString(15, sdsdive);
				pstmt.setString(16, sdhfdive);

				pstmt.setString(17, gdive_Info);
				pstmt.setString(18, hfdive_Info);
				pstmt.setString(19, sdive_Info);
				pstmt.setString(20, sdg_Info);
				pstmt.setString(21, sdhf_Info);
				pstmt.setString(22, sds_Info);
				pstmt.setString(23, sdgdive_Info);
				pstmt.setString(24, sdsdive_Info);
				pstmt.setString(25, sdhfdive_Info);

				pstmt.executeUpdate(); // execute insert statement
				pstmt.close();
			}
			counter = 0;
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (con != null) try { con.close(); } catch(Exception e) {}
		}
	}

	/**Function to create a Calendar (Date) from a date time string.
	 * @param date
	 * @param time
	 * @return Calendar representation of the date and time
	 */
	public Calendar createCalendar(String date, String time)
	{
		//System.out.println("date--"+date);
		//break down the date and time into parts:
		String[] dateparts = date.split("-");
		String day = dateparts[2];
		String month = dateparts[1];
		String year = dateparts[0];
		int ye = Integer.parseInt(year);
		int mo = Integer.parseInt(month);
		mo--; //cause calendar months run from 0-11, but we running from 1-12!
		int d = Integer.parseInt(day);
		//System.out.println("year: "+ye);
		//System.out.println("month: "+mo);
		//System.out.println("day: "+d);

		String hour = time.substring(0, time.indexOf(':'));
		String min = time.substring(time.indexOf(':')+1, time.lastIndexOf(':'));
		String sec = time.substring(time.lastIndexOf(':')+1);
		int h = Integer.parseInt(hour);
		int mi = Integer.parseInt(min);
		int s = Integer.parseInt(sec);

		//create a Calender (Date) instance of the date and time:
		Calendar c = new GregorianCalendar(ye, mo, d, h, mi, s);

		return c;
	}
}
