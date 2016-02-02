import java.util.*;

public class Node {
	public char state[][]; // = {{1,2,3,4},{5,6,7,8},{9,0,10,12},{13,14,11,15}};
	private Node parent;
	private int level, utility;
	public int row, col;
	char player;  // Indicates the type of player. X is assumed to be Max player.
	private ArrayList<Node> children;

	
	Node() // constructor no args
	{
		state = new char[3][3];
		parent = null;
		level = 0;
		utility = 999;
		player = 'X';
		row = 0;
		col = 0;
		children = new ArrayList<Node>();
	}
	
	Node (Node node, int row, int column, char player) // new child constructor
	{
		int i, j;
		state = new char[3][3];
		this.parent = node;
		this.row = row;
		this.col = column;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
				this.state[i][j] = node.state[i][j];
		}
		this.state[row][column] = player;
		this.level = node.level + 1;
		this.utility = 999;
		this.player = player;
		this.children = new ArrayList<Node>();
	}
	
	Node (Node node) // just copy constructor
	{
		int i, j;
		state = new char[3][3];
		this.parent = node.parent;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
				this.state[i][j] = node.state[i][j];
		}
		this.level = node.level;
		this.utility = node.utility;
		this.player = node.player;
		this.children = new ArrayList<Node>(node.children);
	}
	
	public void findChilds(Node parent, char player) //, LinkedList<Node> visited_nodes)
	{
		Node child = null;
		int i, j;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				if(parent.state[i][j] == 'B')
				{
					child = new Node(parent, i, j, player);
					parent.children.add(child);
				}
					
			}
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
	
	public boolean isTerminal()  // checks if the node is terminal node or not.
	{	
		int i=0, j=0;
		
		for(i=0;i<3;i++)
		{
			if((this.state[i][0] == 'X' && this.state[i][1] == 'X' && this.state[i][2] == 'X') || (this.state[0][i] == 'X' && this.state[1][i] == 'X' && this.state[2][i] == 'X'))
			{
				this.utility = 1;
				return true;
			}
			else if((this.state[i][0] == 'O' && this.state[i][1] == 'O' && this.state[i][2] == 'O') || (this.state[0][i] == 'O' && this.state[1][i] == 'O' && this.state[2][i] == 'O'))
			{
				this.utility = -1;
				return true;
			}
		}
		
		if((this.state[0][0] == 'X' && this.state[1][1] == 'X' && this.state[2][2] == 'X') || (this.state[0][2] == 'X' && this.state[1][1] == 'X' && this.state[2][0] == 'X'))
			{
				utility = 1;
				return true;
			}
		else if ((this.state[0][0] == 'O' && this.state[1][1] == 'O' && this.state[2][2] == 'O') || (this.state[0][2] == 'O' && this.state[1][1] == 'O' && this.state[2][0] == 'O'))
			{
				utility = -1;
				return true;
			}
		else 
	 block:{
				for(i=0;i<3;i++)
					for(j=0;j<3;j++)
					{
						if(this.state[i][j] != 'B')
							continue;
						else
							break block;
					}
				this.utility = 0;
				return true;
			}
		
		return false;
	}

	public int getUtility()
	{
		return this.utility;
	}
	
	public void setUtility(int util)
	{
		this.utility = util;
	}
	
	public ArrayList<Node> getChildren()
	{
		return this.children;
	}
	
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
}
