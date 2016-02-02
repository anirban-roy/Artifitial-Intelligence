

package anirbanroy.ai.fifteenpuz;
import java.util.*;
import java.text.NumberFormat;
/**
 * @author Anirban Roy
 *
 */
public class FifteenPuz_AStar {
	
	public static void main(String args[])
	{
		int i=0, j=0, st=0, current_level=0;
		long freemem=0, totalmem=0, usedmemBefore = 0, usedmemAfter = 0, starttime=0, endtime=0;
		String result_moves = "";
		
		Scanner sc = new Scanner(System.in);
		Runtime runtime = Runtime.getRuntime();
		NumberFormat format = NumberFormat.getInstance();
	      
		Node node = new Node();
		Node temp_node = null;
		
		Comparator compH1 = new NodeCompHeu1();
		Comparator compH2 = new NodeComHeu2();
		Queue<Node> pQueue1 = new PriorityQueue(compH1); // Queue 1 for Heuristic 1
		Queue<Node> pQueue2 = new PriorityQueue<Node>(compH2);
		
		LinkedList<Node> visited_nodes = new LinkedList<Node>(); // keeps track of already visited nodes
		
		System.out.println (":::: Enter the initial State::::");
		
		for(i=0;i<4;i++)
		{
			for(j=0;j<4;j++)
			{
					System.out.print("Enter value in position [" + (i+1)+"][" +(j+1)+"]:");
					node.state[i][j] = sc.nextInt();
					if(node.state[i][j] == 0)
						{
							node.blank_cell_x = i;
							node.blank_cell_y = j;
						}
			}
		}
		
		node.findNoMsplcdTiles();
		
		// Heuristic 1: Using no. of misplaced tiles.
		/*System.gc();
		freemem = runtime.freeMemory();
		totalmem = runtime.totalMemory();
		usedmemBefore = totalmem-freemem;
		
		starttime = System.currentTimeMillis();*/
		
		pQueue1.add(node);
		
		while(!pQueue1.isEmpty())
		{
			temp_node = pQueue1.poll();
			//if(temp_node.getDirection() !='\0')
				//result_moves += temp_node.getDirection();
			
			visited_nodes.addLast(node);
			if(temp_node.checkGoal())
			{
				System.out.println("\n\nUsing Number of misplaced tiles Heuristic");
				System.out.println("==========================================================================");
				System.out.println("	Board				|  Number of Moves  |	Solution	");
				System.out.println("==========================================================================");
				node.printState();
				while(temp_node.getParent()!=null)
				{
					result_moves += temp_node.getDirection();
					temp_node = temp_node.getParent();
				}
				result_moves = new StringBuilder(result_moves).reverse().toString();
				System.out.print("\t\t"+(result_moves.length()) + "\t\t"+result_moves); 
				
				/*endtime = System.currentTimeMillis();
				System.gc();
				runtime = Runtime.getRuntime();
				freemem = runtime.freeMemory();
				totalmem = runtime.totalMemory();
				usedmemAfter = totalmem - freemem;
				System.out.println("\n\nMemeroy Used = " + Math.abs(usedmemAfter-usedmemBefore));
				System.out.println("Running Time = " + Math.abs(starttime - endtime) + " Milliseconds");
				System.out.println("Number of nodes expanded = " + (visited_nodes.size() - 1));*/
				break;
			}
			else
				temp_node.findChilds(temp_node, pQueue1, visited_nodes);
		}
		
		// Heuristic 2 Flow (Using Manhattan Distance)
		result_moves = "";
		visited_nodes.clear();
		pQueue1.clear();
		/*System.gc();
		freemem = runtime.freeMemory();
		totalmem = runtime.totalMemory();
		usedmemBefore = totalmem-freemem;
		starttime = System.currentTimeMillis();*/
		
		pQueue2.add(node);
		
		while(!pQueue2.isEmpty())
		{
			temp_node = pQueue2.poll();
			//if(temp_node.getDirection() !='\0')
			//	result_moves += temp_node.getDirection();
			
			visited_nodes.addLast(node);
			if(temp_node.checkGoal())
			{
				System.out.println("\n\n\nUsing Sum of Manhattan Distances Heuristic");
				System.out.println("==========================================================================");
				System.out.println("	Board				|  Number of Moves  |	Solution	");
				System.out.println("==========================================================================");
				node.printState();
				while(temp_node.getParent()!=null)
				{
					result_moves += temp_node.getDirection();
					temp_node = temp_node.getParent();
				}
				result_moves = new StringBuilder(result_moves).reverse().toString();
				System.out.print("\t\t"+(result_moves.length()) + "\t\t"+result_moves); 
				
				/*endtime = System.currentTimeMillis();
				System.gc();
				//runtime = Runtime.getRuntime();
				freemem = runtime.freeMemory();
				totalmem = runtime.totalMemory();
				usedmemAfter = totalmem - freemem;
				System.out.println("\n\nMemeroy Used = " + Math.abs(usedmemAfter-usedmemBefore));
				System.out.println("Running Time = " + Math.abs(starttime - endtime) + " Milliseconds");
				System.out.println("Number of nodes expanded = " + (visited_nodes.size() - 1));*/
				System.exit(0);
			}
			else
				temp_node.findChilds(temp_node, pQueue2, visited_nodes);
		}
		
		System.out.println("*** GOAL COULD NOT BE FOUND *** " );
	} // end main()
}
