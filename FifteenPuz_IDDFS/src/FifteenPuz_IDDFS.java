import java.util.*;

public class FifteenPuz_IDDFS {
	
	public static void main(String args[])
	{
		int i=0, j=0, st=0, depth=0, current_level=0;
		long freemem=0, totalmem=0, usedmemBefore = 0, usedmemAfter = 0, starttime=0, endtime=0;
		Scanner sc = new Scanner(System.in);
		Runtime runtime = Runtime.getRuntime();
		
		Node node = new Node();
		Node temp_node = null;
		LinkedList<Node> stack = new LinkedList<Node>(); // Stack to implement BFS
		LinkedList<Node> visited_nodes = new LinkedList<Node>(); // keeps track of already visited nodes
		LinkedList<Node> new_iteration = new LinkedList<Node>(); // Keeps track of a new iteration of IDDFS
		
		System.out.print("Enter the maximum depth for search:");
		depth = sc.nextInt();
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
				
		System.gc();
		freemem = runtime.freeMemory();
		totalmem = runtime.totalMemory();
		usedmemBefore = totalmem-freemem;
		
		starttime = System.currentTimeMillis();		
		
		stack.push(node);
		
		while(!stack.isEmpty())
		{
			temp_node = stack.pop();
			visited_nodes.addFirst(node);
			if(temp_node.checkGoal())
			{
				System.out.println("======================");
				System.out.println("Goal found at Level:"+temp_node.depth);
				System.out.println("======================");
				temp_node.printState();
				
				endtime = System.currentTimeMillis();
				System.gc();
				runtime = Runtime.getRuntime();
				freemem = runtime.freeMemory();
				totalmem = runtime.totalMemory();
				usedmemAfter = totalmem - freemem;
				
				System.out.println("\n\nMemeroy Used = " + Math.abs(usedmemAfter-usedmemBefore));
				System.out.println("Running Time = " + Math.abs(starttime - endtime) + " Milliseconds");
				System.out.println("Number of nodes expanded = " + (visited_nodes.size() - 1));
				System.exit(0);
			}
			else
			{
				if(temp_node.depth < depth)
				{
					System.out.println ("======================");
					System.out.println("State at Level:"+temp_node.depth);
					System.out.println("======================");
					temp_node.printState();
					temp_node.findChilds(temp_node, stack, visited_nodes);
				}
				else 
					continue;
			}
			//st++;
		}
		System.out.println("*** GOAL COULD NOT BE FOUND UNDER SEARCH DEPTH " + depth);
	} // end main()
}
