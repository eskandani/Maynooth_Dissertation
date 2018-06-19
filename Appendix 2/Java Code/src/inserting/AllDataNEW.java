package inserting;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Stores all the data.
 */
public class AllDataNEW{
	/* Columns in the database */
	private String date;
	private String time;
	private String gsr;
	private String energyLevel;
	private String skintemp;
	private String heartrate;
	private String heatflux;

	private String gsr_div_energy;
	private String hf_div_energy;
	private String st_div_energy;
	private String sd_gsr;
	private String sd_hf;
	private String sd_st;
	private String sd_gsr_div_energy;
	private String sd_st_div_energy;
	private String sd_hf_div_energy;

	private String gsr_div_energy_Info;
	private String hf_div_energy_Info;
	private String st_div_energy_Info;
	private String sd_gsr_Info;
	private String sd_hf_Info;
	private String sd_st_Info;
	private String sd_gsr_div_energy_Info;
	private String sd_st_div_energy_Info;
	private String sd_hf_div_energy_Info;


	/**
	 * Constructor.
	 * 
	 * @param d
	 * @param t
	 * @param g
	 * @param e
	 * @param s
	 * @param h
	 * @param hf
	 * @param gdive
	 * @param hfdive
	 * @param sdive
	 * @param sdg
	 * @param sdhf
	 * @param sds
	 * @param sdgdive
	 * @param sdsdive
	 * @param sdhfdive
	 * @param gdive_Info
	 * @param hfdive_Info
	 * @param sdive_Info
	 * @param sdg_Info
	 * @param sdhf_Info
	 * @param sds_Info
	 * @param sdgdive_Info
	 * @param sdsdive_Info
	 * @param sdhfdive_Info
	 */
	public AllDataNEW(String d, String t, String g, String e, String s, String h, String hf, String gdive, String hfdive, String sdive, String sdg, String sdhf, String sds, String sdgdive, String sdsdive, String sdhfdive, String gdive_Info, String hfdive_Info, String sdive_Info, String sdg_Info, String sdhf_Info, String sds_Info, String sdgdive_Info, String sdsdive_Info, String sdhfdive_Info){
		date = d;
		time = t;
		gsr = g;
		energyLevel = e;
		skintemp = s;
		heartrate = h;
		heatflux = hf;

		gsr_div_energy = gdive;
		hf_div_energy = hfdive;
		st_div_energy = sdive;
		sd_gsr = sdg;
		sd_hf = sdhf;
		sd_st = sds;
		sd_gsr_div_energy = sdgdive;
		sd_st_div_energy = sdsdive;
		sd_hf_div_energy = sdhfdive;

		gsr_div_energy_Info = gdive_Info;
		hf_div_energy_Info = hfdive_Info;
		st_div_energy_Info = sdive_Info;
		sd_gsr_Info = sdg_Info;
		sd_hf_Info = sdhf_Info;
		sd_st_Info = sds_Info;
		sd_gsr_div_energy_Info = sdgdive_Info;
		sd_st_div_energy_Info = sdsdive_Info;
		sd_hf_div_energy_Info = sdhfdive_Info;
	}

