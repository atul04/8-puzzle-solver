package Puzzle8;

import java.util.*;

public class Board {
	public int [][] b;
	private int size ;
	public Board(int [][]cells, int size)
	{
		b = cells.clone();
		this.size = size;
	}
	
	public void print()
	{
		for(int i = 0 ; i < size ; i++)
		{
			for(int j = 0 ; j < size ; j++)
				System.out.print(b[i][j]+" ");
			System.out.println();
			
		}
		System.out.println();
	}
	
	public int hamming(int moves)
	{
		int sum = 0;
		for(int i = 0 ; i < size ; i++)
			for(int j = 0 ; j < size ; j++)
			{
				if(b[i][j] == 0)
					continue;
				else if(b[i][j] != (i*size + (j+1)))
					sum+=1;
			}
		//System.out.println("W P "+sum);
		
		return(sum+moves);
	}
	
	public int manhattan(int moves)
	{
		int sum = 0;
		int move = 0;
		for(int i = 0 ; i < size ; i++)
			for(int j = 0 ; j < size ; j++)
			{
				if(b[i][j] == 0)
					continue;
				else
				{	
					int row = (int)Math.ceil(b[i][j]*1.0/size)-1;
					int col = b[i][j] - row*size - 1;
					
					move = Math.abs(i - row)+Math.abs(j-col);
					sum+=move;
						
				}
			}
		//System.out.println("T P "+sum);
		
		return (sum + moves);
	}
	
	public boolean isGoal()
	{
		boolean check = true;
		
		for(int i = 0  ; i < size ; i++ ){
			for(int j = 0 ; j < size ; j++)
			{
				if(b[i][j] == 0)
					continue;
				if(b[i][j] != (i*size + (j+1)) )
				{
					check = false;
					break;
				}
			}
			if(check == false)
				break;
		}
		return check;
	}
	
	public boolean isSolvable()
	{
		int inversions = 0;
		for(int i = 0 ; i < size ; i++)
			for(int j = 0 ; j < size ; j++)
			{
				if(b[i][j] == 0 || (i == size-1 && j == size -1))
					continue;
				else
					for(int k = 0 ; k < size ; k++)
						for(int l = 0 ; l < size ; l++)
						{
							if(k < i)
								continue;
							else if(k == i && l <= j)
								continue;
								
							else
							{
								if(b[i][j] > b[k][l] && b[k][l] != 0){
									inversions+=1;
									//System.out.println("b["+i+"]["+j+"] = "+b[i][j]+" b["+k+"]["+l+"] = "+b[k][l]);
								}
							}
						}
			}
		
		//System.out.print("Inversions : "+inversions);
	
		if(inversions%2==1)
			return false;
		else
			return true;
	}
	
	
	
	public boolean equals(Board y)
	{
		 boolean check = true;
		 
		 for(int i = 0 ; i < size ; i++){
			 for(int j = 0 ; j < size ; j++)
			 {
				 if(b[i][j] != y.b[i][j])
				 {
					 check = false;
					 break;
				 }
					 
			 }
			 if(check == false)
				 break;
		 }
		 return check;
	}
	
	public Set<Board> neighbours()
	{
		Set<Board> neighbour  = new LinkedHashSet<Board>();
		int[][] n = new int[size][size];
		
		int i=0,j=0;
		boolean found = false;
		for(i = 0 ; i < size ; i++)
		{
			for(j = 0 ; j < size ; j++){
				if(b[i][j] == 0){
					
					found = true;
					break;
				}
			}
			if(found)
				break;
		}
		
		
		
		if(i+1<size)
		{
			for(int k = 0 ; k < size ; k++)
				for(int l = 0 ; l < size ; l++)
				{
					n[k][l] = b[k][l];
				}
			
			n[i][j] = n[i][j]^n[i+1][j];
			n[i+1][j] = n[i][j]^n[i+1][j];
			n[i][j] = n[i][j]^n[i+1][j];
			//swap............
			int x[][] = new int[size][size];
			for(int y = 0 ; y < size ; y++)
				for(int z = 0 ; z < size ; z++)
					x[y][z] = n[y][z];
			Board b1 = new Board(x,size);
			//b1.print();
			neighbour.add(b1);
		}

		

		if(j-1>=0)
		{
			for(int k = 0 ; k < size ; k++)
				for(int l = 0 ; l < size ; l++)
				{
					n[k][l] = b[k][l];
				}
			n[i][j] = n[i][j]^n[i][j-1];
			n[i][j-1] = n[i][j]^n[i][j-1];
			n[i][j] = n[i][j]^n[i][j-1];
			//swap............
			int x[][] = new int[size][size];
			for(int y = 0 ; y < size ; y++)
				for(int z = 0 ; z < size ; z++)
					x[y][z] = n[y][z];
			Board b1 = new Board(x,size);
			//b1.print();
			neighbour.add(b1);

		}
		
		
		if(i-1>=0)
		{
			for(int k = 0 ; k < size ; k++)
				for(int l = 0 ; l < size ; l++)
				{
					n[k][l] = b[k][l];
				}
			n[i][j] = n[i][j]^n[i-1][j];
			n[i-1][j] = n[i][j]^n[i-1][j];
			n[i][j] = n[i][j]^n[i-1][j];
			//swap............
			int x[][] = new int[size][size];
			for(int y = 0 ; y < size ; y++)
				for(int z = 0 ; z < size ; z++)
					x[y][z] = n[y][z];
			Board b1 = new Board(x,size);
			//b1.print();
			neighbour.add(b1);
		}
		
		

		if(j+1<size)
		{
			for(int k = 0 ; k < size ; k++)
				for(int l = 0 ; l < size ; l++)
				{
					n[k][l] = b[k][l];
				}
			n[i][j] = n[i][j]^n[i][j+1];
			n[i][j+1] = n[i][j]^n[i][j+1];
			n[i][j] = n[i][j]^n[i][j+1];
			//swap............
			int x[][] = new int[size][size];
			for(int y = 0 ; y < size ; y++)
				for(int z = 0 ; z < size ; z++)
					x[y][z] = n[y][z];
			Board b1 = new Board(x,size);
			//b1.print();
			neighbour.add(b1);
		}
		
		
		
		return neighbour;
		
	}
}

