import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

	public static class HoughTransform{
		public class xyCoord{
			int x, y;
			xyCoord(int row, int col){
				y = row;
				x = col;
			}
		}
		
		xyCoord point;
		int angleInDegree;
		double pi = 3.14159, angleInRadians;
		int numRows = 180, numCols, minVal, maxVal;
		int[][] HoughAry;
		PrintWriter outFile1, outFile2;
		
		HoughTransform(int[][] Ary, int row, int col, int min, int max, String out1, String out2) throws FileNotFoundException{
			outFile1 = new PrintWriter(out1);
			outFile2 = new PrintWriter(out2);
			minVal = min;
			maxVal = max;
			numCols = (int)Math.sqrt(Math.pow(row,2) + Math.pow(col,2));
			HoughAry = new int[numRows][numCols];
			for(int i =0; i< numRows; i++){
				for(int j =0; j< numCols; j++){
					HoughAry[i][j] = 0;
				}
			}
			
			for(int i =0; i< row; i++){
				for(int j=0; j< col; j++){
					if(Ary[i][j] > 0){
						point = new xyCoord(i, j);
						for(angleInDegree = 0; angleInDegree < numRows; angleInDegree++){
							angleInRadians = (double)angleInDegree/180*pi;
							int dist = Math.abs(computeDistance(point, angleInRadians));
							HoughAry[angleInDegree][dist]++;
							if(HoughAry[angleInDegree][dist]> maxVal){
								maxVal = HoughAry[angleInDegree][dist];
							}
						}
					}
				}
			}
			determineHeader(HoughAry);
			prettyPrint(HoughAry);
			outFile1.close();
			outFile2.close();
		}
		
		int computeDistance(xyCoord point, double angle){
			int dist = (int)(Math.sqrt(Math.pow(point.x,2)+ Math.pow(point.y, 2)) * Math.cos(angle - Math.atan2(point.y,point.x) - pi/2));
			return dist;
		}
		
		void determineHeader(int[][] Ary){
			outFile1.println(numRows + " " + numCols + " " + minVal + " " + maxVal);
			for(int i =0; i< numRows; i++){
				for(int j =0; j< numCols; j++){
					outFile1.print(Ary[i][j] + " ");
				}
				outFile1.println();
			}
		}
		
		void prettyPrint(int[][] Ary){
			for(int i =0; i< numRows; i++){
				for(int j =0; j< numCols; j++){
					if(Ary[i][j] > 0){
						outFile2.print(".");
					}
					else{
						outFile2.print(" ");
					}
				}
				outFile2.println();
			}
		}
	}
	
	public static class ImageP{
		int numRows, numCols, minVal, maxVal;
		int[][] imgAry;
		Scanner inFile;
		ImageP(String input, String output1, String output2) throws FileNotFoundException{
			inFile = new Scanner(new FileReader(input));
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			imgAry = new int[numRows][numCols];
			loadImage();
			HoughTransform hough = new HoughTransform(imgAry, numRows, numCols, minVal, maxVal, output1, output2);
			inFile.close();
			
		}
		
		void loadImage(){
			for(int i =0; i< numRows; i++){
				for(int j =0; j< numCols; j++){
					imgAry[i][j] = inFile.nextInt();
				}
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException{
		ImageP img = new ImageP(args[0],args[1],args[2]);
	}
}
