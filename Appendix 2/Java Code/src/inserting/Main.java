package inserting;

import com.csvreader.CsvReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class is the main class that does the following:
 * 1. Reads the logbook and puts it in the database.
 * 2. Gets the important events from the loggbook table and Allbio table.
 * 3. Finds the photos that matches the important events' times.
 * 4. Write the results to a CSV file.
 */
public class Main {
    public static String IMAGES_PATH = "C:\\Users\\razav\\Documents\\nafise\\Photos";
    public static String CONNECTION_URL = "C:\\Users\\razav\\Documents\\nafise\\\\BiometricData\\pll.db"; //This should be changed to the name and location of the DB you want to use
    public static String LIFELOG_CSV = "C:\\Users\\razav\\Documents\\nafise\\logbook\\Book1.csv";
    public static String USER_CSV = "C:\\Users\\razav\\Documents\\nafise\\user.csv";
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Main(String images, String db, String life, String user) {
        IMAGES_PATH = images;
        CONNECTION_URL = db;
        LIFELOG_CSV = life;
        USER_CSV = user;
        con = null;
        stmt = null;
        rs = null;

    }

    /**
     * This function reads the logbook and puts it in a given database.
     */
    public void writeTheLogBook() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + CONNECTION_URL);
            String sql="CREATE TABLE if not exists logbook(mydate STRING, clock STRING, activity STRING)";
            Statement stat=con.createStatement();
            stat.execute(sql);

            FileReader read = new FileReader(LIFELOG_CSV);
            CsvReader reader = new CsvReader(read);

            reader.readHeaders();
            while (reader.readRecord()) {
                String[] raw = reader.getRawRecord().split(",");
                if(raw.length == 0)
                    break;
                String[] d = raw[0].split("/");
                String date = d[2] + "-" + d[1] + "-" + d[0];
                String time = raw[1];
                String activity = raw[2];

                String SQL = "INSERT INTO logbook"+"(mydate, clock, activity) VALUES(?, ?, ?);";
                PreparedStatement pstmt = con.prepareStatement(SQL); // create a statement
                pstmt.setString(1, date);
                pstmt.setString(2, time);
                pstmt.setString(3, activity);
                pstmt.executeUpdate(); // execute insert statement
                pstmt.close();

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    /**
     * This method finds important events.
     * @return the important events.
     */
    public String[][] getAttributes() {
        String[][] result = new String[45][10];
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + CONNECTION_URL);
            String[] act = {"Walking", "Eating", "Working", "Travelling", "Other"};
            String[] type = {"ASC", "DESC"};
            String[] typePr = {"MIN", "MAX"};
            String[] by = {"AllBio.skintemp", "AllBio.heatflux", "AllBio.gsr"};
            String[] prt = {"ST", "HF", "GSR"};
            int counter = 0;
            for (int i = 0; i < by.length; i++)
                for (String activity: act)
                    for (int j = 0; j < type.length; j++) {
                        String incdec = type[j];
                        String b = by[i];
                        String sql="SELECT logbook.activity, AllBio.time as DataTime, logbook.clock as LogTime, logbook.mydate, AllBio.date, AllBio.heatflux, AllBio.skintemp, Allbio.gsr\n" +
                                "FROM AllBio\n" +
                                "INNER JOIN logbook ON substr(logbook.clock, 0,3) == substr(AllBio.time, 0,3) AND logbook.mydate == AllBio.date\n" +
                                "WHERE logbook.activity = \""+activity+"\" \n" +
                                "AND ((substr(AllBio.time, 4,2) >= substr(logbook.clock, 4,2)) or (substr(AllBio.time, 4,2) < (substr(logbook.clock, 4,2) + 30)))\n" +
                                "ORDER BY " + b + " " +incdec+ "\n" +
                                "limit 1";
                        Statement stat=con.createStatement();
                        ResultSet rs = stat.executeQuery(sql);
                        while (rs.next()) {
                            result[counter][0] = rs.getString("DataTime");
                            result[counter][1] = rs.getString("LogTime");
                            result[counter][2] = rs.getString("date");
                            result[counter][3] = rs.getString("heatflux");
                            result[counter][4] = rs.getString("gsr");
                            result[counter][5] = rs.getString("skintemp");
                            result[counter][6] = activity;
                            result[counter][7] = typePr[j];
                            result[counter][8] = prt[i];
                            counter++;
                        }

                    }

            for (int i = 0; i < by.length; i++) {
                for (String activity: act) {
                    String b = by[i];
                    String sql="SELECT logbook.activity, AllBio.time as DataTime, logbook.mydate, AllBio.date, logbook.clock as LogTime, AllBio.heatflux, AllBio.skintemp, Allbio.gsr,\n" +
                            "(SELECT\n" +
                            "    AVG(skintempavg)\n" +
                            "FROM\n" +
                            "(\n" +
                            "SELECT " + b + " as skintempavg\n" +
                            "FROM AllBio\n" +
                            "INNER JOIN logbook ON substr(logbook.clock, 0,3) == substr(AllBio.time, 0,3) AND logbook.mydate == AllBio.date\n" +
                            "WHERE logbook.activity = \"" + activity + "\" \n" +
                            "ORDER BY "+ b +" DESC\n" +
                            ") MyTable) AS Average\n" +
                            "FROM AllBio\n" +
                            "INNER JOIN logbook ON substr(logbook.clock, 0,3) == substr(AllBio.time, 0,3) AND logbook.mydate == AllBio.date\n" +
                            "WHERE logbook.activity = \"" + activity + "\" \n" +
                            "AND " + b + " > Average \n" +
                            "ORDER BY " + b + " ASC\n" +
                            "LIMIT 1\n";
                    Statement stat=con.createStatement();
                    ResultSet rs = stat.executeQuery(sql);
                    while (rs.next()) {
                        result[counter][0] = rs.getString("DataTime");
                        result[counter][1] = rs.getString("LogTime");
                        result[counter][2] = rs.getString("date");
                        result[counter][3] = rs.getString("heatflux");
                        result[counter][4] = rs.getString("gsr");
                        result[counter][5] = rs.getString("skintemp");
                        result[counter][6] = activity;
                        result[counter][7] = "AVG";
                        result[counter][8] = prt[i];
                        counter++;
                    }

                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
        names(result);
        return result;
    }

    /**
     * This method locates the photos that matches the important events' times.
     * @param data, important events.
     */
    public void names(String[][] data) {
        File[] images = new File(IMAGES_PATH).listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("h:m:s");

        for (int i = 0; i < data.length; i++) {
            if (data[i][0] == null)
                return;
            String time = data[i][0];
            long min = Long.MAX_VALUE;
            String fn = "";
            for (File image: images) {
                BasicFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(Paths.get(image.getPath()), BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String mt = attr.lastModifiedTime().toString().substring(11, attr.lastModifiedTime().toString().length() - 1);
                try {
                    if (Math.abs(sdf.parse(time).getTime() - sdf.parse(mt).getTime()) < min) {
                        min = Math.abs(sdf.parse(time).getTime() - sdf.parse(mt).getTime());
                        fn = image.getName();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            data[i][9] = fn;

        }
    }

    /**
     * This method writes the results to a given CSV file.
     * @param res, the results that should be written in the CSV file.
     */
    public void writeCSV(String[][] res) {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(USER_CSV));
            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] headerRecord = {"Time in Bio", "Time in Log", "Date", "HF", "GSR", "ST",
                    "Activity", "Intensity", "Biometric Response", "Photo Name",
                    "Memorable", "Important that Time", "Important Now", "Remember", "Feel that Time",
                    "Feel Re-Presented", "Retrieve the Event"};
            csvWriter.writeNext(headerRecord);
            for (int i = 0; i < res.length; i++) {
                if (res[i][0] == null)
                    break;
                csvWriter.writeNext(res[i]);
            }
            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
