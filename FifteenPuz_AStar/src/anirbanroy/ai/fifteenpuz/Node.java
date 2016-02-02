package anirbanroy.ai.fifteenpuz;
import java.util.*;
import java.util.Comparator;

class Node {
	public int state[][];// = {{5,1,3,4},{2,6,7,8},{9,10,12,0},{13,14,11,15}};
	private Node parent;
	private char direction;
	private int realCost;
	
	private int manhattanDist;
	private int misplacedTiles;
	public int blank_cell_x, blank_cell_y;

	
	Node() // constructor no args
	{
		state = new int[4][4];
		blank_cell_x = 0;
		blank_cell_y = 0;
		misplacedTiles = 0;
		manhattanDist = 0;
		setParent(null);
		direction = '\0';
		realCost = 0;
	}
	
	Node (Node node, char move) // copy constructor
	{
		int i, j;
		state = new int[4][4];
		this.setParent(node);
		for(i=0;i<4;i++)
		{
			for(j=0;j<4;j++)
				this.state[i][j] = node.state[i][j];
		}

		this.blank_cell_x = node.blank_cell_x;
		this.blank_cell_y = node.blank_cell_y;
		this.direction = move;
		misplacedTiles = 0;
		manhattanDist = 0;
		this.realCost = node.realCost + 1; // Real cost to reach a child from its immediate parent is taken to be 1
	}
	
	public void findChilds(Node node, Queue<Node> pQueue, LinkedList<Node> visited_nodes)
	{
		Node child1, child2, child3, child4;
		
		if(node.blank_cell_x + 1 <= 3)
		{
			child1 =  new Node(node, 'D');
			child1.state[child1.blank_cell_x][child1.blank_cell_y] = child1.state[child1.blank_cell_x+1][child1.blank_cell_y];
			child1.state[child1.blank_cell_x+1][child1.blank_cell_y] = 0;
			child1.blank_cell_x = child1.blank_cell_x + 1;
			child1.setParent(node);
			if(!child1.check_visited(visited_nodes))
				pQueue.add(child1);
		}
		if(node.blank_cell_y + 1 <= 3)
		{
			child2 =  new Node(node, 'R');
			child2.state[child2.blank_cell_x][child2.blank_cell_y] = child2.state[child2.blank_cell_x][child2.blank_cell_y+1];
			child2.state[child2.blank_cell_x][child2.blank_cell_y+1] = 0;
			child2.blank_cell_y = child2.blank_cell_y + 1;
			child2.setParent(node);
			if(!child2.check_visited(visited_nodes))
				pQueue.add(child2);
		}
		if(node.blank_cell_x - 1 >= 0)
		{
			child3 =  new Node(node, 'U');
			child3.state[child3.blank_cell_x][child3.blank_cell_y] = child3.state[child3.blank_cell_x-1][child3.blank_cell_y];
			child3.state[child3.blank_cell_x-1][child3.blank_cell_y] = 0;
			child3.blank_cell_x = child3.blank_cell_x - 1;
			child3.setParent(node);
			if(!child3.check_visited(visited_nodes))
				pQueue.add(child3);
		}
		if(node.blank_cell_y - 1 >= 0)
		{
			child4 =  new Node(node, 'L');
			child4.state[child4.blank_cell_x][child4.blank_cell_y] = child4.state[child4.blank_cell_x][child4.blank_cell_y-1];
			child4.state[child4.blank_cell_x][child4.blank_cell_y-1] = 0;
			child4.blank_cell_y = child4.blank_cell_y - 1;
			child4.setParent(node);
			if(!child4.check_visited(visited_nodes))
				pQueue.add(child4);
		}
	} // end of findChilds()
	
	public boolean check_visited(LinkedList<Node> visited_nodes) // Checks if current state is already visited before
	{
		int i, j;
		Node node = null;
		int count = 0;
		boolean match = false;
		while(count<visited_nodes.size())
		{
			node = visited_nodes.get(count);
			start: for(i=0;i<4;i++)
					{
						for(j=0;j<4;j++)
						{
							if(this.state[i][j] == node.state[i][j])
								match = true;
							else
								{
									match = false;
									break start;
								}
						}
					}
			if(match == false)
				count++;
			else
				return true;
		}
		return false;
	}
	public boolean checkGoal()
	{	
		int i, j;
		String goal = " ";
		for(i=0;i<4;i++)
			for(j=0;j<4;j++)
			 goal = goal + this.state[i][j];
		
		if(goal.equals(" 1234567891011121314150"))
			return true;
		else 
			return false;
	}

	public int findNoMsplcdTiles() // Heuristic 1
	{
		int i = 0, j=0, misplacedNo = 0;
		int goal[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		for(i=0;i<4;i++)
		{
			for(j=0;j<4;j++)
			{
				if(this.state[i][j] == goal[i][j])
					continue;
				else
					misplacedNo++;
				
			}
		}
		
		this.misplacedTiles = misplacedNo;
		return misplacedNo;
	}
	
	public int findManhattanDistance() // Heuristic 2
	{
		int i = 0, j=0, manX=0, manY = 0;
		int totalManDis = 0;
		// the goal state of the puzzle to attain
		int goal[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		for(i=0;i<4;i++)
		{
			for(j=0;j<4;j++)
			{
				if(this.state[i][j] != 0)  // Ignore the position of 0
				{
					manX = (state[i][j]-1)/4;
					manY = (state[i][j]-1)%4;
					totalManDis += Math.abs(i - manX) + Math.abs(j - manY); 
				}
			}
		}
		this.manhattanDist = totalManDis;
		return totalManDis;
	}
	
	// check if the state is solvable
	public boolean isSolvable(){
		// find the no. of inversions; when a tile with greater value occurs before another tile in the state, we say that as an inversions. 
		// to check the solvability, the number of inversions needs to be calculated
		int current = 0;
		int noOfInversions = 0;
		for(int i=0; i<=3;i++){
			for(int j=0; j<=3; j++){
				for(int k=current; i<=15; k++){
					
				}
				current++;
			}
		}
		
		return true;
	}
	
	// show the puzzle state to console
	public void printState()
	{
		int i, j;
		for(i=0;i<4;i++)
		 {
			for(j=0;j<4;j++)
			{
				System.out.print(this.state[i][j]+" ");
			}
		 }
	}
	
	
	public int getRealCost()
	{
		return this.realCost;
	}
	
	public void setDirection(int cost)
	{
		this.realCost = cost;
	}

	public char getDirection()
	{
		return this.direction;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}


class NodeComHeu2 implements Comparator<Node> {

	public int compare(Node node1, Node node2) {
		// TODO Auto-generated method stub
		return ((node1.findManhattanDistance()+node1.getRealCost()) - (node2.findManhattanDistance()+node2.getRealCost()));
	}

}

class NodeCompHeu1 implements Comparator<Node> {
	
	public int compare(Node node1, Node node2) {
	
		return ((node1.findNoMsplcdTiles()+node1.getRealCost()) - (node2.findNoMsplcdTiles()+node2.getRealCost()));
		
	}

}
