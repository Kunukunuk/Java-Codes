import java.io.*;
import java.util.Scanner;

public class Main {

public static class HeapSort{
		
		int intItem;
		int[] heapAry;
		int count = 1;
		int size;
		
		HeapSort(int arySize){
			size = arySize+1;
			heapAry = new int[size];
			heapAry[0] = 0;
		}
		
		void buildHeap(String data, PrintWriter outFile) throws IOException{
			Scanner inFile = new Scanner(new FileReader(data));
			while(inFile.hasNext()){
				intItem = inFile.nextInt();
				if(isHeapFull()){
					outFile.println("FULL");
				}
				outFile.print("Inserting this number: " + intItem);
				insertOneDataItem(intItem);
				printHeap(outFile);
			}
			inFile.close();
		}
		
		void deleteHeap(PrintWriter outFile){
			while(!isHeapEmpty()){
				deleteRoot();
				bubbleDown();
				printHeap(outFile);
			}
			
		}
		void insertOneDataItem(int value){
			if(isHeapEmpty()){
				heapAry[1] = value;
				heapAry[0]++;
				count++;
			}
			else if(isHeapFull()){
				
			}
			else {
				heapAry[count] = value;
				count++;
				heapAry[0]++;
				bubbleUp();
			}
		}
		
		void deleteRoot(){
			int last = heapAry[0];
			heapAry[1] = heapAry[last];
			heapAry[0]--;
		}
		
		void bubbleUp(){
			for(int last = heapAry[0]; last >= 1; last-=2){
				if(heapAry[last] < heapAry[last/2] && last !=1){
					int temp = heapAry[last];
					heapAry[last] = heapAry[last/2];
					heapAry[last/2] = temp;
				}
			}
		}
		void bubbleDown(){
			
			int parent = 1;
			int lkid = parent*2;
			int rkid = lkid+1;
			int children;
			while(lkid < heapAry[0] && heapAry[parent] > heapAry[lkid] || heapAry[parent] > heapAry[rkid] ){
				if(heapAry[lkid] < heapAry[rkid]){
					children = lkid;
				}
				else{
					children = rkid;
				}
				int temp = heapAry[parent];
				heapAry[parent] = heapAry[children];
				heapAry[children] = temp;
				parent = children;
				lkid = parent*2;
				rkid = lkid+1;
			}
		}
		
		boolean isHeapFull(){
			if(heapAry[0] == size){
				return true;
			}
			return false;
		}
		boolean isHeapEmpty(){
			if(heapAry[0] == 0){
				return true;
			}
			return false;
		}
		
		void printHeap(PrintWriter outFile){
			outFile.println("This is the first 10 items: ");
			for(int i = 1; i<= 10; i++){
				outFile.println(heapAry[i]);
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int data; 
		int count = 0;
		Scanner inFile = new Scanner(new FileReader(args[0]));
		PrintWriter outFile = new PrintWriter( new FileWriter(args[1]));
		PrintWriter outFile2 = new PrintWriter(new FileWriter(args[2]));
		while(inFile.hasNext()){
			data = inFile.nextInt();
			count++;
		}
		inFile.close();
		HeapSort heap = new HeapSort(count);
		heap.buildHeap(args[0], outFile);
		heap.deleteHeap(outFile2);
		outFile.close();
		outFile2.close();
	}

}
