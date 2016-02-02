import java.util.LinkedList;


public class MinMax_Decision {

	Node state;
	LinkedList<Node> nodes;
	public MinMax_Decision(Node node) {
		state = node;
		nodes = new LinkedList<Node>();
	}
	
	public String minmax_start()
	{
		String actions = "";
		int index = 0, maxutility = 999, i=0, j=0, count = 1;
		if(this.state.isTerminal())
		{
			return ("***No next move. Game is already in terminal state");			
		}
		state.findChilds(state, 'X');
		nodes.addLast(state);
		while(index < state.getChildren().size())
		{
			min_value(state.getChildren().get(index));
			nodes.addLast(state.getChildren().get(index));
			if(state.getChildren().get(index).getUtility() == 1)
				{
					count = 1;
					for(i=0;i<3;i++)
						for(j=0;j<3;j++)
						{
							if((i==state.getChildren().get(index).row) && (j==state.getChildren().get(index).col))
							{
								actions += count + " ";
							}
							count++;
						}
				}
			index++;
		}
		if(actions.length() == 0)
		{
			index = 0;
			while(index < state.getChildren().size())
			{
				if(state.getChildren().get(index).getUtility() == 0)
					{
						count = 1;
						for(i=0;i<3;i++)
							for(j=0;j<3;j++)
							{
								if((i==state.getChildren().get(index).row) && (j==state.getChildren().get(index).col))
								{
									actions += count + " ";
								}
								count++;
							}
					}
				index++;
			}
		}
		actions += ". Nodes:" + nodes.size();
		return actions;
	}
	
	public void max_value(Node node)
	{
		int index = 0, minutility = 999;
		if(node.isTerminal())
		{
			return;
		}
		else
		{
			node.findChilds(node, 'X');
			while(index < node.getChildren().size())
			{
				min_value(node.getChildren().get(index));
				nodes.addLast(node.getChildren().get(index));
				index++;
			}
			
			index = 1;
			minutility = node.getChildren().get(0).getUtility();
			while(index < node.getChildren().size())
			{
				if(minutility <= node.getChildren().get(index).getUtility())
					minutility = node.getChildren().get(index).getUtility();
				index++;
			}
			node.setUtility(minutility);
			return;
		}
	}
	
	public void min_value(Node node)
	{
		int index = 0, maxutility = 999;
		if(node.isTerminal())
		{
			return;
		}
		else
		{
			node.findChilds(node, 'O');
			while(index < node.getChildren().size())
			{
				max_value(node.getChildren().get(index));
				nodes.addLast(node.getChildren().get(index));
				index++;	
			}
			index = 1;
			maxutility = node.getChildren().get(0).getUtility();
			while(index < node.getChildren().size())
			{
				if(maxutility >= node.getChildren().get(index).getUtility())
				{
					maxutility = node.getChildren().get(index).getUtility();
				}
				index++;	
			}
			node.setUtility(maxutility);
			return;
		}
	}
}
