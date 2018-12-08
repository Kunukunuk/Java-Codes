import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

	public static class EuclideanD{
		int numRows, numCols, minVal, maxVal;
		double newMin, newMax;
		double[][] zeroFramedAry;
		double[] NeighborAry = new double[5];
		double diagonal = Math.sqrt(2);
		Scanner inFile;
		PrintWriter outFile, outFile2;
		
		EuclideanD(String input, String output, String output2) throws IOException{
			inFile = new Scanner(new FileReader(input));
			outFile = new PrintWriter(new FileWriter(output));
			outFile2 = new PrintWriter(new FileWriter(output2));
			
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			
			zeroFramedAry = new double[numRows+2][numCols+2];
			newMin = minVal;
			newMax = maxVal;
			zeroFramed();
			loadImage();
			firstPassEuclideanDistance();
			prettyPrintDistance(1);
			secondPassEuclideanDistance();
			prettyPrintDistance(2);
			newMax = Math.round(newMax*100.0)/100.0;
			outFile.println(numRows + " " + numCols + " " + newMin + " " + newMax);
			for(int i =1; i< numRows+1; i++){
				for(int j =1; j< numCols+1; j++){
					outFile.print(Math.round(zeroFramedAry[i][j]*100.0)/100.0);
					if(zeroFramedAry[i][j] != 0 && zeroFramedAry[i][j] - (int)zeroFramedAry[i][j] > 0.0){
						outFile.print(" ");
					}
					else{
						outFile.print("  ");
					}
				}
				outFile.println();
			}
			inFile.close();
			outFile.close();
			outFile2.close();
		}
		
		public void zeroFramed(){
			for(int i = 0; i< numRows+2; i++){
				zeroFramedAry[i][0] = 0;
				zeroFramedAry[i][numCols+1] = 0;
			}
			
			for(int j = 0; j< numCols+2; j++){
				zeroFramedAry[0][j] = 0;
				zeroFramedAry[numRows+1][j] = 0;
			}
		}
		
		public void loadImage(){
			for(int i=1; i<=numRows; i++){
				for(int j =1; j<= numCols; j++){
					zeroFramedAry[i][j] = inFile.nextInt();	
				}
			}
		}
		
		public void firstPassEuclideanDistance(){
			for(int i=1; i< numRows+1; i++){
				for(int j=1; j < numCols+1; j++){
					if(zeroFramedAry[i][j] > 0){
						zeroFramedAry[i][j] = findMin(i,j,1);
					}
				}
			}
		}
		
		public void secondPassEuclideanDistance(){
			for(int i=numRows; i >= 1; i--){
				for(int j= numCols; j >= 1; j--){
					if(zeroFramedAry[i][j] > 0){
						zeroFramedAry[i][j] = findMin(i,j,2);
						if(newMax < zeroFramedAry[i][j]){
							newMax = zeroFramedAry[i][j];
						}
					}
				}
			}
		}
		
		double findMin(int row, int col, int pass){
			double min= 999.99;
			loadNeighbors(row, col, pass);
			if(pass == 2){
				min = NeighborAry[4];
			}
			for(int i=0; i<4; i++){
				if(pass ==1 && i%2==0 && min > NeighborAry[i]){
					min = NeighborAry[i];
				}
				else if(pass ==1 && i%2!=0 && min > NeighborAry[i]){
					min = NeighborAry[i];
				}
				else if(pass ==2){
					if(i%2==0 && min > NeighborAry[i]){
						min = NeighborAry[i];
					}
					else if(i%2!=0 && min > NeighborAry[i]){
						min = NeighborAry[i];
					}
				}
			}
			return min;
		}
		
		public void loadNeighbors(int row, int col, int pass){
			for(int i =0; i<5; i++){
				if(i <3 && pass ==1){
					NeighborAry[i] = zeroFramedAry[row-1][col-1+i];
				}
				else if(pass ==1 && i >=3){
					NeighborAry[i] = zeroFramedAry[row][col-4 +i];
				}
				else if(pass ==2 && i<3){
					NeighborAry[i] = zeroFramedAry[row+1][col+1-i];
				}
				else if(pass ==2 && i>=3){
					NeighborAry[i] = zeroFramedAry[row][col+4-i];
				}
			}
			for(int i=0; i<4; i++){
				if(i%2==0){
					NeighborAry[i] = NeighborAry[i]+diagonal;
				}
				else if(i%2 !=0 ){
					NeighborAry[i] = NeighborAry[i]+1;
				}
			}
		}
		
		public void prettyPrintDistance(int pass){
			outFile2.println("This is the pretty print of pass " + pass );
			for(int i = 1; i< numRows+2; i++){
				for(int j =1; j < numCols+2; j++){
					if(zeroFramedAry[i][j] > 0){
						outFile2.print((int)(zeroFramedAry[i][j]+0.5) + " ");
					}
					else {
						outFile2.print("  ");
					}
				}
				outFile2.println();
			}
		}
	}
	
	public static void main(String args[]) throws IOException{
		EuclideanD distance = new EuclideanD(args[0], args[1], args[2]);
	}
}
