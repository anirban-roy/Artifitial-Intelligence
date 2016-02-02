import java.util.LinkedList;


public class MinMax_Decision {

	Node state;
	LinkedList<Node> nodes;
	//private int alpha, beta;
	public MinMax_Decision(Node node) {
		state = node;
		nodes = new LinkedList<Node>();
		//alpha = Integer.MIN_VALUE;
		//beta = Integer.MAX_VALUE;
	}
	
	public String minmax_start()
	{
		String actions = "";
		int index = 0, maxutility = 999, i=0, j=0, count = 1;
		char move = '\0';
		if(this.state.isTerminal())
		{
			return ("***No next move. Game is already in terminal state");			
		}
		state.findChilds(state, 'X');
		nodes.addLast(state);
		while(index < state.getChildren().size())
		{
			min_value(state.getChildren().get(index), Integer.MIN_VALUE, Integer.MAX_VALUE); //, alpha, beta);
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
		if(actions.length()==0)
			actions = "No optimal moves exist.";
		else
		{
			actions = Character.toString(actions.charAt(0));
		}
		actions += ". Total Nodes: "+ nodes.size();
		return actions;
	}
	
	public int max_value(Node node, int alpha, int beta)
	{
		int index = 0, maxutility = 999;
		if(node.isTerminal())
		{
			return node.getUtility();
		}
		else
		{
			node.findChilds(node, 'X');
			while(index < node.getChildren().size())
			{
				maxutility = min_value(node.getChildren().get(index), alpha, beta);
				nodes.addLast(node.getChildren().get(index));
				if(alpha<maxutility)
					alpha = maxutility;
				
				if(beta<alpha)
					break;
				index++;
			}			
			node.setUtility(alpha);
			return alpha;
		}
	}
	
	public int min_value(Node node, int alpha, int beta)
	{
		int index = 0, minutility = 999;
		if(node.isTerminal())
		{
			return node.getUtility();
		}
		else
		{
			node.findChilds(node, 'O');
			while(index < node.getChildren().size())
			{
				minutility = max_value(node.getChildren().get(index), alpha, beta);
				nodes.addLast(node.getChildren().get(index));
				if(beta > minutility)
					beta = minutility;
				if(beta <= alpha)
					break;
				index++;
			}		
			node.setUtility(beta);
			return beta;
		}
	}
}
