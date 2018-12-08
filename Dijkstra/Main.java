import java.io.*;
import java.util.Scanner;

public class Main {

	public static class DijkstraSSS{
		
		int numNodes, sourceNode, minNode, currentNode, newCost;
		int[][] costMatrix;
		int[] fatherAry, markedAry, bestCostAry;
		
		public void loadCostMatrix(Scanner infile, String input){
			int fromNode, toNode, cost;
			while(infile.hasNext()){
				fromNode = infile.nextInt();
				toNode = infile.nextInt();
				cost = infile.nextInt();
				for(int i =1; i<= numNodes; i++){
					for(int j =1; j<= numNodes; j++){
						if( i == fromNode && j == toNode){
							costMatrix[i][j] = cost;
						}
					}
				}
			}
			
		}
		
		public void loadBestCostAry(int sourceNode){
			for(int i =1; i<= numNodes; i++){
				bestCostAry[i] = costMatrix[sourceNode][i];
			}
		}
		
		public void loadFatherAry(int sourceNode){
			for(int i=1; i<= numNodes; i++){
				fatherAry[i] = sourceNode;
			}
		}
		
		public void loadMarkedAry(){
			for(int i = 1; i<= numNodes; i++){
				markedAry[i] = 0;
			}
		}
		
		public void markMinNode(int min){
			markedAry[min] = min;
		}
		public void changeFather(int node, int minNode){
			fatherAry[node] = minNode;
		}
		
		public void changeCost(int node, int newCost){
			bestCostAry[node] = newCost;
		}
		
		public String debugPrint(){
			String line = "";
			line = line + "The source node is: " + sourceNode + "\r\n";
			line = line + "The father array is :";
			for(int i =1; i<= numNodes; i++){
				line = line + fatherAry[i] + ", ";
			}
			line = line + "\r\n";
			line = line + "The bestCostAry is: ";
			for(int i =1; i <= numNodes; i++){
				line = line + bestCostAry[i] + ", ";
			}
			line = line + "\r\n";
			line = line + "The markedAry is: ";
			for(int i =1; i<= numNodes; i++){
				line = line + markedAry[i] + ", ";
			}
			line = line + "\r\n";
			return line;
		}
		
		public String printShortestPath(int source){
			String line = "";
			line = "======================================================== \r\n";
			line = line + "Source node = " + source + " \r\n";
			line = line + "The shortest paths from node " + source + " are:\r\n";
			for(int i =1 ; i<= numNodes; i++){
				line = line + "The path from " + source + " to " + i + " : ";
				line = line + source + "--> " + i + " : cost = " + bestCostAry[i] + " \r\n";
			}
			return line;
		}
		
		boolean allMarked(){
			for(int i = 1; i<= numNodes; i++){
				if(markedAry[i] == 0){
					return false;
				}
			}
			return true;
		}
		
		public int computeCost(int min, int current){
			int newcost;
			newcost = bestCostAry[min] + costMatrix[min][current];
			return newcost;
		}
		
		DijkstraSSS(String input, String output1, String output2) throws IOException{
			Scanner inFile = new Scanner(new FileReader(input));
			PrintWriter outfile = new PrintWriter (new FileWriter(output1));
			PrintWriter outfile1 = new PrintWriter (new FileWriter(output2));
			numNodes = inFile.nextInt();
			costMatrix = new int[numNodes+1][numNodes+1];
			for(int i =1; i<= numNodes; i++){
				for(int j =1 ; j<= numNodes; j++){
					if(i == j){
						costMatrix[i][j] = 0;
					}else{
						costMatrix[i][j] = 99999;
					}
				}
			}
			
			fatherAry = new int[numNodes +1];
			markedAry = new int [numNodes +1];
			bestCostAry = new int [numNodes +1];
			for(int i =1; i<= numNodes; i++){
				fatherAry[i] = i;
				markedAry[i] = 0;
				bestCostAry[i] = 9999;
			}
			
			loadCostMatrix(inFile, input);
			inFile.close();
			sourceNode = 1;
			outfile.println("There are " + numNodes + " nodes in the input graph");
			while(sourceNode <= numNodes){
				loadBestCostAry(sourceNode);
				loadFatherAry(sourceNode);
				loadMarkedAry();
				
				while(!allMarked()){
					int min = 999999;
					for(int i =1; i <= numNodes ; i++){
						if(bestCostAry[i] < min && markedAry[i] == 0){
							min = bestCostAry[i];
							minNode = i;
						}
					}
					markMinNode(minNode);
					outfile1.println("Debug for " + minNode);
					outfile1.println(debugPrint());
					int tempNode =1;
					while(tempNode <= numNodes){
						if(markedAry[tempNode] == 0){
							currentNode = tempNode;
							newCost = computeCost(minNode, currentNode);
							if(newCost < bestCostAry[currentNode]){
								changeFather(currentNode, minNode);
								changeCost(currentNode, newCost);
								outfile1.println(debugPrint());
							}
						}
						tempNode++;
					}
					
				}
				currentNode = 1;
				while(currentNode <= numNodes){
					if(currentNode == sourceNode){
						outfile.println(printShortestPath(sourceNode));
					}
					currentNode++;
				}
				sourceNode++;
			}
			outfile.close();
			outfile1.close();
		}
	}
	
	public static void main(String[] args) throws IOException{
		DijkstraSSS dijk = new DijkstraSSS(args[0], args[1], args[2]);
	}
}
