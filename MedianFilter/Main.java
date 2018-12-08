import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static class Median {
		
		int numRows, numCols, maxVal, minVal, newMin = 999, newMax = -999;
		int [][] tempAry, mirrorFramedAry;
		int [] neighborAry = new int[9];
		Scanner inFile;
		PrintWriter outFile;
		
		Median(String input, String output) throws IOException{
			inFile = new Scanner( new FileReader(input));
			outFile = new PrintWriter( new FileWriter(output));
			
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			
			mirrorFramedAry = new int [numRows+2][numCols+2];
			tempAry = new int [numRows+2][numCols+2];
			
			loadImage();
			mirrorFramed();
			
			for(int i =1; i< numRows+1; i++){
				for(int j =1; j< numCols+1 ; j++){
					loadNeighbors(i, j);
					
					for(int k=0; k<5; k++){
						for(int h = k+1; h< 9; h++){
							if(neighborAry[k] > neighborAry[h]){
								int temp = neighborAry[k];
								neighborAry[k] = neighborAry[h];
								neighborAry[h] = temp;
							}
						}
					}
					
					tempAry[i][j] = neighborAry[4];
					if(tempAry[i][j] > newMax){
						newMax = tempAry[i][j];
					}
					else if( tempAry[i][j] < newMin){
						newMin = tempAry[i][j];
					}
				}
			}
			
			outFile.println(numRows + " " + numCols + " " + newMin + " " + newMax);
			for(int i =1; i< numRows+1; i++){
				for(int j=1; j< numCols+1; j++){
					outFile.print(tempAry[i][j]+ " ");
				}
				outFile.println();
			}
			
			inFile.close();
			outFile.close();
		}
		
		public void mirrorFramed(){
			for(int i =0; i<numRows+2; i++){
				mirrorFramedAry[i][0] = mirrorFramedAry[i][1];
				mirrorFramedAry[i][numCols+1] = mirrorFramedAry[i][numCols];
			}
			
			for(int j =0; j< numCols+2; j++){
				mirrorFramedAry[0][j] = mirrorFramedAry[1][j];
				mirrorFramedAry[numRows+1][j] = mirrorFramedAry[numRows][j];
				
			}
		}
		
		public void loadImage(){
			for(int i =1; i <= numRows; i++){
				for(int j=1; j<=numCols; j++){
					mirrorFramedAry[i][j] = inFile.nextInt();
				}
			}
		}
		
		public void loadNeighbors(int row, int col){
			
			for(int i =0; i< 9; i++){
				if(i < 3){
					neighborAry[i] = mirrorFramedAry[row-1][(col-1) +i];
				}
				else if(i>=3 && i<6){
					neighborAry[i] = mirrorFramedAry[row][(col-1) + (i-3)];
				}
				else{
					neighborAry[i] = mirrorFramedAry[row+1][(col-1)+ (i-6)];
				}
			}
		}
	}

	public static void main(String [] args) throws IOException{
		
		Median med = new Median(args[0], args[1]);
	}
}
