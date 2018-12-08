import java.io.*;
import java.util.Scanner;
public class Main {

	public static class listBinTreeNode{
		public String chStr;
		public int prob;
		
		listBinTreeNode next = null;
		listBinTreeNode left = null;
		listBinTreeNode right = null;
		
		listBinTreeNode(int pro, String chac){
			prob = pro;
			chStr = chac;
		}
		
		listBinTreeNode(){
			prob = 0;
			chStr = "dummy";
		}
		
		public String printNode(listBinTreeNode T){
			String node= "";
			if(T.next == null){
				node = node + T.chStr + ", " + T.prob + ", " + "NULL, " + T.left.chStr + ", " + T.right.chStr + "\n";
			}
			else if(T.left == null || T.right == null){
				node = node + T.chStr + ", " + T.prob + ", " + T.next.chStr + ", NULL, NULL \n";
			}
			else{
				node = node + T.chStr + ", " + T.prob + ", " + T.next.chStr + " , " + T.left.chStr + "," + T.right.chStr + "\n";
			}
			return node;
		}
	}
	
	public static class HuffmanLinkedList{
		public listBinTreeNode listHead;
		public listBinTreeNode oldlistHead;
		
		HuffmanLinkedList(){
			listBinTreeNode dummy = new listBinTreeNode();
			dummy.next = null;
			listHead = dummy;
		}
		
		public void listInsert(listBinTreeNode T){
			listBinTreeNode temp = findSpot(T.prob);
			T.next = temp.next;
			temp.next = T;
		}
		
		listBinTreeNode findSpot(int number){
			listBinTreeNode spot = listHead;
			while(spot.next != null && spot.next.prob < number){
				spot = spot.next;
			}
			return spot;
		}
		public String printList(){
			String list = "";
			if(isEmpty()){
				list = "listHead -->(" + listHead.chStr + ", " + listHead.prob + ", NULL ) --> NULL\n";
			}
			else{
				list = "listHead -->(" + listHead.chStr + ", " + listHead.prob + ", " + listHead.next.chStr + ")-->";
				listBinTreeNode current = listHead;
				while(current.next.next != null){
					list = list + "(" + current.next.chStr + ", " + current.next.prob + ", " + current.next.next.chStr + ")-->";
					current = current.next;
				}
				list = list + "(" + current.next.chStr + ", " + current.next.prob + ", NULL)-->NULL\n";
			}
			return list;
		}
		boolean isEmpty(){
			if(listHead.next == null){
				return true;
			}
			return false;
		}
	}
	
	public static class HuffmanBinaryTree{
		
		String code = "";
		listBinTreeNode root;
		
		public void preOrderTraversal(listBinTreeNode T, PrintWriter outfile){
			if(T == null){
				return;
			}
			else{
				outfile.print(T.printNode(T));
				preOrderTraversal(T.left, outfile);
				preOrderTraversal(T.right, outfile);
				
			}
		}
		
		HuffmanBinaryTree(listBinTreeNode T, PrintWriter outfile1, PrintWriter outfile2){
			root = T;
			constructCharCode(root, code, outfile1);
			preOrderTraversal(root, outfile2);
		}
		
		public void constructCharCode(listBinTreeNode T, String code, PrintWriter outfile){
			if(T == null){
				return;
			}
			else if( T.left == null || T.right == null){
				outfile.println(T.chStr + " " + code);
			}
			else{
				constructCharCode(T.left, code+"0", outfile);
				constructCharCode(T.right, code+"1", outfile);
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		Scanner inFile = new Scanner(new FileReader(args[0])); 
		PrintWriter outfile1 = new PrintWriter(new FileWriter(args[1]));
		PrintWriter outfile2 = new PrintWriter(new FileWriter(args[2]));
		PrintWriter outfile3 = new PrintWriter(new FileWriter(args[3]));
		int data;
		String character;
		HuffmanLinkedList list = new HuffmanLinkedList();
		while(inFile.hasNext()){
			
			character = inFile.next();
			data = inFile.nextInt();
			listBinTreeNode newNode = new listBinTreeNode(data, character);
			list.listInsert(newNode);
			outfile3.print(list.printList());
		}
		inFile.close();
		outfile3.println("End of construction of ordered linked list");
		outfile3.println("CurrentchStr, currentprob, nextchStr, leftchStr, rightchStr");
		
		list.oldlistHead = new listBinTreeNode();
		list.oldlistHead.next = list.listHead.next;
		list.listHead = list.listHead.next;
		int sumprob;
		String concatchar;
		while(!list.isEmpty()){
			sumprob = list.listHead.prob + list.listHead.next.prob;
			concatchar = list.listHead.chStr + list.listHead.next.chStr;
			listBinTreeNode newNode = new listBinTreeNode(sumprob, concatchar);
			newNode.left = list.listHead;
			newNode.right = list.listHead.next;
			list.listInsert(newNode);
			list.listHead = list.listHead.next.next;
			
			outfile3.print(list.listHead.printNode(newNode));
			outfile3.print(list.printList());
			
		}
		outfile2.println("currentchStr, currentprob, nextchStr, leftchStr, rightchStr");
		HuffmanBinaryTree tree = new HuffmanBinaryTree(list.listHead, outfile1, outfile2);
		outfile1.close();
		outfile2.close();
		outfile3.close();
	}
}
