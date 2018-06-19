package inserting;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Removes the beginning of all biometric data.
 */
public class removeBeginSessionsNEW {
	public static PrintWriter results;

	/**
	 * Constructor
	 */
	removeBeginSessionsNEW() {}

	/**
	 * Removes the start of data from all the columns.
	 * @param all column names and the data for them.
	 */
	public void remove(HashMap <String, AllDataNEW> all) {
		Map allData = new TreeMap(all);
		Iterator itr1 = allData.entrySet().iterator(); 
		String dPrevious = "2000-01-01";//default settings
		String tPrevious = "00:00:00";

		while(itr1.hasNext()) {
			Map.Entry e = (Map.Entry)itr1.next() ; 
			String key = (String)e.getKey();
			AllDataNEW ad = (AllDataNEW)e.getValue();
			String d = ad.getDate();
			String t = ad.getTime();

			//keep record of the previous entry --if more than one sec between the previous and current readin:
			//ignore the first 45 mins of readings.
			//System.out.println("d: "+d+"..dPrev: "+dPrevious);
			//System.out.println("t: "+t+"..tPrev: "+tPrevious);

			if(d.equals(dPrevious)&& t.equals(tPrevious)) { 
				//do nothing because we keeping this record
			}
			else { //we need to skip 45minutes worth of data
				//removing the first element we skipping from the hashmap:
				all.remove(key);

				int counter = 1;
				String d_skipPrevious = d;//default settings
				String t_skipPrevious = t;
				//add one second to the previous time cause we want to check that the new time is only 1 sec greater than the previous time:
				Calendar cal = createCalendar(d_skipPrevious, t_skipPrevious);
				//System.out.println("original: "+cal.toString());
				//System.out.println("original date: "+date);
				cal.add(Calendar.SECOND, 1);
				int hour = cal.get(Calendar.HOUR);
				String min = Integer.toString(cal.get(Calendar.MINUTE));
				if(min.length()<2){
					min = "0"+min;
				}
				String sec = Integer.toString(cal.get(Calendar.SECOND));
				if(sec.length()<2){
					sec = "0"+sec;
				}
				if((cal.get(Calendar.AM_PM)==1)&&(hour !=12)) {
					hour = hour + 12;
					t_skipPrevious = hour+":"+min+":"+sec;
				}
				else {
					String hr = Integer.toString(cal.get(Calendar.HOUR));
					if(hr.length()<2) {
						hr = "0"+hr;
					}
					t_skipPrevious = hr+":"+min+":"+sec;
				}

				String d_skip = "2000-01-01", t_skip = "00:00:00";
				boolean samePeriod = true;
				while(counter<2700 && itr1.hasNext()&&samePeriod) {
					counter++;
					//need to do a check within here that we still staying within the one session too (in case a session is less than 45mins long)
					Map.Entry e1 = (Map.Entry)itr1.next(); 

					String key_skip = (String)e1.getKey ();
					AllDataNEW ad_skip = (AllDataNEW)e1.getValue();

					d_skip = ad_skip.getDate();
					t_skip = ad_skip.getTime();

					/* 
					 * Commenting out the results.print(...). Reason given above
					 */
					 //results.println("---------------------------");
					 //results.println("skip: "+d_skip+" "+t_skip);
					 //results.println("previous: "+d_skipPrevious+" "+t_skipPrevious);

					if(d_skip.equals(d_skipPrevious)&&t_skipPrevious.equals(t_skip)) {
						all.remove(key_skip);
					}
					else {
						all.remove(key_skip);
						samePeriod = false;
						d_skip = "2000-01-01";
						t_skip = "00:00:00";
					}

					d_skipPrevious = d_skip;//default settings
					t_skipPrevious = t_skip;
					//add one second to the previous time cause we want to check that the new time is only 1 sec greater than the previous time:
					cal = createCalendar(d_skipPrevious, t_skipPrevious);
					//System.out.println("original: "+cal.toString());
					//System.out.println("original date: "+date);
					cal.add(Calendar.SECOND, 1);
					hour = cal.get(Calendar.HOUR);

					min = Integer.toString(cal.get(Calendar.MINUTE));
					if(min.length()<2){
						min = "0"+min;
					}
					sec = Integer.toString(cal.get(Calendar.SECOND));
					if(sec.length()<2){
						sec = "0"+sec;
					}
					if((cal.get(Calendar.AM_PM)==1)&&(hour !=12)) {
						hour = hour + 12;
						t_skipPrevious = hour+":"+min+":"+sec;
					}
					else {
						String hr = Integer.toString(cal.get(Calendar.HOUR));
						if(hr.length()<2) {
							hr = "0"+hr;
						}
						t_skipPrevious = hr+":"+min+":"+sec;
					}
				}

				d = d_skip;
				t = t_skip;
			}
			
			dPrevious = d;
			tPrevious = t;
			//add one second to the previous time cause we want to check that the new time is only 1 sec greater than the previous time:
			Calendar cal = createCalendar(dPrevious, tPrevious);
			//System.out.println("original: "+cal.toString());
			//System.out.println("original date: "+date);
			cal.add(Calendar.SECOND, 1);
			int hour = cal.get(Calendar.HOUR);
			String min = Integer.toString(cal.get(Calendar.MINUTE));
			if(min.length()<2){
				min = "0"+min;
			}
			String sec = Integer.toString(cal.get(Calendar.SECOND));
			if(sec.length()<2){
				sec = "0"+sec;
			}
			if((cal.get(Calendar.AM_PM)==1)&&(hour !=12)) {
				hour = hour + 12;
				tPrevious = hour+":"+min+":"+sec;
			}
			else {
				String hr = Integer.toString(cal.get(Calendar.HOUR));
				if(hr.length()<2) {
					hr = "0"+hr;
				}
				tPrevious = hr+":"+min+":"+sec;
			}
		}
	}
	
	/**Function to create a Calendar (Date) from a date time string.
	 * @param date
	 * @param time
	 * @return Calendar representation of the date and time
	 */
	public Calendar createCalendar(String date, String time) {
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

