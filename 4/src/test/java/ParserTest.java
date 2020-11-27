import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class ParserTest {
    private Parser instance;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    public ParserTest() {
        this.instance = new Parser();
    }

    @Test
    public void testParseArrayList() {
        assertEquals("1+1+1", instance.parseArrayList(new ArrayList<>(Arrays.asList("a", "b", "c"))));
        assertEquals("3+4+6", instance.parseArrayList(new ArrayList<>(Arrays.asList("abc","1234" , "q2!cvb"))));
        assertEquals("8+6", instance.parseArrayList(new ArrayList<>(Arrays.asList("abc,1234" , "q2!cvb"))));
        assertEquals("2+2+0+9", instance.parseArrayList(new ArrayList<>(Arrays.asList("10","AU", "", "Australia"))));
        assertEquals("2+2+13", instance.parseArrayList(new ArrayList<>(Arrays.asList("13","AU", "Aus \"\" tralia"))));

    }

    @Test
    public void testSaveFile() throws IOException{
        ArrayList<String> test = new ArrayList<String>();
        test.add("string1\n");
        this.instance.saveToFile(test);
    }

    @Test
    public void testParseLine () {
        assertEquals(new ArrayList<>(Arrays.asList("a", "b", "c")), instance.parseLine("\"a\",b,c"));
        assertEquals(new ArrayList<>(Arrays.asList("abc", "1234", "q2!cvb")), instance.parseLine("abc,1234,q2!cvb"));
        assertEquals(new ArrayList<>(Arrays.asList("abc,1234", "q2!cv")), instance.parseLine("\"abc,1234\",q2!cv"));
        assertEquals(new ArrayList<>(Arrays.asList("10", "AU", "" ,"Australia")), instance.parseLine("10,AU,,Australia"));

    }

    @Test
    public void testSetterGetter() {
        Parser testInstance = new Parser("1", "2", "base.csv", "result.txt", 32);
        assertEquals("2", testInstance.getDelimiter());
        assertEquals("1", testInstance.getUniter());
        assertEquals(1, testInstance.setDelimiter(","));
        assertEquals(1, testInstance.setUniter("+"));
        assertEquals("base.csv", testInstance.getdirIn());
        assertEquals(32, testInstance.getNumberOfStreams());
        assertEquals(1, testInstance.setNumberOfStreams(32));
        assertEquals("result.txt", testInstance.getFileOut());
    }


    @Test
    public void testParseFile() throws IOException {
        final File tempDirIn = tempFolder.newFolder("subfolder");
        final File tempFileOut = tempFolder.newFile("result.txt");
        final File tempFileIn = tempFolder.newFile("base.csv");
       
        this.instance.parseFile(tempDirIn.getAbsolutePath(), tempFileOut.getAbsolutePath(), 1);
            
    }
    
}
