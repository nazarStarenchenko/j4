import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Parser {
	private String dirIn, fileOut;
	private String delimiter = ",", uniter = "+";
	private final Object MONITOR = new Object();
	//there might be 1,2,8,32 streams
	private int numberOfStreams = 1;

	Parser() {
		System.out.println("No input or outputfile"); 
	}

	Parser( String unit,String delimit, String inputDir, String outputFile, int numberOfStreamsFromUser)
	{
		delimiter = delimit; 
		uniter = unit; 
		dirIn = inputDir; 
		fileOut = outputFile; 
		numberOfStreams = numberOfStreamsFromUser; 
	}
	public void parseFile(String dirInPath , String fileOut, int numberOfStreamsFromUser) {

		File dirIn = new File(dirInPath);
		File[] files = dirIn.listFiles();
		ArrayList<String> addToFileLater = new ArrayList<String>(); 
		//cleaning result file before every run of main code
		try {
			PrintWriter cleanWriter = new PrintWriter(fileOut);
			cleanWriter.print("");
			cleanWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//multithread file working
		ExecutorService service = Executors.newFixedThreadPool(numberOfStreamsFromUser);
		for (File f: files){
			service.execute(new Runnable() {
				@Override
				public void run() {
					//opening files that we will be woring with
					try(BufferedReader reader = new BufferedReader(new FileReader(f));) {

						String temp;
						String line = reader.readLine();
						while (line != null) {
									temp = parseArrayList(parseLine(line)) + "\n";
									addToFileLater.add(temp);
									line = reader.readLine();
								}

						System.out.println(addToFileLater);

						saveToFile(addToFileLater);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		service.shutdown();
   	}

   	public void saveToFile(ArrayList<String> strings) {
		synchronized (MONITOR) {       
	      	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut, true))) {
	            for (String string : strings) {
	                writer.write(string);
	                writer.flush();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    }
    

   	//parisng one line of file into Array List [abc,hd,str]
    public ArrayList<String> parseLine(String line) {
        ArrayList<String> result = new ArrayList<String>();
        StringBuilder subStr = new StringBuilder();
        boolean quoteStatus = false;
        boolean collectStatus = false;
        char[] characters = line.toCharArray();
        char tempDelim = delimiter.charAt(0);
        
        for (char ch : characters) {

        	       	
        		if (quoteStatus == true) {

	                collectStatus = true;
	                if (ch == '"') quoteStatus = false;
	                else subStr.append(ch);
	            } else if(quoteStatus == false) {
	                if (ch == '"') {
	                    quoteStatus = true;
	                    if (characters[0] != '"') subStr.append('"');
	                    if (collectStatus == true) subStr.append('"');
	                } else if (ch == tempDelim) {
	                    if (!subStr.toString().equals("/")) result.add(subStr.toString());
	                    subStr = new StringBuilder();
	                    collectStatus = false;
	                } else if (ch != '\r') if (ch == '\n') break;
	                else subStr.append(ch);
	            }
	    }

        if (!subStr.toString().equals("/")) result.add(subStr.toString());
        return result;
    }

    //transforming array list from parseLine into string of needed format: "3+4+5"
    public String parseArrayList (ArrayList<String> arrList) {
    	String result;
    	StringBuilder tempStr = new StringBuilder();	

    	for (String strIter : arrList) {
    		tempStr.append(strIter.length());
    		tempStr.append(uniter);
	   	}
	   	tempStr.setLength(tempStr.length() - 1);
	   	result = tempStr.toString();
	   	return result;
    }

    //getters and setters
    public String getDelimiter() {
            return delimiter;
        }

        public String getUniter() {
            return uniter;
	}

	public int setDelimiter(String delim) {
		if(true){
			delimiter = delim;
			return 1;
		}else return 0;
	}

	public int setUniter(String unit) {
		if(true){
			uniter = unit;
			return 1;
		}else return 0;
	}

	public String getdirIn () {
		return dirIn;
	}

	public String getFileOut() {
		return fileOut;
	}
	
	public int getNumberOfStreams() {
		return numberOfStreams;
	}

	public int setNumberOfStreams(int num) {
		if(true){
			numberOfStreams = num;
			return 1;
		}else return 0;
	}

}


