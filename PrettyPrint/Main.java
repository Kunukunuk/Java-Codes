import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

	public static void prettyprint(String input) throws IOException{
		
		int numRows, numCols, minVal, maxVal, pixel_val;
		Scanner inFile;
		PrintWriter outFile;
		
		inFile = new Scanner( new FileReader(input));
		input = input.substring(0, input.length()-4);
		outFile = new PrintWriter(input + "_PP.txt");
		
		numRows = inFile.nextInt();
		numCols = inFile.nextInt();
		minVal = inFile.nextInt();
		maxVal = inFile.nextInt();
		
		for( int i =0; i< numRows; i++){
			for(int j =0; j<numCols; j++){
				pixel_val = inFile.nextInt();
				if(pixel_val >0){
					outFile.print(1 + " ");
				}
				else{
					outFile.print(" ");
				}
			}
			outFile.println();
		}
		
		inFile.close();
		outFile.close();
	}
	
	public static void main(String[] args) throws IOException{
		
		prettyprint(args[0]);
	}
}
