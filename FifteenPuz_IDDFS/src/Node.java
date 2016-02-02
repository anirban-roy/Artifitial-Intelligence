import java.util.*;

public class Node {
	public int state[][]; // = {{1,2,3,4},{5,6,7,8},{9,0,10,12},{13,14,11,15}};
	public int depth;
	public int blank_cell_x, blank_cell_y;
	
	Node() // constructor no args
	{
		state = new int[4][4];
		depth = 0;
		blank_cell_x = 0;
		blank_cell_y = 0;
	}
	
	Node (Node node, int p_depth) // copy constructor no args
	{
		int i, j;
		this.depth = p_depth;
		state = new int[4][4];
		for(i=0;i<4;i++)
		{
			for(j=0;j<4;j++)
				this.state[i][j] = node.state[i][j];
		}

		this.blank_cell_x = node.blank_cell_x;
		this.blank_cell_y = node.blank_cell_y;
	}
	
	public void findChilds(Node node, LinkedList<Node> stack, LinkedList<Node> visited_nodes)
	{
		Node child1, child2, child3, child4;
		
		if(node.blank_cell_x + 1 <= 3)
		{
			child1 =  new Node(node, node.depth+1);
			child1.state[child1.blank_cell_x][child1.blank_cell_y] = child1.state[child1.blank_cell_x+1][child1.blank_cell_y];
			child1.state[child1.blank_cell_x+1][child1.blank_cell_y] = 0;
			child1.blank_cell_x = child1.blank_cell_x + 1;
			if(!child1.check_visited(visited_nodes))
				stack.push(child1);
		}
		if(node.blank_cell_y + 1 <= 3)
		{
			child2 =  new Node(node, node.depth+1);
			child2.state[child2.blank_cell_x][child2.blank_cell_y] = child2.state[child2.blank_cell_x][child2.blank_cell_y+1];
			child2.state[child2.blank_cell_x][child2.blank_cell_y+1] = 0;
			child2.blank_cell_y = child2.blank_cell_y + 1;
			if(!child2.check_visited(visited_nodes))
				stack.push(child2);
		}
		if(node.blank_cell_x - 1 >= 0)
		{
			child3 =  new Node(node, node.depth+1);
			child3.state[child3.blank_cell_x][child3.blank_cell_y] = child3.state[child3.blank_cell_x-1][child3.blank_cell_y];
			child3.state[child3.blank_cell_x-1][child3.blank_cell_y] = 0;
			child3.blank_cell_x = child3.blank_cell_x - 1;
			if(!child3.check_visited(visited_nodes))
				stack.push(child3);
		}
		if(node.blank_cell_y - 1 >= 0)
		{
			child4 =  new Node(node, node.depth+1);
			child4.state[child4.blank_cell_x][child4.blank_cell_y] = child4.state[child4.blank_cell_x][child4.blank_cell_y-1];
			child4.state[child4.blank_cell_x][child4.blank_cell_y-1] = 0;
			child4.blank_cell_y = child4.blank_cell_y - 1;
			if(!child4.check_visited(visited_nodes))
				stack.push(child4);
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
		System.out.print("\n");
	}

}
