import java.util.Scanner;



public class Main{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
			
		/*
		System.out.println("enter input directory: ");
		String inputDir = scan.nextLine();
		
		System.out.println("enter output file: ");
		String outputFile = scan.nextLine();

		System.out.println("enter delimeter: ");
		String delimiter = scan.nextLine();
		
		System.out.println("enter uniter: ");
		String uniter = scan.nextLine();

		System.out.println("enter number of streams: ");
		int numberOfStreams = scan.nextInt();
*/
		scan.close();

		//creating parser object
		String uniter = "+", delimiter = ",";
		String inputDir = "../resources/csv", outputFile =  "../resources/res.txt";
		int numberOfStreams = 1;
		Parser csvParser = new Parser( uniter, delimiter, inputDir, outputFile, numberOfStreams);

		//running main function and getting amount of time it worked
		long start = System.currentTimeMillis();
		csvParser.parseFile(inputDir, outputFile, numberOfStreams);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.println(elapsedTime);
	}
}