	/**
	 * 
	 * @param otherObject 
	 * @return -1, 0 or 1
	 */
	public int compareTo(Object otherObject){
		AllDataNEW other = (AllDataNEW) otherObject;

		Calendar cal = createCalendar(getDate(), getTime());
		Calendar cal_other = createCalendar(other.getDate(), other.getTime());

		if(cal.after(cal_other)) return -1;
		if(getDate() == other.getDate() &&  getTime() == other.getTime()) return 0;
		return 1;
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
	
	/* Setters */

	/**
	 * 
	 * @param h the value of galvanic skin response
	 */
	public void setGsr(String h) {gsr = h;}

	/**
	 * 
	 * @param h the value of heatflux
	 */
	public void setHeatflux(String h) {heatflux = h;}
	
	/**
	 * 
	 * @param h the value of skin temperature
	 */
	public void setSkintemp(String h) {skintemp = h;}

	/**
	 * 
	 * @param h the value of energy expenditure
	 */
	public void setEnergyLevel(String h) {energyLevel = h;}

	/**
	 * 
	 * @param h the value of heart rate
	 */
	public void setHeartrate(String h) {heartrate = h;}

	/**
	 * 
	 * @param h the value of GSR/E
	 */
	public void setGsr_div_energy(String h) {gsr_div_energy = h;}

	/**
	 * 
	 * @param h the value of HF/E
	 */
	public void setHf_div_energy(String h) {hf_div_energy = h;}
	
	/**
	 * 
	 * @param h the value of ST/E
	 */
	public void setSt_div_energy(String h) {st_div_energy = h;}
	
	/**
	 * 
	 * @param h the value of SD(GSR)
	 */
	public void setSd_gsr(String h) {sd_gsr = h;}
	
	/**
	 * 
	 * @param h the value of SD(HF)
	 */
	public void setSd_hf(String h) {sd_hf = h;}
	
	/**
	 * 
	 * @param h the value of SD(ST)
	 */
	public void setSd_st(String h) {sd_st = h;}
	
	/**
	 * 
	 * @param h the value of SD(GSR/E)
	 */
	public void setSd_gsr_div_energy(String h) {sd_gsr_div_energy = h;}
	
	/**
	 * 
	 * @param h the value of SD(ST/E)
	 */
	public void setSd_st_div_energy(String h) {sd_st_div_energy = h;}
	
	/**
	 * 
	 * @param h the value of SD(HF/E)
	 */
	public void setSd_hf_div_energy(String h) {sd_hf_div_energy = h;}
	
	/**
	 * 
	 * @param h the value of GSR/E Info
	 */
	public void setGsr_div_energy_Info(String h) {gsr_div_energy_Info = h;}
	
	/**
	 * 
	 * @param h the value of HF/E Info
	 */
	public void setHf_div_energy_Info(String h) {hf_div_energy_Info = h;}
	
	/**
	 * 
	 * @param h the value of ST/E Info
	 */
	public void setSt_div_energy_Info(String h) {st_div_energy_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(GSR) Info
	 */
	public void setSd_gsr_Info(String h) {sd_gsr_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(HF) Info
	 */
	public void setSd_hf_Info(String h) {sd_hf_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(ST) Info
	 */
	public void setSd_st_Info(String h) {sd_st_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(GSR/E) Info
	 */
	public void setSd_gsr_div_energy_Info(String h) {sd_gsr_div_energy_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(ST/E) Info
	 */
	public void setSd_st_div_energy_Info(String h) {sd_st_div_energy_Info = h;}
	
	/**
	 * 
	 * @param h the value of SD(HF/E)
	 */
	public void setSd_hf_div_energy_Info(String h) {sd_hf_div_energy_Info = h;}

	/* Getters */
	
	/**
	 * 
	 * @return the value of date of data
	 */
	public String getDate() {return date;}

	/**
	 * 
	 * @return the value of time of data
	 */
	public String getTime() {return time;}
	
	/**
	 * 
	 * @return the value of heart rate
	 */
	public String getHeartrate() {return heartrate;}
	
	/**
	 * 
	 * @return the value of galvanic skin response
	 */
	public String getGsr() {return gsr;}

	/**
	 * 
	 * @return the value of heatflux
	 */
	public String getHeatflux() {return heatflux;}

	/**
	 * 
	 * @return the value of skin temperature
	 */
	public String getSkintemp() {return skintemp;}

	/**
	 * 
	 * @return the value of energy expenditure
	 */
	public String getEnergyLevel() {return energyLevel;}
	
	/**
	 * 
	 * @return the value of GSR/E
	 */
	public String getGsr_div_energy() {return gsr_div_energy;}
	
	/**
	 * 
	 * @return the value of HF/E
	 */
	public String getHf_div_energy() {return hf_div_energy;}
	
	/**
	 * 
	 * @return the value of ST/E
	 */
	public String getSt_div_energy() {return st_div_energy;}
	
	/**
	 * 
	 * @return the value of SD(GSR)
	 */
	public String getSd_gsr() {return sd_gsr;}
	
	/**
	 * 
	 * @return the value of SD(HF)
	 */
	public String getSd_hf() {return sd_hf;}
	
	/**
	 * 
	 * @return the value of SD(ST)
	 */
	public String getSd_st() {return sd_st;}
	
	/**
	 * 
	 * @return the value of SD(GSR/E)
	 */
	public String getSd_gsr_div_energy() {return sd_gsr_div_energy;}
		
	/**
	 * 
	 * @return the value of SD(HF/E)
	 */
	public String getSd_hf_div_energy() {return sd_hf_div_energy;}

	/**
	 * 
	 * @return the value of SD(ST/E)
	 */
	public String getSd_st_div_energy() {return sd_st_div_energy;}

	/**
	 * 
	 * @return the value of GSR/E Info
	 */
	public String getGsr_div_energy_Info() {return gsr_div_energy_Info;}
	
	/**
	 * 
	 * @return the value of HF/E Info
	 */
	public String getHf_div_energy_Info() {return hf_div_energy_Info;}
	
	/**
	 * 
	 * @return the value of ST/E Info
	 */
	public String getSt_div_energy_Info() {return st_div_energy_Info;}
	
	/**
	 * 
	 * @return the value of SD(GSR) Info
	 */
	public String getSd_gsr_Info() {return sd_gsr_Info;}
	
	/**
	 * 
	 * @return the value of SD(HF) Info
	 */
	public String getSd_hf_Info() {return sd_hf_Info;}
	
	/**
	 * 
	 * @return the value of SD(ST) Info
	 */
	public String getSd_st_Info() {return sd_st_Info;}
	
	/**
	 * 
	 * @return the value of SD(GSR/E) Info
	 */
	public String getSd_gsr_div_energy_Info() {return sd_gsr_div_energy_Info;}
	
	/**
	 * 
	 * @return the value of SD(HF/E) Info
	 */
	public String getSd_hf_div_energy_Info() {return sd_hf_div_energy_Info;}
	
	/**
	 * 
	 * @return the value of SD(ST/E)
	 */
	public String getSd_st_div_energy_Info() {return sd_st_div_energy_Info;}
}
