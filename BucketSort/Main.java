import java.io.*;
import java.util.Scanner;


public class Main {
	
	public static class bucketSort{
		
		int [] bucketAry;
		int bucketSize, offSet, minData, maxData;
		int count = 0;
		
		public void findMinMax(int value){
			int temp = value;
			if(count == 0){
				minData = temp;
				maxData = temp;
			}
			else if(temp > maxData){
				maxData = temp;
			}
			else if(temp < minData){
				minData = temp;
			}
			count++;
		}
		
		public void printBucketAry(PrintWriter file2){
			for(int i =0; i<= 18; i++){
				file2.print(i + " ");
			}
			file2.println();
			for(int i =0; i <=18; i++){
				if(i>10){
					file2.print(" " + bucketAry[i] + " ");
				}
				else{
					file2.print(bucketAry[i] + " ");
				}
			}
		}
		
		public void printSortedData(PrintWriter file1){
			for(int index =0; index <= bucketSize; index ++){
				while(bucketAry[index]!=0){
					file1.println(index + offSet);
					bucketAry[index]--;
				}
			}
		}
		
		public int getIndex(int value){
			return value;
		}
		
		bucketSort(String input, PrintWriter file2) throws IOException{
			int data;
			Scanner inFile = new Scanner(new FileReader(input));
			while(inFile.hasNext()){
				data = inFile.nextInt();
				findMinMax(data);
			}
			inFile.close();
			bucketSize = maxData - minData;
			offSet = minData;
			bucketAry = new int[bucketSize+1];
			bucketAry[0] = 0;
		
		}
	}
	public static void main(String[] args) throws IOException{
		
		int data;
		Scanner inFile = new Scanner( new FileReader(args[0]));
		PrintWriter outfile1 = new PrintWriter( new FileWriter(args[1]));
		PrintWriter outfile2 = new PrintWriter (new FileWriter(args[2]));
		
		bucketSort bucket = new bucketSort(args[0], outfile2);
		while(inFile.hasNext()){
			data = inFile.nextInt();
			int index = bucket.getIndex(data-bucket.offSet);
			bucket.bucketAry[index]++;
			outfile2.println("The Data is: " + data + " and its index is: " + index );
		}
		bucket.printBucketAry(outfile2);
		bucket.printSortedData(outfile1);
		
		inFile.close();
		outfile1.close();
		outfile2.close();

	}

}
