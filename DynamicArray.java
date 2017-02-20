public class DynamicArray<T> //implementation of the Dynamic Array which 
//contains elements in blocks and pointers called superblocks
{
	protected Object[] arrayOfBlocks;//each index holds a block which is 
	//another array which holds elements
	protected final int DEFAULTCAPACITY = 4; //given by specs
	protected int sizeOfArrayOfBlocks; // number of Blocks in arrayOfBlocks
	protected int size; // number of elements in DynamicArray
	protected int numberOfEmptyDataBlocks; // when superblock it creates block accoring to amount of block for that block 
	// for ex SB 2 can hold 2 block if one block has an element but other doesent then numberOfEmptyDataBlocks is 1 
	// since SB2 = 2 blocks, 1 without element and one without
	protected int numberOfNonEmptyDataBlocks; // same as above but oppsite
	protected int numberOfDataBlocks; // number of data blocks inside arrayof blocks 
	protected int indexOfLastNonEmptyDataBlock;// last block with an element in it
	protected int indexOfLastDataBlock; // the last data block empty or non empty
	protected int numberOfSuperBlocks; // exactally what the name says
	protected SuperBlock lastSuperBlock; // right-most SuperBlock

	// Workhorse constructor. Initialize variables, create the array
	// and the last SuperBlock, which represents SB0.
	public DynamicArray() 
	{
		this.arrayOfBlocks = new Object[DEFAULTCAPACITY];
		this.sizeOfArrayOfBlocks = 1; // initally one block
		this.size = 0; // no elements in block atm
		this.numberOfEmptyDataBlocks = 1;// one block with no elements = emptyblock
		this.numberOfNonEmptyDataBlocks = 0;// no elements in block 
		this.numberOfDataBlocks = 1; // one block so far
		this.indexOfLastNonEmptyDataBlock = -1;// no blocks with elements rn
		this.indexOfLastDataBlock = 0;//index 0 = B0 a.k.a 
		this.numberOfSuperBlocks = 1; // SB0 is initalm points to just B0

		lastSuperBlock = new SuperBlock(0,1,1,1);
		Block<T> b0 = new Block<T>(0,1);
		arrayOfBlocks[0] = b0;
	}
	// Returns the Location of element i, which is the index of the Block
	// and the position of i within that Block.
	// Throws IllegalArgumentException if index < 0 or index > size-1;
	// Target complexity: O(1)
	protected Location locate(int index) 
	{

		int r = index + 1; // gets value of r 
		double k = Math.floor(log2(r)); //superblock number
		int p; // number of blocks preceding current superblock 
		if (k % 2 == 0) // checks if odd/even
			p = (int) (2 * (Math.pow(2,Math.floor(k/2))-1)); // k = even
		else
			p =  (int) ((2*(Math.pow(2, Math.floor(k/2))-1)) + Math.pow(2,Math.floor(k/2))); // k = odd
		int M = (int) Math.ceil(k/2); // gets number of bits to mask by
		int e = r & maskOfN(M); // equ from sec 5.3
		int tmp = r >> M; // number of bits to shift b
		int bM = maskOfN( (int) Math.floor(k/2));
		int b = tmp & bM; // equ also given from sec 5.3
		b = p + b; // given in sec 5.3
		Location waldo = new Location(b,e); // waldo has been finally found
		return waldo;
	}

	// Returns the Block at position i in arrayOfBlocks.
	// Throws IllegalArgumentException if index < 0 or
	// index > sizeOfArrayOfBlocks - 1;
	// Target complexity: O(1)
	@SuppressWarnings("unchecked")
	protected Block<T> getBlock(int index) 
	{
		return (Block<T>) arrayOfBlocks[index];// get index number of index within arrayofBlocks 
	}

	// Returns the element at position i in the DynamicArray.
	// Throws IllegalArgumentException if index < 0 or
	// index > size -1;
	// Target complexity: O(1)
	public T get(int i) 
	{
		if (i < 0 || i >= size) // checks if i is a valid input
			throw new IllegalArgumentException();
		Location loc = locate(i); // get loc
		Block<T> tmp = getBlock(loc.getBlockIndex()); // get inddx withing in that loc
		return tmp.getElement(loc.getElementIndex());
	}

	// Sets the value at position i in DynamicArray to x.
	// Throws IllegalArgumentException if index < 0 or
	// index > size -1;
	// Target complexity: O(1)
	public void set(int index, T x) 
	{
		if (index < 0 || index > size-1) // checks if i is a valid input
			throw new IllegalArgumentException();
		Location loc = locate(index); // get location of index
		Block<T> tmp = getBlock(loc.getBlockIndex()); // get block of that loc
		tmp.setElement(loc.getElementIndex(),x); // set elemett within that block to x
	}

	// Allocates one more spaces in the DynamicArray. 
	// This may require the creation of a Block and the last SuperBlock may change.
	// Also, expandArray is called if the arrayOfBlocks is full when a Block is created.
	// Called by add.
	// Target complexity: O(1)
	protected void grow() 
	{
		Block<T> b = getBlock(indexOfLastDataBlock); 
		if (indexOfLastDataBlock == arrayOfBlocks.length-1 && 
				b.size() == b.getCapacity()) // if arrayofblocks is full and last block is full
			expandArray();//expand array
		if (b.size() == b.getCapacity()) //if array of blocks is full but block is
		{		//checks to see if new sb needs to be made
			if (lastSuperBlock.getCurrentNumberOfDataBlocks() == lastSuperBlock.getMaxNumberOfDataBlocks()) 
			{ // if it does, following if else are for odd and even sb
				if ((double) lastSuperBlock.getNumber() % 2 == 0) // checks if odd or even number
				{ // this is update if following sb is even
					lastSuperBlock = new SuperBlock(lastSuperBlock.getNumber()+1, lastSuperBlock.getMaxNumberOfDataBlocks(),
							lastSuperBlock.getMaxNumberOfElementsPerBlock()*2, 0); // new even sb
					numberOfSuperBlocks++;
				} 
				else 
				{ // this is update if following sb is odd
					lastSuperBlock = new SuperBlock(lastSuperBlock.getNumber()+1, lastSuperBlock.getMaxNumberOfDataBlocks() *2,
							lastSuperBlock.getMaxNumberOfElementsPerBlock(), 0); // new odd sb
					numberOfSuperBlocks++;
				}
			} //after new spuperblock is made need to make new block 
			Block<T> bb = new Block<T>(b.getNumber()+1,lastSuperBlock.getMaxNumberOfElementsPerBlock());
			bb.grow();//grow this newly made block
			arrayOfBlocks[numberOfDataBlocks] = bb;//add into arrayOfBlocks
			lastSuperBlock.currentNumberOfDataBlocks++; //updating stats
			sizeOfArrayOfBlocks++;
			indexOfLastNonEmptyDataBlock++;
			indexOfLastDataBlock++;
			numberOfDataBlocks++;
			numberOfNonEmptyDataBlocks++;
		}
		else 
		{
			if (b.size() == 0) // if 0 meant block was considered empty
			{
				numberOfNonEmptyDataBlocks++;//updating stats 
				numberOfEmptyDataBlocks--;
			}
				b.grow(); // this last situation is if arrayOfBlocks was not full
				// and if block was also not full
				indexOfLastNonEmptyDataBlock = b.getNumber();
		}
	}

	public void add(T x) 
	{	
		grow(); 
		Location loc = locate(size); //gets block and element of last avaliable spot
		@SuppressWarnings("unchecked")
		Block<T> b =(Block<T>) arrayOfBlocks[loc.getBlockIndex()]; //get last block
		b.setElement(loc.elementIndex, x); // set element of that last index within block to x
		size++; // increments every-time new element is added
	}

	// Write a null value to the last element, shrinks the DynamicArray by one
	// space, and decreases the size of the DynamicArray. A Block may be
	// deleted and the last SuperBlock may change.
	// Also, shrinkArray is called if the arrayOfBlocks is less than or equal
	// to a quarter full when a Block is deleted.
	// Target complexity: O(1)
	public void remove() 
	{
		if (size() == 0) //if nothing is present to remove 
			throw new IllegalStateException(); // must catch that error
		Location loc = locate(size()-1); //use to capture last block 
		Block<T> b = getBlock(loc.getBlockIndex());//got last block
		b.shrink();//shrink it by 1
		size--; // since we removed an element need to update
		if (b.size() == 0) //if block is empty we just delete that block
		{
			indexOfLastNonEmptyDataBlock--;//update stats
			numberOfNonEmptyDataBlocks--;
			numberOfEmptyDataBlocks++;
		}
		if (numberOfEmptyDataBlocks > 1)//if more then one empty data blocks exist
		{ //given in specs
			arrayOfBlocks[indexOfLastDataBlock] = null; // need to delete extra block
			lastSuperBlock.currentNumberOfDataBlocks--;//update stats
			numberOfEmptyDataBlocks--;
			numberOfDataBlocks--;
			indexOfLastDataBlock--;
			sizeOfArrayOfBlocks--;
		}
		//if superblock has no blocks in it
		if (lastSuperBlock.getCurrentNumberOfDataBlocks() == 0) //readjust SB 
		{
			if ((double)size/arrayOfBlocks.length <= (double)1/4) //first check size vs lenght
				shrinkArray(); // if size is equal to or less then 1/4 of array, shrink it
			if ((double) lastSuperBlock.getNumber() % 2 == 0) //if preceeding SB is even
			{
				//update SB to one before
				lastSuperBlock = new SuperBlock(lastSuperBlock.getNumber()-1, lastSuperBlock.getMaxNumberOfDataBlocks()/2, 
						lastSuperBlock.getMaxNumberOfElementsPerBlock(),lastSuperBlock.getMaxNumberOfDataBlocks()/2);
				numberOfSuperBlocks--;//update stats	
			} 
			else 
			{ // if SB is odd
				lastSuperBlock = new SuperBlock(lastSuperBlock.getNumber()-1,lastSuperBlock.getMaxNumberOfDataBlocks(), 
						lastSuperBlock.getMaxNumberOfElementsPerBlock()/2,lastSuperBlock.getMaxNumberOfDataBlocks());
				numberOfSuperBlocks--;//update stats
			}
		}
	}
	// Decreases the length of the arrayOfBlocks by half. Create a new
	// arrayOfBlocks and copy the Blocks from the old one to this new array.
	protected void shrinkArray() 
	{
		Object[] tmp = arrayOfBlocks; // tmp array set to arrayofblocks
		arrayOfBlocks = new Object[arrayOfBlocks.length/2];//re initilize with half length
		for (int j = 0; j < arrayOfBlocks.length; j++)
			arrayOfBlocks[j] = tmp[j];//moves tmp elements back into resize arrayofblocks
	}

	// Doubles the length of the arrayOfBlocks. Create a new
	// arrayOfBlocks and copy the Blocks from the old one to this new array.
	protected void expandArray() 
	{
		Object[] tmp = arrayOfBlocks;// tmp array set to arrayofblocks
		arrayOfBlocks = new Object[arrayOfBlocks.length * 2];//re initilize with double length
		for (int j = 0; j < tmp.length; j++)
			arrayOfBlocks[j] = tmp[j];//moves tmp elements back into resize arrayofblocks
	}

	// Returns the size of the DynamicArray which is the number of elements that
	// have been added to it with the add(x) method but not removed. The size
	// does not correspond to the capacity of the array.
	public int size() 
	{
		return this.size; // returns how many elemetns have been added 
	}
	//DO NOT EDIT
	// Returns the log base 2 of n
	protected static double log2(int n) 
	{
		return (Math.log(n) / Math.log(2));
	}

	// Returns a mask of N 1 bits; this code is provided below and can be used
	// as is
	protected int maskOfN(int N) 
	{
		int POW2ToN = 1 << N; // left shift 1 N places; e.g., 1 << 2 = 100 = 4
		int mask = POW2ToN - 1; // subtract 1; e.g., 1002 – 12 = 0112 = 3
		// Integer.toString(mask,2); // a String with the bits of mask
		return mask;
	}

	// Create a pretty representation of the DynamicArray. This method should
	// return string formatted similarly to ArrayList
	// Examples: [], [X], [A, B, C, D]
	//           ^SB  ^B    ^block Elements
	// Target Complexity: O(N)
	// N: number of elements in the DynamicArray
	public String toString() 
	{
		String DA = "["; // initial
		for (int i = 0; i < size(); i++) // elemtnts in block
		{
			DA += get(i); // gets element in each index
			if (i != size() - 1) // for A E S T H E T I C S 
				DA += ", ";
		}
		DA += "]"; // --personal note: why dosent concat() work here?
		return DA;
	}

	// Create a pretty representation of the DynamicArray for debugging
	// Example:
	// DynamicArray: A B
	// numberOfDataBlocks: 2
	// numberOfEmptyDataBlocks: 0
	// numberOfNonEmptyDataBlocks: 2
	// indexOfLastNonEmptyDataBlock: 1
	// indexOfLastDataBlock: 1
	// numberOfSuperBlocks: 2
	// lastSuperBlock: SB1
	// Block0: A
	// - capacity: 1 size: 1
	// Block1: B
	// - capacity: 2 size: 1
	// SB1:
	// - maxNumberOfDataBlocks: 1
	// - numberOfElementsPerBlock: 2
	// - currentNumberOfDataBlocks: 1
	protected String toStringForDebugging() 
	{
		String DA = ""; // for blocks
		for (int i = 0; i < size(); i++) 
		{
			DA += get(i); //add elements 
			DA += " ";//space for formating
		}
		String bb = ""; // elements in block
		for (int j = 0; j < sizeOfArrayOfBlocks; j++) 
		{
			Block<T> b = getBlock(j); // gets block
			bb += ("Block");
			bb += (b.getNumber()); // block number
			bb += (": ");
			bb += b.toString();  // prints out toString
			bb += ("\n");
			bb += ("- capacity=" + b.getCapacity() + " size=" + b.size());  //formatting
			bb += "\n";  //formatting
		}
		String sb = ""; // for superblock
		sb += "SB" + lastSuperBlock.getNumber() + ": \n"; // next few lines basically get data from each SB
		sb += "- maxNumberOfDataBlocks: " + lastSuperBlock.maxNumberOfDataBlocks + "\n";
		sb += "- maxNumberOfElementsPerBlock: " + lastSuperBlock.maxNumberOfElementsPerBlock + "\n";
		sb += "- currentNumberOfDataBlocks: " + lastSuperBlock.currentNumberOfDataBlocks + "\n";
		return "Dynamic Array: " + DA + "\n" + "numberOfDataBlocks: " + numberOfDataBlocks + "\n"
		+ "numberOfEmptyDataBlocks: " + numberOfEmptyDataBlocks + "\n" + "numberOfNonEmptyDataBlocks: "
		+ numberOfNonEmptyDataBlocks + "\n" + "indexOfLastNonEmptyDataBlock: " + indexOfLastNonEmptyDataBlock
		+ "\n" + "indexOfLastDataBlock: " + indexOfLastDataBlock + "\n" + "numberOfSuperBlocks: "
		+ numberOfSuperBlocks + "\n" + "lastSuperBlock: SB" + (numberOfSuperBlocks - 1) + "\n" + bb + sb;
	}
}

