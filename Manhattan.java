package Puzzle8;

import java.util.Comparator;

public class Manhattan implements Comparator<MultipleTypes> {

	@Override
		    public int compare(MultipleTypes x, MultipleTypes y)
		    {
		        // Assume neither string is null. Real code should
		        // probably be more robust
		        // You could also just return x.length() - y.length(),
		        // which would be more efficient.
		        if (x.moves < y.moves)
		        {
		            return -1;
		        }
		        if (x.moves > y.moves)
		        {
		            return 1;
		        }
		        return 0;
		    }
}
