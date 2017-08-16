package Puzzle8;

import java.util.*;

import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String []args)
	{
		/*Scanner in = new Scanner(System.in);
		
		System.out.println("Start Entering the puzzle");
		int [][] a = new int[3][3];
		for(int i = 0 ; i < 3 ; i++)
		{
			for(int j = 0 ; j < 3 ; j++)
			{
				System.out.println("a["+(i+1)+"]["+(j+1)+"] : ");
				a[i][j] = in.nextInt();
			}
			
			System.out.println();
		}
		
		*/
		//new Solver(new Board(a,3));
		new GameBoard();

	}
	
	
}
