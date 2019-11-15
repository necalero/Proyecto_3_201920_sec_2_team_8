package model.data_structures.BSTRojoNegro;



public class NodoBSTRojoNegro <Key,Val> {
	private Key key;           // key
	private Val val;         // associated data
	private NodoBSTRojoNegro left, right;  // links to left and right subtrees
	private boolean color;     // color of parent link
	private int size;          // subtree count
	

	public NodoBSTRojoNegro(Key key2, Val val2, boolean color, int size2) {
		// TODO Auto-generated constructor stub
		this.key = key2;
		this.val = val2;
		this.color = color;
		this.size = size2;
	}
	public boolean darColor()
	{
		return color;
	}
	public void setColor(boolean pColor)
	{
		color = pColor;
	}
	public Key darKey()
	{
		return key;
	}
	public void setKey(Key k)
	{
		key = k;
	}
	public Val darValue()
	{
		return val;
	}
	public void setValue(Val pVal)
	{
		val = pVal;
	}
	public NodoBSTRojoNegro darLeft()
	{
		return left;
	}
	public NodoBSTRojoNegro darRight()
	{
		return right;
	}
	public void setLeft(NodoBSTRojoNegro pLeft)
	{
		left = pLeft;
	}
	public void setRight(NodoBSTRojoNegro pRight)
	{
		left = pRight;
	}
	public int darSize()
	{
		return size;
	}
	public void setSize(int pSize)
	{
		size = pSize;
	}

}
