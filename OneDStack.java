import java.io.*;
import java.util.Scanner;

public class OneDStack {
	
	int top;
	int[] StackArray;
	
	public OneDStack(int size){
		top = -1;
		StackArray = new int[size];
	}
	
	public void push(int data){
		top++;
		StackArray[top] = data;
	}
	
	public int pop(){
		int temp = StackArray[top];
		if(top == -1){
			return 0;
		}
		top--;
		return temp;
	}
	public boolean isEmpty(){
		if(top == -1){
			return true;
		}
		return false;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int count =0;
		int intItem;
		Scanner inFile = new Scanner(new FileReader(args[0]));
		
		while(inFile.hasNext()){
			intItem = inFile.nextInt();
			count++;
			System.out.print(intItem + " ");
		}
		inFile.close();
		System.out.println();
	
		OneDStack one = new OneDStack(count);
		
		inFile = new Scanner(new FileReader(args[0]));
		while(inFile.hasNext()){
			intItem = inFile.nextInt();
			one.push(intItem);

		}
		while(!one.isEmpty()){
			System.out.print(one.pop() + " ");
		}
		inFile.close();
	}

}
