package inserting;

import java.io.*;
import java.util.*;

import java.text.*;

import com.csvreader.CsvReader;

/**
 * Extracts the biometric data.
 *
 */
public class ExtractBiometricNEW {
	private String pathToFiles;

	/*
	 * ArrayList<String> variables to store the data that will be placed in the database. 
	 */
	private ArrayList<String> dateA = new ArrayList<String>();
	private ArrayList<String> timeA = new ArrayList<String>();
	private ArrayList<String> gsrA = new ArrayList<String>();
	private ArrayList<String> energyLevelA = new ArrayList<String>();
	private ArrayList<String> skintempA = new ArrayList<String>();
	private ArrayList<String> heatfluxA = new ArrayList<String>();

	private ArrayList<String> gsr_div_energy = new ArrayList<String>();
	private ArrayList<String> hf_div_energy = new ArrayList<String>();
	private ArrayList<String> st_div_energy = new ArrayList<String>();
	private ArrayList<String> sd_gsr = new ArrayList<String>();
	private ArrayList<String> sd_hf = new ArrayList<String>();
	private ArrayList<String> sd_st = new ArrayList<String>();
	private ArrayList<String> sd_gsr_div_energy = new ArrayList<String>();
	private ArrayList<String> sd_st_div_energy = new ArrayList<String>();
	private ArrayList<String> sd_hf_div_energy = new ArrayList<String>();

	private ArrayList<String> gsr_div_energy_Info = new ArrayList<String>();
	private ArrayList<String> hf_div_energy_Info = new ArrayList<String>();
	private ArrayList<String> st_div_energy_Info = new ArrayList<String>();
	private ArrayList<String> sd_gsr_Info = new ArrayList<String>();
	private ArrayList<String> sd_hf_Info = new ArrayList<String>();
	private ArrayList<String> sd_st_Info = new ArrayList<String>();
	private ArrayList<String> sd_gsr_div_energy_Info = new ArrayList<String>();
	private ArrayList<String> sd_st_div_energy_Info = new ArrayList<String>();
	private ArrayList<String> sd_hf_div_energy_Info = new ArrayList<String>();

	/**
	 * Constructor
	 * @param pathtofiles
	 */
	public ExtractBiometricNEW(String pathtofiles) {
		pathToFiles = pathtofiles;
	}
	
