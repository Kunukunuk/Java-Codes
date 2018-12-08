
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

	public static class Threshold{
		
		int numRows, numCols, minVal, maxVal;
		int thre_val, pixel_val;
		Scanner inFile;
		PrintWriter outFile;
		
		Threshold(String input) throws IOException{
			inFile = new Scanner( new FileReader (input));
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
		}
		
		void computeThreshold() throws IOException{
			System.out.println("What value would you like for the threshold?");
			Scanner value = new Scanner(System.in);
			thre_val = value.nextInt();
			value.close();
			
			outFile = new PrintWriter("Thr_" + thre_val + ".txt");
			outFile.println(numRows + " " + numCols + " " + 0 + " " + 1);
			for(int i=0; i<numRows; i++){
				for(int j=0; j<numCols; j++){
					pixel_val = inFile.nextInt();
					if(pixel_val < thre_val){
						outFile.print(0+ " ");
					}
					else{
						outFile.print(1 + " ");
					}
				}
				outFile.println();
			}
			inFile.close();
			outFile.close();
			
		}
	}
	public static void main(String[] args) throws IOException{
		
		Threshold thr = new Threshold(args[0]);
		thr.computeThreshold();

	}

}
