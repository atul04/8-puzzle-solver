package Puzzle8;

import java.util.*;

public class Solver {
	private Board initial ;
	public int totalMoves;
	Comparator<MultipleTypes> comp = new Manhattan();
	PriorityQueue<MultipleTypes> frontier = new PriorityQueue<MultipleTypes>(8,comp);
	
	HashMap<Board , Board> came_from = new HashMap<>();
	HashMap<Board,Integer> cost_so_far = new HashMap<>();
	Board goal = null;
	Queue<Board> solution = new LinkedList<Board>();
	String msg = "Solved";
	public Solver(Board b)
	{
		initial = b;
		if(initial.isSolvable()){
			System.out.println(msg);
			solution_Start();
			//solution_Table();
			//print_Solution();
			
		}
		else{
			msg = "Can't be solved";
			System.out.println(msg);
		}
	}
	public void print_Solution() {
		// TODO Auto-generated method stub
		Iterator<Board> it = solution.iterator();
		
		System.out.println("Total No. of Moves :"+cost_so_far.get(goal));
		
		while(it.hasNext())
		{
			it.next().print();
		}
	}
	public Queue<Board> solution_Table() {
		// TODO Auto-generated method stub
		Board curr = goal;
		
		Stack<Board> s = new Stack<Board>();
		
		while(curr!=null)
		{
			
			s.push(curr);
			curr = came_from.get(curr);
		}
		
		while(!s.isEmpty())
			solution.add(s.pop());
		totalMoves = cost_so_far.get(goal);
		return solution;
	}
	
	private void solution_Start() {
		// TODO Auto-generated method stub
		frontier.offer(new MultipleTypes(initial,initial.manhattan(0)));
		came_from.put(initial,null);
		cost_so_far.put(initial, 0);
		Board current;
		while(!frontier.isEmpty())
		{
			current = frontier.poll().b;
			//System.out.println("Current : ");
			//current.print();
			
			if(current.isGoal())
			{
				goal = current;
				//System.out.println("Goal is reached : ");
				//current.print();
				break;
			}
			
			Iterator<Board> it = current.neighbours().iterator();	// To get the neighbours list
			
			Board next ;
			
			while(it.hasNext())
			{
				
				//System.out.println("Next Neighbour :");
				next = it.next();
				//next.print();
				if(!next.isSolvable())
					continue;
				
				//next.print();
				//System.out.println(" moves ="+(cost_so_far.get(current)+1)+" Hamming ="+next.hamming(cost_so_far.get(current)+1)+" manhattan = "+next.manhattan(cost_so_far.get(current)+1));
				
				//if(next.hamming(cost_so_far.get(current)+1) > 24 || next.manhattan(cost_so_far.get(current)+1) > 30)
				//{
				//	continue;
				//}
					
				int new_cost = cost_so_far.get(current) + 1;
				Iterator<Board> iter = cost_so_far.keySet().iterator();
				boolean found = false;
				
				while(iter.hasNext() && !found)
				{
					//System.out.println("Inside the Found While");
				//	iter.next().print();
					Board dummy = iter.next();
					if(next.equals(dummy))
					{
						found = true;
						next = dummy;
						//System.out.println("Found for :");
						//next.print();
						//System.out.println("Value = "+cost_so_far.get(next));
					}
				}
				
				if(!found || new_cost < cost_so_far.get(next))
				{
					cost_so_far.put(next, new_cost);
					frontier.offer(new MultipleTypes(next,next.manhattan(cost_so_far.get(current)+1)));
					//System.out.println("Have been putten ");
					//next.print();
					if(frontier.size()>300)
						frontier = removeLast(frontier,frontier.size()-300);
					came_from.put(next, current);
				}
			}
		}
	}
	private PriorityQueue<MultipleTypes> removeLast(
			PriorityQueue<MultipleTypes> pq, int i) {
		// TODO Auto-generated method stub
		PriorityQueue<MultipleTypes> pqnew = new PriorityQueue<MultipleTypes>(8,comp);

	    while(pq.size() > i)
	    {
	        pqnew.offer(pq.poll());
	    }

	    pq.clear();
	    return pqnew;
	}
	
}
