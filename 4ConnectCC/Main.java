import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

	public static class ConnectedCC{
		
		int numRows, numCols, minVal, maxVal, newMin, newMax, newLabel = 0;
		int[][] zeroFramedAry;
		int[] EQAry;
		int[] NeighborAry = new int[3];
		int usedLabel = 0;
		
		class Property{
			int label, numpixels;
			int[] boundBox = {999,999, -999, -999};
			
			Property(){
				
			}
		}
		
		Scanner inFile;
		PrintWriter outFile, outFile2, outFile3;
		
		ConnectedCC(String input, String output, String output2, String output3) throws IOException{
			inFile = new Scanner(new FileReader(input));
			outFile = new PrintWriter(new FileWriter(output));
			outFile2 = new PrintWriter(new FileWriter(output2));
			outFile3 = new PrintWriter(new FileWriter(output3));
			
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			
			zeroFramedAry = new int[numRows+2][numCols+2];
			EQAry = new int [(numRows*numCols)/4];
			
			zeroFramed();
			loadImage();
			ConnectCC_Pass1();
			prettyPrint(1);
			manageEQAry(1);
			ConnectCC_Pass2();
			prettyPrint(2);
			manageEQAry(2);
			updateEQAry();
			manageEQAry(4);
			ConnectCC_Pass3();
			prettyPrint(3);
			manageEQAry(3);
			
			outFile2.println(numRows + " " + numCols + " " + newMin + " " + newMax);
			for(int i=1; i< numRows+1; i++){
				for(int j=1; j<numCols+1; j++){
					outFile2.print(zeroFramedAry[i][j] + " ");
				}
				outFile2.println();
			}
			
			inFile.close();
			outFile.close();
			outFile2.close();
			outFile3.close();
		}
		
		public void zeroFramed(){
			for(int i =0; i< numRows+2; i++){
				zeroFramedAry[i][0] = 0;
				zeroFramedAry[i][numCols+1] = 0;
			}
			
			for(int j = 0; j< numCols+2; j++){
				zeroFramedAry[0][j] = 0;
				zeroFramedAry[numRows+1][j] = 0;
			}
			
		}
		
		public void loadImage(){
			for(int i =1; i<=numRows; i++){
				for(int j=1; j<=numCols; j++){
					zeroFramedAry[i][j] = inFile.nextInt();
				}
			}
		}
		
		public void loadNeighbors(int row, int col, int pass){
			
				for(int i =0; i<3; i++){
					if(i ==2){
						NeighborAry[i] = zeroFramedAry[row][col];
					}
					else if( pass ==1){
						NeighborAry[i] = zeroFramedAry[row-1+i][col-i];
					}
					else if (pass ==2){
						NeighborAry[i] = zeroFramedAry[row+1-i][col+i];
					}
				}
		}
		
		public void ConnectCC_Pass1(){
			for(int i =1; i< numRows+1; i++){
				for( int j=1; j< numCols+1; j++){
					if(zeroFramedAry[i][j] > 0){
						loadNeighbors(i, j, 1);
						if(NeighborAry[0] == 0 && NeighborAry[1] == 0){
							newLabel++;
							EQAry[newLabel] = newLabel;
							zeroFramedAry[i][j] = newLabel;
						}
						else if(NeighborAry[0] > 0 || NeighborAry[1] > 0){
							if(NeighborAry[0] >0 && NeighborAry[1] == 0){
								newMin = NeighborAry[0];
								newMax = newMin;
								zeroFramedAry[i][j] = newMin;
							}
							else if(NeighborAry[1] > 0 && NeighborAry[0] ==0){
								newMin = NeighborAry[1];
								newMax = newMin;
								zeroFramedAry[i][j] = newMin;
							}
							else if(NeighborAry[0] > 0 && NeighborAry[1] > 0){
								if(NeighborAry[0] > NeighborAry[1]){
									newMin = NeighborAry[1];
									newMax = NeighborAry[0];
									zeroFramedAry[i][j] = newMin;
								}
								else{
									newMin = NeighborAry[0];
									newMax = NeighborAry[1];
									zeroFramedAry[i][j] = newMin;
								}
							}
						}
					}
				}
			}
		}
		
		public void ConnectCC_Pass2(){
			for(int i =numRows; i>=1; i--){
				for(int j =numCols; j>=1; j--){
					if(zeroFramedAry[i][j] >0){
						loadNeighbors(i,j,2);
						if(NeighborAry[0]> 0 || NeighborAry[1] > 0){
							if(NeighborAry[0] >0 && NeighborAry[1] ==0 && NeighborAry[0] < zeroFramedAry[i][j]){
								newMin = NeighborAry[0];
								newMax = zeroFramedAry[i][j];
								EQAry[newMax] = newMin;
								zeroFramedAry[i][j] = newMin;
							}
							else if(NeighborAry[0] ==0 && NeighborAry[1] >0 && NeighborAry[1] < zeroFramedAry[i][j]){
								newMin = NeighborAry[1];
								newMax = zeroFramedAry[i][j];
								EQAry[newMax] = newMin;
								zeroFramedAry[i][j] = newMin;
							}
							else if(NeighborAry[0] >0 && NeighborAry[1] > 0 && NeighborAry[0] != NeighborAry[1]){
								if(NeighborAry[0] < NeighborAry[1] && NeighborAry[0] < zeroFramedAry[i][j]){
									newMin = NeighborAry[0];
									if(zeroFramedAry[i][j] < NeighborAry[1]){
										newMax = NeighborAry[1];
									}
									else{
										newMax = zeroFramedAry[i][j];
									}
									EQAry[zeroFramedAry[i+1][j]] = newMin;
									zeroFramedAry[i][j+1] = newMin;
									EQAry[newMax] = newMin;
									zeroFramedAry[i][j] = newMin;
								}
								else if( NeighborAry[0] > NeighborAry[1] && NeighborAry[1] < zeroFramedAry[i][j]){
									newMin = NeighborAry[1];
									if(zeroFramedAry[i][j] < NeighborAry[0]){
										newMax = NeighborAry[0];
									}
									else{
										newMax = zeroFramedAry[i][j];
									}
									EQAry[zeroFramedAry[i][j+1]] = newMin;
									EQAry[newMax] = newMin;
									zeroFramedAry[i][j] = newMin;
								}
							}
							else if( NeighborAry[0] > 0 && NeighborAry[1] > 0 && NeighborAry[0] == NeighborAry[1]){
								newMin = NeighborAry[0];
								newMax = zeroFramedAry[i][j];
								EQAry[newMax] = newMin;
								zeroFramedAry[i][j] = newMin;
							}
						}
					}
				}
			}
		}
		
		public void ConnectCC_Pass3(){
			int[] count = new int[usedLabel+1];
			newMin =0;
			newMax = 0;
			Property[] propcc = new Property[usedLabel+1];
			for(int i =0; i< propcc.length; i++){
				propcc[i]= new Property();
			}
			for(int i=1; i<= numRows; i++){
				for(int j=1; j<=numCols; j++){
					if(zeroFramedAry[i][j] >0){
						zeroFramedAry[i][j] = EQAry[zeroFramedAry[i][j]];
						if(newMin > zeroFramedAry[i][j]){
							newMin = zeroFramedAry[i][j];
						}
						if(newMax < zeroFramedAry[i][j]){
							newMax = zeroFramedAry[i][j];
						}
						count[zeroFramedAry[i][j]]++;
						loadNeighbors(i,j,1);
						if(NeighborAry[0] ==0 && NeighborAry[1] ==0 && i < propcc[zeroFramedAry[i][j]].boundBox[0]){
							propcc[zeroFramedAry[i][j]].boundBox[0] = i;
						}
						if(NeighborAry[0] ==0 && NeighborAry[1] == 0 && j < propcc[zeroFramedAry[i][j]].boundBox[1]){
							propcc[zeroFramedAry[i][j]].boundBox[1] = j;
						}
						loadNeighbors(i,j, 2);
						if(NeighborAry[0] ==0 && NeighborAry[1] == 0 && i > propcc[zeroFramedAry[i][j]].boundBox[3]){
							propcc[zeroFramedAry[i][j]].boundBox[2] = i; 
						}
						if(NeighborAry[0] ==0 && NeighborAry[1] == 0 && j > propcc[zeroFramedAry[i][j]].boundBox[3]){
							propcc[zeroFramedAry[i][j]].boundBox[3] = j;
						}
					}
				}
			}
			
			for(int i =1; i<= usedLabel; i++){
				propcc[i].label = i;
				propcc[i].numpixels = count[i];
				outFile3.println("Label: " + propcc[i].label);
				outFile3.println("Number of pixels: " + propcc[i].numpixels);
				outFile3.println("MinRow MinCol MaxRow MaxCol");
				outFile3.println(propcc[i].boundBox[0] +"     " +propcc[i].boundBox[1] + "     "+ propcc[i].boundBox[2] + "     "+propcc[i].boundBox[3]);
			}
		}
		
		public void updateEQAry(){
			for(int i =1; i<= newLabel; i++){
				if(EQAry[i] == i){
					usedLabel++;
					EQAry[i] = usedLabel;
				}
				else if(EQAry[i] != i){
					EQAry[i] = EQAry[EQAry[i]];
				}
			}
		}
		
		public void manageEQAry(int pass){
			if(pass ==4){
				outFile.println("This is the EQAry of pass after managing");
			}
			else{
				outFile.println("This is the EQAry of pass " + pass);
			}
			for(int i=0; i<=newLabel; i++){
				outFile.print(EQAry[i] + " ");
			}
			outFile.println();
		}
		
		public void prettyPrint(int pass){
			outFile.println("This is the pretty print of pass " + pass);
			
			for(int i =1; i< numRows+2; i++){
				for(int j =1; j< numCols+2; j++){
					if(zeroFramedAry[i][j] > 0){
						outFile.print(zeroFramedAry[i][j] + " ");
					}
					else{
						outFile.print("  ");
					}
				}
				outFile.println();
			}
		}
	}
	
	public static void main(String args[]) throws IOException{
		
		ConnectedCC connected = new ConnectedCC(args[0], args[1], args[2], args[3]);
		
	}
}
