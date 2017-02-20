public class SuperBlock //a pointer of sorts to 1...n number of blocks 
{
	protected int number; // as in S0, S1, etc.
	protected int maxNumberOfDataBlocks; //how many blocks it can point to
	// number of elements per Block
	protected int maxNumberOfElementsPerBlock; //how many elements blocks 
	//within a certain SB can hold
	
	// current number of Blocks in this SuperBlock
	protected int currentNumberOfDataBlocks;

	// Workhorse constructor. Initialize variables.
	public SuperBlock(int number, int maxNumberOfDataBlocks,
			int maxNumberOfElementsPerBlock, int currentNumberOfDataBlocks) 
	{
		this.number = number;
		this.maxNumberOfDataBlocks = maxNumberOfDataBlocks;
		this.maxNumberOfElementsPerBlock = maxNumberOfElementsPerBlock;
		this.currentNumberOfDataBlocks = currentNumberOfDataBlocks;

	}

	// Returns number.
	public int getNumber() 
	{
		return number; // globals variable number
	}

	// Returns maxNumberOfDataBlocks
	public int getMaxNumberOfDataBlocks() 
	{ 
		return maxNumberOfDataBlocks; // global variable 
	}

	// Returns maxNumberOfElementsPerBlock
	public int getMaxNumberOfElementsPerBlock() 
	{
		return maxNumberOfElementsPerBlock; // gets variable
	}

	// Returns currentNumberOfDataBlocks
	public int getCurrentNumberOfDataBlocks() 
	{
		return currentNumberOfDataBlocks; // gets variable
	}

	// Increments CurrentNumberOfDataBlocks
	public int incrementCurrentNumberOfDataBlocks() 
	{
		return currentNumberOfDataBlocks++; // increases by 1 to use in grow
	}

	// Decrements currentNumberOfDataBlocks
	public int decrementCurrentNumberOfDataBlocks() 
	{
		return currentNumberOfDataBlocks--; // decreases by 1 to use in grow
	}

	// Create a pretty representation of the SuperBlock for debugging.
	/*
	 * Example: - maxNumberOfDataBlocks:2 - maxNumberOfElementsPerBlock:2 -
	 * currentNumberOfDataBlocks:1
	 */
	protected String toStringForDebugging()
	 {
		 return "- maxNumberOfDataBlocks: " +  maxNumberOfDataBlocks +  "\n" +  
		 "- maxNumberOfElementsPerBlock: " + maxNumberOfElementsPerBlock + "\n" +
		 "- currentNumberOfDataBlocks: " + currentNumberOfDataBlocks + "\n";
	 }
}
