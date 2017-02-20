public class Location // hold the block and elements index of a element
{
	// index of Block in DynamicArray.arrayofBlocks
	protected final int blockIndex;
	// index of element in the arrayOfElements in the Block
	protected final int elementIndex;

	// Workhorse constructor. Initialize variables.
	//
	public Location(int blockIndex, int elementIndex) 
	{
		this.blockIndex = blockIndex; // not a lot going on here to talk about :/
		this.elementIndex = elementIndex;
	}

	// Returns blockIndex
	public int getBlockIndex() 
	{
		return blockIndex;// not a lot going on here to talk about :/
	}

	// returns elementIndex
	public int getElementIndex() 
	{
		return elementIndex;// not a lot going on here to talk about :/
	}

	// Create a pretty representation of the Location for debugging.
	// Example:
	// blockIndex:2 elementIndex:1
	protected String toStringForDebugging() 
	{
		return "blockIndex:" + getBlockIndex() + " elementIndex:"	// not a lot going on here to talk about :/
				+ getElementIndex();
	}

}
