package model;

public enum Goal{ 
	RED1(Color.RED, 1), RED2(Color.RED,2), 
	RED3(Color.RED,3), RED4(Color.RED,4),
	BLUE1(Color.BLUE,1), BLUE2(Color.BLUE,2), 
	BLUE3(Color.BLUE,3), BLUE4(Color.BLUE,4),
	GREEN1(Color.GREEN,1), GREEN2(Color.GREEN,2), 
	GREEN3(Color.GREEN,3), GREEN4(Color.GREEN,4),
	YELLOW1(Color.YELLOW,1), YELLOW2(Color.YELLOW,2), 
	YELLOW3(Color.YELLOW,3), YELLOW4(Color.YELLOW,4),
	WILDCARD(Color.BLACK,0);
	
	public final Color colour;
	public final int shape;
	
	Goal (Color colour, int shape)
	{
		this.colour = colour;
		this.shape = shape;
	}
}
