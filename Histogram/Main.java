import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {
	
	public static class Histogram{
		
		int[] histogram;
		int numRows, numCols, minVal, maxVal;
		int pij;
		
		Scanner inFile;
		PrintWriter outFile;
		
		Histogram(String input, String output) throws IOException{
			
			inFile = new Scanner( new FileReader(input));
			outFile = new PrintWriter( new FileWriter(output));
			
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			histogram = new int[maxVal+1];
			histogram[0] = 0;
			
			computeHistogram();
		}
		
		public void computeHistogram(){
			while(inFile.hasNext()){
				pij = inFile.nextInt();
				histogram[pij]++;
			}
			for(int i= 0; i <= maxVal; i++){
				if(histogram[i] == 0){
					outFile.print("( " + i + "): 0 \n" );
				}
				else if(histogram[i] < 80){
					outFile.print("( " + i + "):" + histogram[i] + " ");
					for(int j =1 ; j <= histogram[i]; j++){
						outFile.print("+");
					}
					outFile.println();
				}
				else{
					outFile.print("( " + i + "):" + histogram[i] + " ");
					for(int k = 1; k <= 80; k++){
						outFile.print("+");
					}
					outFile.println();
				}
			}
			inFile.close();
			outFile.close();
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		
		Histogram histog = new Histogram(args[0], args[1]);
		
	}
}
