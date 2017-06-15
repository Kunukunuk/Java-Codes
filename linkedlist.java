import java.io.*;
import java.util.Scanner;

public class linkedlist {

	public static class listNode{
		int data;
		listNode next;
		
		listNode(){
			data = 0;
		}
		
		listNode(int value){
			data = value;
		}
	}
	
	public static class linkedlistStack{
		listNode top;
		linkedlistStack(){
			top = null;
		}
		
		void push(int value){
			listNode newnode = new listNode(value);
			newnode.next = top;
			System.out.println(newnode.data + " " + newnode.next);
			top = newnode;
		}
		
		int pop(){
			int temp = top.data;
			if(isEmpty()){
				return 0;
			}
			top = top.next;
			return temp;
		}
		
		boolean isEmpty(){
			if(top == null){
				return true;
			}
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		int intItem;
		Scanner inFile = new Scanner(new FileReader(args[0]));
		linkedlistStack lls = new linkedlistStack();
		System.out.println("Pushing in the values into the Stack:");
		while(inFile.hasNext()){
			intItem = inFile.nextInt();
			lls.push(intItem);
		}
		inFile.close();
		System.out.println("Poping out the list:");
		int finish = 1;
		while(finish != 0){
			System.out.println(lls.pop());
			if(lls.isEmpty()){
				finish = 0;
			}
		}
		
	}
}
