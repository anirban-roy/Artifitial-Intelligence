import java.util.*;
import java.text.NumberFormat;

public class TicTacToe_MiMx {
	
	public static void main(String args[])
	{
		int i=0, j=0, ind=0, st=0, current_level=0;
		long freemem=0, totalmem=0, usedmemBefore = 0, usedmemAfter = 0, starttime=0, endtime=0;
		String result_moves = "", state = "";
		
		Scanner sc = new Scanner(System.in);
		Node node = new Node();
				
		System.out.print ("Enter the initial State:");
		state = sc.nextLine();
		state = state.toUpperCase();
		if(state.length()!=0)
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
					node.state[i][j] = state.charAt(ind);
					ind++;
			}
		}
		else
		{
			System.out.println("Invalid Input");
			System.exit(0);
		}
		
		
		MinMax_Decision mnmx = new MinMax_Decision(node);
		result_moves = mnmx.minmax_start();
		if(result_moves.length()==0)
			result_moves = "No optimal moves exit.";
		System.out.println("Possible good moves for X are: " + result_moves);
		
	} // end main()
}
