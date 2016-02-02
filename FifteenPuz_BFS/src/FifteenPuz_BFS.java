// Developed by Anirban Roy

import java.util.*;

public class FifteenPuz_BFS {
	
	public static void main(String args[])
	{
		int i=0, j=0, st=0;
		long freemem=0, totalmem=0, usedmemBefore = 0, usedmemAfter = 0, starttime=0, endtime=0;
		Scanner sc = new Scanner(System.in);
		Runtime runtime = Runtime.getRuntime();
		
		Node node = new Node();
		Node temp_node = null;
		LinkedList<Node> queue = new LinkedList<Node>(); // Queue to implement BFS
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
		
		System.gc();
		freemem = runtime.freeMemory();
		totalmem = runtime.totalMemory();
		usedmemBefore = totalmem-freemem;
		
		starttime = System.currentTimeMillis();
		queue.addFirst(node);
		
		while(!queue.isEmpty())
		{
			temp_node = queue.removeFirst();
			visited_nodes.addFirst(node);
			if(temp_node.checkGoal())
			{
				System.out.println ("=============");
				System.out.println("State:"+st);
				System.out.println("=============");
				temp_node.printState();
				System.out.println ("\nGoal Achieved through above states!!");
				
				endtime = System.currentTimeMillis();
				System.gc();
				//runtime = Runtime.getRuntime();
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
				System.out.println ("=============");
				System.out.println("State:"+st);
				System.out.println("=============");
				temp_node.printState();
				temp_node.findChilds(temp_node, queue, visited_nodes);
			}
			st++;
		}
	} // end main()

}
