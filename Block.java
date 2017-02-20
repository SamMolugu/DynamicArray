//Capacity is arrayOfElements.length;
//Size is what ever is accessible out of capacity/length
public class Block<T> //array of elements
{
	protected final int number; // Block number, as in Block0, Block1
	protected final T[] arrayOfElements; // Holds actual elements

	// Note that it is not possible to use the code
	// T a[] = new T[size]; 
	// which leads to Java’s Generic Array Creation error on 
	// compilation. Consult the textbook for solution to surmount this 
	// minor problem

	// Number of elements that can be stored in this block;
	// this is equal to arrayOfElements.length
	protected final int capacity;

	// Number of spaces that have been allocated for storing elements;
	// initially 0. size <= capacity
	protected int size; // number of usable spots in block, not the same as block.length or capacity

	// Workhorse constructor. Initialize variables and create array.
	@SuppressWarnings("unchecked")
	public Block(int number, int capacity)
	{
		this.number = number; 
		this.capacity = capacity;
		this.size = 0;
		arrayOfElements = (T[])new Object[capacity]; // initilizes size of arrayofelements to capacity
	}

	// Returns Number
	public int getNumber()
	{
		return number;
	}

	// Returns capacity
	public int getCapacity()
	{
		return capacity;
	}

	// Returns size
	public int size()
	{
		return this.size;
	}

	// Increase the space allocated for storing elements. Increases 
	// size.
	public void grow()
	{
		size++;
	}

	// Set the last element to null and decrease the space allocated 
	// for storing elements. Decreases size.
	public void shrink()
	{
		arrayOfElements[size-1] = null; //turns last element into null, reduce size by 1
		size--; // NEED TO CHECK
	}

	// Returns the element at position index in arrayOfElements.
	public T getElement(int index)
	{
		return arrayOfElements[index]; 
	}

	// Sets the value at position i in arrayOfElements to x.
	public void setElement(int i, T x)
	{
		arrayOfElements[i] = x;
	}

	// Create a pretty representation of the Block.
	// Example: 
	// A    
	public String toString()
	{
		String block = ""; //initial string
		for (int i = 0; i < arrayOfElements.length; i++)
		{
			if (arrayOfElements[i] != null) // add on for each element
			{
				block = block.concat((String) arrayOfElements[i]);
			}
		}
		return block;
	}

	// Create a pretty representation of the Block for debugging.
	// Example: 
	// A
	// - capacity=1 size=1        
	protected String toStringForDebugging()
	{
		String block = ""; //initial 
		for (int i = 0; i < arrayOfElements.length; i++)
		{
			block = block.concat((String) arrayOfElements[i]); // add on after each element
		}
		return block + "\n" + "-capacity= " + capacity + "size= " + size;
	}

}