	/**
	 * gets the biometric data
	 */
	public void getbiometric(){
		try{
			FileReader read = new FileReader(pathToFiles);
			CsvReader reader = new CsvReader(read); 
			try{
				reader.readHeaders();
				while (reader.readRecord()){
					/* 
					 * time = time in unix epoch standard (contains time + date together in 1 long number).
					 * Need to put it in 2 separate variables "time" and "date".
					 * "time" in format "HH:mm:ss"
					 * "date" in format "yyyy-MM-dd"
					 */
					String time_unix_epoch = reader.get("Time");
					long time_long = Long.parseLong(time_unix_epoch); //convert String to Long
					Date whole_date = new Date(time_long); //Initialise date with the Long format
					DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //put the date in a given format
					String whole_date_str = formatter.format(whole_date).toString(); // turn the date into string
					String date = whole_date_str.substring(0,10).replace("/", "-"); //take substring date part of the full date string change "/" in the format to "-" to match the format we want
					String time = whole_date_str.substring(11); //substring time of the full date

					String gsr = reader.get("gsr_average");
					//System.out.println("GSR:"+gsr);
					String energy = reader.get("energy_expenditure_per_minute");
					String skintemp = reader.get("skin_temp_average_original_rate");
					String heatflux = reader.get("heat_flux_average_original_rate");
					if(!gsr.equals("")){
						//System.out.println("adding to allData");
						dateA.add(date);
						timeA.add(time);
						gsrA.add(gsr);
						energyLevelA.add(energy);
						skintempA.add(skintemp);
						heatfluxA.add(heatflux);						
					}
					//System.out.println(date+"_"+time+"-"+gsr+"-"+energy);
				}
				reader.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//now write the results to the allData hashtable, filling in the missing readings:
		String eng1 = energyLevelA.get(0);
		String eng2 = "";
		int counter = 1;
		for(int i = 1; i < energyLevelA.size(); i++){
			while(i<energyLevelA.size() && energyLevelA.get(i).equals("")){
				i++;
			}
			if(i < energyLevelA.size()){
				eng2 = energyLevelA.get(i);
			}
			else{
				eng2 = energyLevelA.get(i-1);
			}

			if(!eng2.equals("")){
				double increment = (Double.parseDouble(eng2) - Double.parseDouble(eng1))/(i-counter+1);
				//System.out.println("i is: "+i+"; counter is: "+counter+"; i-counter+1 is: "+(i-counter+1));
				//System.out.println("eng1: "+eng1);
				double val = Double.parseDouble(eng1);
				for(int j = counter; j<i;j++){
					val = val+increment;
					energyLevelA.remove(j);
					energyLevelA.add(j, Double.toString(val));
					//	System.out.println("incre: "+val);
				}
				//	System.out.println("eng2: "+eng2);
			}
			else{
				System.out.println("in the else");
				for(int j = counter; j<i;j++){
					energyLevelA.remove(j);
					energyLevelA.add(j, eng1);
				}
			}
			eng1 = eng2;
			counter = i;
		}
		//Skin temperature (ST):
		String st1 = skintempA.get(0);
		String st2 = "";
		counter = 1;
		for(int i = 1; i < skintempA.size(); i++){
			while(i<skintempA.size() && skintempA.get(i).equals("")){
				i++;
			}
			if(i<skintempA.size()){
				st2 = skintempA.get(i);
			}
			else{
				st2 = skintempA.get(i-1);
			}
			if(!st2.equals("")){
				double increment = (Double.parseDouble(st2) - Double.parseDouble(st1))/(i-counter+1);
				double val = Double.parseDouble(st1);
				for(int j = counter; j<i;j++){
					val = val+increment;
					skintempA.remove(j);
					skintempA.add(j, Double.toString(val));
				}
			}
			else{
				for(int j = counter; j<i;j++){
					skintempA.remove(j);
					skintempA.add(j, st1);
				}
			}
			st1 = st2;
			counter = i;
		}
		//Heatflux (HF):
		String hf1 = heatfluxA.get(0);
		String hf2 = "";
		counter = 1;
		for(int i = 1; i < heatfluxA.size(); i++){
			while(i<heatfluxA.size() && heatfluxA.get(i).equals("")){
				i++;
			}
			if(i<heatfluxA.size()){
				hf2 = heatfluxA.get(i);
			}
			else{
				hf2 = heatfluxA.get(i-1);
			}
			if(!hf2.equals("")){
				double increment = (Double.parseDouble(hf2) - Double.parseDouble(hf1))/(i-counter+1);
				//System.out.println(i-counter+1);
				double val = Double.parseDouble(hf1);
				for(int j = counter; j<i;j++){
					val = val+increment;
					heatfluxA.remove(j);
					heatfluxA.add(j, Double.toString(val));	}
			}
			else {
				for(int j = counter; j<i;j++){
					heatfluxA.remove(j);
					heatfluxA.add(j, hf1);
				}
			}
			hf1 = hf2;
			counter = i;
		}

		/*
		 * Populating the GSR/E, HF/E and ST/E columns for this project.
		 */
		double energyexpenditure = 0.0, metric = 0.0, result = 0.0;
		for(int i = 0; i < dateA.size(); i++) {
			//Energy expenditure value
			energyexpenditure = Double.parseDouble(energyLevelA.get(i));

			//galvanic skin response value
			metric = Double.parseDouble(gsrA.get(i));
			result = metric / energyexpenditure;
			gsr_div_energy.add(i, Double.toString(result));

			//heatflux value
			metric = Double.parseDouble(heatfluxA.get(i));
			result = metric / energyexpenditure;
			hf_div_energy.add(i, Double.toString(result));

			//skin temperature value
			metric = Double.parseDouble(skintempA.get(i));
			result = metric / energyexpenditure;
			st_div_energy.add(i, Double.toString(result));
		}

		
		/* 
		 * Populating SD(GSR), SD(HF), SD(ST), SD(GSR/E), SD(HF/E) and SD(ST.E) columns for this project.
		 * Sliding window method was implemented.
		 */
		sd_gsr = slidingWindow(gsrA);
		sd_hf = slidingWindow(heatfluxA);
		sd_st = slidingWindow(skintempA);
		sd_gsr_div_energy = slidingWindow(gsr_div_energy);
		sd_st_div_energy = slidingWindow(st_div_energy);
		sd_hf_div_energy = slidingWindow(hf_div_energy);

		/*
		 * Populating the columns that contain information about the previous metrics whether they are a PEAK or not.
		 */
		gsr_div_energy_Info = findPeak(gsr_div_energy);
		hf_div_energy_Info = findPeak(hf_div_energy);
		st_div_energy_Info = findPeak(st_div_energy);
		sd_gsr_Info = findPeak(sd_gsr);
		sd_hf_Info = findPeak(sd_hf);
		sd_st_Info = findPeak(sd_st);
		sd_gsr_div_energy_Info = findPeak(sd_gsr_div_energy);
		sd_st_div_energy_Info = findPeak(sd_st_div_energy);
		sd_hf_div_energy_Info = findPeak(sd_hf_div_energy);

		/*
		 * Removing tail of all the data to get rid of any values that happened when the device was taken off.
		 */
		dateA = removeTail(dateA);
		timeA = removeTail(timeA);
		gsrA = removeTail(gsrA);
		energyLevelA = removeTail(energyLevelA);
		skintempA = removeTail(skintempA);
		heatfluxA = removeTail(heatfluxA);
		gsr_div_energy = removeTail(gsr_div_energy);
		hf_div_energy = removeTail(hf_div_energy);
		st_div_energy = removeTail(st_div_energy);
		sd_gsr = removeTail(sd_gsr);
		sd_hf = removeTail(sd_hf);
		sd_st = removeTail(sd_st);
		sd_gsr_div_energy = removeTail(sd_gsr_div_energy);
		sd_st_div_energy = removeTail(sd_st_div_energy);
		sd_hf_div_energy = removeTail(sd_hf_div_energy);
		gsr_div_energy_Info = removeTail(gsr_div_energy_Info);
		hf_div_energy_Info = removeTail(hf_div_energy_Info);
		st_div_energy_Info = removeTail(st_div_energy_Info);
		sd_gsr_Info = removeTail(sd_gsr_Info);
		sd_hf_Info = removeTail(sd_hf_Info);
		sd_st_Info = removeTail(sd_st_Info);
		sd_gsr_div_energy_Info = removeTail(sd_gsr_div_energy_Info);
		sd_st_div_energy_Info = removeTail(sd_st_div_energy_Info);
		sd_hf_div_energy_Info = removeTail(sd_hf_div_energy_Info);


		/*
		 * Putting all data in a hashmap. 
		 */
		for(int i = 0; i < dateA.size(); i++){
			AllDataNEW ad = new AllDataNEW(dateA.get(i), timeA.get(i), gsrA.get(i), energyLevelA.get(i), skintempA.get(i), "0", heatfluxA.get(i),gsr_div_energy.get(i), hf_div_energy.get(i), st_div_energy.get(i), sd_gsr.get(i), sd_hf.get(i), sd_st.get(i), sd_gsr_div_energy.get(i), sd_st_div_energy.get(i), sd_hf_div_energy.get(i), gsr_div_energy_Info.get(i), hf_div_energy_Info.get(i), st_div_energy_Info.get(i), sd_gsr_Info.get(i), sd_hf_Info.get(i), sd_st_Info.get(i), sd_gsr_div_energy_Info.get(i), sd_st_div_energy_Info.get(i), sd_hf_div_energy_Info.get(i));
			staticDivEng.allData.put(dateA.get(i)+"_"+timeA.get(i), ad);
		}
	}

	/**
	 * Remove the tail of a list. Specifically remove the last 180 entries (3 minutes)
	 * @param original List containing biometric values.
	 * @return The input but without the last 180 entries.
	 */
	public static ArrayList<String> removeTail(ArrayList<String> original){
		int size = original.size();
		ArrayList<String> newList = new ArrayList<String>(original.subList(0, size-180));
		return newList;
	}

	/**
	 * Remove the head of a list. Specifically remove the last 60 entries (1 minute)
	 * @param original Original list containing biometric values.
	 * @return The input but without the first 60 entries.
	 */
	public static ArrayList<String> removeHead(ArrayList<String> original){
		int size = original.size();
		ArrayList<String> newList = new ArrayList<String>(original.subList(60, size-1));
		return newList;
	}

	/**
	 * Sliding window method that based on an input array, outputs an array with values of standard deviations of the previous 60 rows.
	 * The first 60 rows are 0. Values start at 60th row.
	 * @param original List of the values from an biometric extraction method.
	 * @return List of standard deviation values by using Sliding Window method.
	 */
	public static ArrayList<String> slidingWindow(ArrayList<String> original){
		ArrayList<String> target = new ArrayList<String>();
		int size = original.size();
		int slider = 0;
		for(int i = 0; i < size; i++) {
			if(i < 60) {
				target.add(i, "0");
			}
			else {
				String val = Double.toString(standardDev(original.subList(slider, i)));
				target.add(i, val);
				slider++;
			}
		}
		return target;
	}

	/**
	 * Calculates the standard deviation of a list of numbers.
	 * @param inputArray List of values.
	 * @return standard deviation of a list of numbers.
	 */
	public static double standardDev(List<String> inputArray){
		List<Double> numArray = new ArrayList<Double>();
		for(int i = 0; i < inputArray.size(); i++) {
			numArray.add(i, Double.parseDouble(inputArray.get(i)));
		}
		double sum = 0.0, standardDeviation = 0.0;
		for(double num : numArray) {
			sum += num;
		}
		double numArraySize = (double)numArray.size();
		double mean = sum/numArraySize;
		for(double num: numArray) {
			standardDeviation += Math.pow(num - mean, 2);
		}
		return Math.sqrt(standardDeviation/numArraySize);
	}

	/**
	 * Marking the values which are peaks and not peaks. "P" = peak, "F" = not a peak.
	 * @param original array of numbers
	 * @return List that contains information about the values in the input array
	 */
	public static ArrayList<String> findPeak(ArrayList<String> original){
		int size = original.size();
		ArrayList<String> Info = new ArrayList<String>(Collections.nCopies(size, "F"));
		ArrayList<Double> numOriginal = new ArrayList<Double>();

		for(int i = 0; i < size; i++) {
			numOriginal.add(i, Double.parseDouble(original.get(i)));
		}

		for(int i = 1; i < size-1; i++) {

			double i_minus_one = numOriginal.get(i-1);
			double i_ = numOriginal.get(i);
			double i_plus_one = numOriginal.get(i+1);

			//If peak then set the parameters
			if (i_minus_one < i_ && i_ > i_plus_one) {
				Info.set(i, "P");
			}
		}
		return Info;
	}

	/*
	 * CODE BELOW WAS ABANDONED AS NEW APPROACH OF FINDING PEAKS USING SQL QUERY WAS IMPLEMENTED.
	 */
	
	/*
	 * A method to choose random peaks for analysing out of all the peaks found in a graph
	 * @param:
	 * String arrayList with values marked with P/PT (Peak/Peak and Threshold)
	 * Int number indicating how many random peaks do we want to find. (default set to 5)
	 */
	//	public static ArrayList<String> pickRandom(ArrayList<String> original){
	//		// how many random values do we want to pick
	//		int howMany = 5;
	//		//random generator
	//		Random rand = new Random();
	//		//array in which we will store the positions of where the Peak above threshold values are
	//		ArrayList<Integer> pos_AT = new ArrayList<Integer>();
	//		ArrayList<Integer> pos_BT = new ArrayList<Integer>();
	//		//size of the original array
	//		int size = original.size();
	//		
	//		//find where P_AT and P_BT are and save the position
	//		for(int i = 0; i < size; i++) {
	//			if(original.get(i).equals("P_AT")) {
	//				pos_AT.add(i);
	//			}
	//			if(original.get(i).equals("P_BT")) {
	//				pos_BT.add(i);
	//			}
	//		}
	//		
	//		//now get the size of "pos_AT" array to find out how many PT values we found altogether
	//		int size_AT = pos_AT.size();
	//		int size_BT = pos_BT.size();
	//		//pick howMany of all the values we found to be chosen.
	//		for(int i = 0; i < howMany; i++) {
	//			//generate a number between 0 and the size of sizeInfo
	//			int  n = rand.nextInt(size_AT);
	//			//so the position is stored at index n 
	//			int randPos = pos_AT.get(n);
	//			original.set(randPos, original.get(randPos) + "R");
	//			
	//		}
	//		
	//		
	//		return original;		
	//	}


	/*
	 * Method 1: to find the peaks inside an ArrayList.
	 * @param ArrayList
	 * Returns an ArrayList with values: true or false.
	 * True if the value is a peak
	 * False if the value is not a peak
	 */
	//	public static ArrayList<String> findPeak(ArrayList<String> original){
	//		ArrayList<String> Info = new ArrayList<String>();
	//		
	//		for(int i = 0; i < original.size(); i++) {
	//			Info.add("FALSE");
	//		}
	//				
	//		for(int i = 1; i < original.size()-1; i++) {
	//			
	//			double i_minus_one = Double.parseDouble(original.get(i-1));
	//			double i_ = Double.parseDouble(original.get(i));
	//			double i_plus_one = Double.parseDouble(original.get(i+1));
	//			
	//			if (i_minus_one <= i_ && i_ >= i_plus_one && i_ != i_plus_one) {
	//				Info.set(i, "TRUE");
	//				i += 119;
	//			}
	//		}
	//		
	//		return Info;
	//	}

	/*
	 * METHOD 2: This method is new, it only finds peaks within each 30 minute time interval. 
	 * This is because there are so many peaks that are part of a bigger peak that the above method
	 * wouldn't be suitable for this project.
	 * So I decided I will only look at the highest peak of every hour and analyse it.
	 */
	//	public static ArrayList<String> findPeak(ArrayList<String> original){
	//		ArrayList<String> Info = new ArrayList<String>();
	//
	//		for(int i = 0; i < original.size(); i++) {
	//			Info.add("FALSE");
	//		}
	//				
	//		double max_val = 0.0;
	//		int max_val_pos = 0;
	//		
	//		for(int i = 0; i < original.size(); i++) {
	//			double curr = Double.parseDouble(original.get(i));
	//			
	//			if(curr > max_val) {
	//				max_val = curr;
	//				max_val_pos = i;
	//			}
	//			
	//			if((i % 1800 == 0 || i == original.size() - 1) && max_val != 0.0 && i != 0) {
	//				Info.set(max_val_pos, "TRUE");
	//				max_val = 0.0;
	//			}
	//		}
	//		
	//		
	//		return Info;
	//	}

}
