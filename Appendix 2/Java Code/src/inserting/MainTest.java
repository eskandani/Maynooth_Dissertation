package inserting;

import java.io.File;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void getAttributesTest1() {
        Main main = new Main(Main.IMAGES_PATH, Main.CONNECTION_URL, Main.LIFELOG_CSV, Main.USER_CSV);
        String[][] att = main.getAttributes();
        assertEquals(att[0][0], "08:02:00");
        assertEquals(att[0][1], "08:02:00");
        assertEquals(att[0][2], "2018-06-07");
        assertEquals(att[0][3], "92.7556648254395");
        assertEquals(att[0][4], "0.01685038395226");
        assertEquals(att[0][5], "25.5014209747314");
        assertEquals(att[0][6], "Walking");
        assertEquals(att[0][7], "MIN");
        assertEquals(att[0][8], "ST");
        assertEquals(att[0][9], "SNAP0123.JPG");
    }

    @org.junit.Test
    public void getAttributesTest2() {
        Main main = new Main(Main.IMAGES_PATH, Main.CONNECTION_URL, Main.LIFELOG_CSV, Main.USER_CSV);
        String[][] att = main.getAttributes();
        assertEquals(att[1][0], "10:30:00");
        assertEquals(att[1][1], "10:30:00");
        assertEquals(att[1][2], "2018-06-07");
        assertEquals(att[1][3], "92.6816253662109");
        assertEquals(att[1][4], "0.115777716040611");
        assertEquals(att[1][5], "29.9552822113037");
        assertEquals(att[1][6], "Walking");
        assertEquals(att[1][7], "MAX");
        assertEquals(att[1][8], "ST");
        assertEquals(att[1][9], "SNAP0420.JPG");
    }

    @org.junit.Test
    public void getAttributesTestNull1() {
        Main main = new Main(Main.IMAGES_PATH, Main.CONNECTION_URL, Main.LIFELOG_CSV, Main.USER_CSV);
        String[][] att = main.getAttributes();
        assertEquals(att[44][0], null);
    }
    @org.junit.Test
    public void writeCSV() {
        Main main = new Main(Main.IMAGES_PATH, Main.CONNECTION_URL, Main.LIFELOG_CSV, Main.USER_CSV);
        String[][] att = main.getAttributes();
        main.writeCSV(att);
        assertNotNull(new File(Main.USER_CSV));
    }
}