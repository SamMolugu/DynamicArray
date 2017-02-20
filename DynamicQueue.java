import java.util.NoSuchElementException;

public class DynamicQueue<T> //creates two dynamic arrays called rear and front
//to implement a queue
{
	//QUEUE = FIRST IN - FIRST OUT 
	protected DynamicArray<T> front;  // These fields may be renamed
	protected DynamicArray<T> rear;   // The methods getFront() and getRear() return them

	// Workhorse constructor. Initialize variables.
	public DynamicQueue()
	{
		front = new DynamicArray<T>(); // set to dynamic array
		rear = new DynamicArray<T>(); // set to dynamic array
	}

	// Adds x to the rear of the queue
	// Target complexity: O(1)
	public void enqueue(T x)
	{
		rear.add(x); // simple add into rear
	}

	// Removes and returns the element at the front of the queue
	// Throws NoSuchElementException if this queue is empty.
	// Target complexity: O(n)
	public T dequeue()
	{
		if (isEmpty()) // if nothing in either dynamic array
			throw new NoSuchElementException(); // catch error
		if (front.size == 0) // if front is empty
		{
			for (int i = rear.size -1; i >= 0;i--) // start from end of rear
			{
				front.add(rear.get(i)); // add end of rear to front dynamicarray
				rear.remove(); // remove that item from rear
			}
		}
		T x = front.get(front.size -1); // this is if front was not empty
		front.remove(); // remove item from front
		return x; //return it
	}

	// Returns true if the queue is empty
	public boolean isEmpty()
	{
		if (front.size == 0 && rear.size == 0) // check if both dynamic arrays are empty
			return true; // if they are
		else
			return false; // if either or contain elements

	}

	// Returns the size of the queue
	public int size()
	{
		return front.size() + rear.size(); // combined size of both front and rear

	}

	// Return the “front” dynamic array of outgoing elements for final testing
	// Target complexity: O(1)
	protected DynamicArray<T> getFront()
	{
		return this.front; //returns front DA
	}

	// Return the “rear” dynamic array of incoming elements for final testing
	// Target complexity: O(1)
	protected DynamicArray<T> getRear()
	{
		return this.rear; // return rear DA
	}

	// Create a pretty representation of the DynamicQueue.
	// Example:
	// A B C D
	public String toString()
	{
		String toS = "["; // opening brackets
		for (int i = 0; i < front.size; i++) // this gets all elements from front
		{
			toS += (String) front.get(i); // capture that element as string
			if (i != front.size -1) 
				toS += ", "; // for formatting
		}
		if(front.size != 0) // formatting
			toS += ", ";
		for (int i = 0; i < rear.size; i++)// this gets all elements from rear
		{
			toS += (String) rear.get(i);// capture element as string
			if (i != rear.size -1) 
				toS += ", "; //formatting
		}
		toS += "]"; // closing brackets
		return toS;
	}
	// this is a toString for rear 
	// to be used in debug toString()
	public String toString2() // rear tostring for toStringForDebugging() method
	{
		String rearS = "["; // opening bracket
		for(int i = 0; i <= rear.size();i++)
		{
			rearS.concat((String) rear.get(rear.size)); // get that element
		}
		rearS += "]"; // close
		return rearS;

	}

	// Create a pretty representation of the DynamicQueue for debugging.
	// Example:
	// front.toString: [A, B] 
	// rear.toString: [C, D]
	protected String toStringForDebugging()
	{
		String frontDebug = front.toString(); // get front queue
		String rearDebug = rear.toString(); // get real queue
		return "front.toString: "+ frontDebug + "rear.toString:" + rearDebug; 
		// return both

	}
}
