package model;

import java.util.HashMap;

/**
 * A part consists of 64 squares, with the one in 0,0 being empty of content.
 * This square is connected to other 0,0 of other parts
 * 
 * @author John
 * 
 */
public class RRPart
{
	private RRSquare[][] squares;
	private int orientation;
	public int partSize = 8;
	
	public static final int UPPERRIGHT=0;
	public static final int UPPERLEFT=1;
	public static final int LOWERLEFT=2;
	public static final int LOWERRIGHT=3;
	

	public RRPart()
	{
		squares = new RRSquare[partSize][partSize];
		for (int i = 0; i < partSize; i++)
		{
			for (int j = 0; j < partSize; j++)
				squares[i][j] = new RRSquare(null, null, this, i, j);
		}
		
		initInterior();
		initSides();
	}
	
	private void initInterior()
	{
		// Set the upper neighbour of all squares, but the last row
		// Set the right neighbour of all squares, except the last column
		for (int i = 0; i < partSize-1; i++ )
		{
			setUpperNeighbours(i);
			setRightNeighbours(i);
		}
		
		// Set the lower neighbour of all squares, except the first row
		// Set the left neighbour of all squares, except the first column
		for (int i = 1; i < partSize; i++)
		{
			setLowerNeighbours(i);
			setLeftNeighbours(i);
		}
	}
	
	private void initSides()
	{
		// Init the border neighbours to themself to signify empty room (no wall)
		for (int i = 0; i < 8; i++)
		{
			squares[0][i].setLowerNeighbour(squares[0][i]);
			squares[i][0].setLeftNeighbour(squares[i][0]);
		}
	}

	/**
	 * @return the squares
	 */
	public RRSquare[][] getSquares()
	{
		return squares;
	}

	/**
	 * Connect part to the left side of this by connecting the squares in the
	 * left column to the bottow row of part
	 * 
	 * @param part
	 */
	public void connectLeftSide(RRPart part)
	{
		for (int i = 1; i < partSize; i++)
		{
			if (part.getSquares()[0][i].getLowerNeighbour() == null)
			{
				squares[i][0].setLeftNeighbour(null);
				continue;
			}
			
			if (squares[i][0].getLeftNeighbour() != null)
				squares[i][0].setLeftNeighbour((part.getSquares()[0][i]));
		}
	}
	
	/**
	 * Connect part to the bottom of this by connecting the squares in the
	 * bottom row to the left column of part
	 * @param part
	 */
	public void connectBottom(RRPart part)
	{
		for (int i = 1; i < partSize; i++)
		{
			if (part.getSquares()[i][0].getLeftNeighbour() == null)
			{
				squares[0][i].setLowerNeighbour(null);
				continue;
			}
			if (squares[0][i].getLowerNeighbour() != null)
				squares[0][i].setLowerNeighbour((part.getSquares()[i][0]));
		}
	}
	
	public void setOrientation(int orint){
		if (orint >= 0 && orint < 4)
			orientation = orint;
	}
	
	public int getOrientation(){
		return orientation;
	}

	/**
	 * Make all squares in row "row" have access to their local upper neighbour
	 * 
	 * @param row
	 */
	public void setUpperNeighbours(int row)
	{
		for (int i = 0; i < partSize; i++)
		{
			squares[row][i].setUpperNeighbour(squares[row+1][i]);
		}
	}

	/**
	 * Make all squares in row "row" have access to their local lower neighbour
	 * 
	 * @param row
	 */
	public void setLowerNeighbours(int row)
	{
		for (int i = 0; i < partSize; i++)
		{
			squares[row][i].setLowerNeighbour(squares[row-1][i]);
		}
	}

	/**
	 * Make all squares in column "column" have access to their right local neighbour
	 * 
	 * @param column
	 */
	public void setRightNeighbours(int column)
	{
		for (int i = 0; i < partSize; i++)
		{
			squares[i][column].setRightNeighbour(squares[i][column+1]);
		}
	}

	/**
	 * Make all squares in column "column" have access to their left local neighbour
	 * 
	 * @param column
	 */
	public void setLeftNeighbours(int column)
	{
		for (int i = 0; i < partSize; i++)
		{
			squares[i][column].setLeftNeighbour(squares[i][column-1]);
		}
	}
	
	public HashMap<Point, RRSquare> setGlobalUpperRight(){
		HashMap<Point, RRSquare> map = new HashMap<Point, RRSquare>();
		for (int i=0; i<partSize; i++){
			for (int j=0; j<partSize; j++){
				int x=partSize+j+1;
				int y=partSize-i;
				squares[i][j].setPos(x,y);
				map.put(new Point(x,y),squares[i][j]);
			}
		}
		return map;
	}
	
	public HashMap<Point, RRSquare> setGlobalUpperLeft(){
		HashMap<Point, RRSquare> map = new HashMap<Point, RRSquare>();
		for (int i=0; i<partSize; i++){
			for (int j=0; j<partSize; j++){
				int x=partSize-i;
				int y=partSize-j;
				squares[i][j].setPos(x,y);
				map.put(new Point(x,y),squares[i][j]);
			}
		}
		return map;
	}
	
	public HashMap<Point, RRSquare> setGlobalLowerLeft(){
		HashMap<Point, RRSquare> map = new HashMap<Point, RRSquare>();
		for (int i=0; i<partSize; i++){
			for (int j=0; j<partSize; j++){
				int x=partSize-j;
				int y=partSize+i+1;
				squares[i][j].setPos(x,y);
				map.put(new Point(x,y),squares[i][j]);
			}
		}
		return map;
	}
	
	public HashMap<Point, RRSquare>setGlobalLowerRight(){
		HashMap<Point, RRSquare> map = new HashMap<Point, RRSquare>();
		for (int i=0; i<partSize; i++){
			for (int j=0; j<partSize; j++){
				int x=partSize+i+1;
				int y=partSize+j+1;
				squares[i][j].setPos(x,y);
				map.put(new Point(x,y),squares[i][j]);
			}
		}
		return map;
	}
	
	public static RRPart upperRight(){
		RRPart part = new RRPart();
		part.orientation=UPPERRIGHT;
		part.getSquares()[0][1].setLeftNeighbour(null);
		part.getSquares()[0][1].setRightNeighbour(null);
		part.getSquares()[0][2].setLeftNeighbour(null);
		part.getSquares()[0][2].setLowerNeighbour(null);
		part.getSquares()[0][2].setGoal(Goal.WILDCARD);
		part.getSquares()[1][0].setLowerNeighbour(null);
		part.getSquares()[1][5].setRightNeighbour(null);
		part.getSquares()[1][6].setLeftNeighbour(null);
		part.getSquares()[1][6].setUpperNeighbour(null);
		part.getSquares()[1][6].setGoal(Goal.BLUE1);
		part.getSquares()[2][3].setUpperNeighbour(null);
		part.getSquares()[2][3].setRightNeighbour(null);
		part.getSquares()[2][3].setGoal(Goal.GREEN2);
		part.getSquares()[2][4].setLeftNeighbour(null);
		part.getSquares()[2][6].setLowerNeighbour(null);
		part.getSquares()[3][3].setLowerNeighbour(null);
		part.getSquares()[3][7].setUpperNeighbour(null);
		part.getSquares()[4][2].setUpperNeighbour(null);
		part.getSquares()[4][7].setLowerNeighbour(null);
		part.getSquares()[5][1].setRightNeighbour(null);
		part.getSquares()[5][2].setLeftNeighbour(null);
		part.getSquares()[5][2].setLowerNeighbour(null);
		part.getSquares()[5][2].setGoal(Goal.RED3);
		part.getSquares()[5][4].setUpperNeighbour(null);
		part.getSquares()[6][4].setLowerNeighbour(null);
		part.getSquares()[6][4].setRightNeighbour(null);
		part.getSquares()[6][4].setGoal(Goal.YELLOW4);
		part.getSquares()[6][5].setLeftNeighbour(null);
		part.getSquares()[7][0].setRightNeighbour(null);
		part.getSquares()[7][1].setLeftNeighbour(null);
		return part;
	}
	
	public static RRPart upperLeft(){
		RRPart part = new RRPart();
		part.orientation=UPPERLEFT;
		// Construct the local walls inside this part
		part.getSquares()[0][1].setLeftNeighbour(null);
		part.getSquares()[1][0].setLowerNeighbour(null);
		part.getSquares()[1][3].setRightNeighbour(null);
		part.getSquares()[1][4].setLeftNeighbour(null);
		part.getSquares()[1][4].setUpperNeighbour(null);
		part.getSquares()[1][4].setGoal(Goal.BLUE4);
		part.getSquares()[2][2].setUpperNeighbour(null);
		part.getSquares()[2][4].setLowerNeighbour(null);
		part.getSquares()[2][7].setUpperNeighbour(null);
		part.getSquares()[3][2].setLowerNeighbour(null);
		part.getSquares()[3][2].setRightNeighbour(null);
		part.getSquares()[3][2].setGoal(Goal.RED2);
		part.getSquares()[3][3].setLeftNeighbour(null);
		part.getSquares()[3][7].setLowerNeighbour(null);
		part.getSquares()[5][1].setUpperNeighbour(null);
		part.getSquares()[5][6].setUpperNeighbour(null);
		part.getSquares()[5][6].setRightNeighbour(null);
		part.getSquares()[5][6].setGoal(Goal.YELLOW1);
		part.getSquares()[5][7].setLeftNeighbour(null);
		part.getSquares()[6][0].setRightNeighbour(null);
		part.getSquares()[6][1].setLeftNeighbour(null);
		part.getSquares()[6][1].setLowerNeighbour(null);
		part.getSquares()[6][1].setGoal(Goal.GREEN3);
		part.getSquares()[6][6].setLowerNeighbour(null);
		part.getSquares()[7][2].setRightNeighbour(null);
		part.getSquares()[7][3].setLeftNeighbour(null);
		return part;
	}
	
	public static RRPart lowerLeft(){
		RRPart part = new RRPart();
		part.orientation=LOWERLEFT;
		// Construct the local walls inside this part
		part.getSquares()[0][1].setLeftNeighbour(null);
		part.getSquares()[0][1].setRightNeighbour(null);
		part.getSquares()[0][2].setLeftNeighbour(null);
		part.getSquares()[0][2].setUpperNeighbour(null);
		part.getSquares()[0][2].setGoal(Goal.RED4);
		part.getSquares()[1][0].setLowerNeighbour(null);
		part.getSquares()[1][2].setLowerNeighbour(null);
		part.getSquares()[1][5].setUpperNeighbour(null);
		part.getSquares()[2][4].setRightNeighbour(null);
		part.getSquares()[2][5].setLeftNeighbour(null);
		part.getSquares()[2][5].setLowerNeighbour(null);
		part.getSquares()[2][5].setGoal(Goal.BLUE3);
		part.getSquares()[3][7].setUpperNeighbour(null);
		part.getSquares()[4][3].setUpperNeighbour(null);
		part.getSquares()[4][7].setLowerNeighbour(null);
		part.getSquares()[5][3].setLowerNeighbour(null);
		part.getSquares()[5][3].setRightNeighbour(null);
		part.getSquares()[5][3].setGoal(Goal.GREEN1);
		part.getSquares()[5][4].setLeftNeighbour(null);
		part.getSquares()[6][1].setUpperNeighbour(null);
		part.getSquares()[6][1].setRightNeighbour(null);
		part.getSquares()[6][1].setGoal(Goal.YELLOW2);
		part.getSquares()[6][2].setLeftNeighbour(null);
		part.getSquares()[7][1].setLowerNeighbour(null);
		part.getSquares()[7][2].setRightNeighbour(null);
		part.getSquares()[7][3].setLeftNeighbour(null);
		return part;
	}
	
	public static RRPart lowerRight(){
		RRPart part = new RRPart();
		part.orientation=LOWERRIGHT;
		// Construct the local walls inside this part
		part.getSquares()[0][1].setLeftNeighbour(null);
		part.getSquares()[0][4].setUpperNeighbour(null);
		part.getSquares()[1][0].setLowerNeighbour(null);
		part.getSquares()[1][3].setRightNeighbour(null);
		part.getSquares()[1][4].setLowerNeighbour(null);
		part.getSquares()[1][4].setLeftNeighbour(null);
		part.getSquares()[1][4].setGoal(Goal.YELLOW3);
		part.getSquares()[3][6].setRightNeighbour(null);
		part.getSquares()[3][6].setUpperNeighbour(null);
		part.getSquares()[3][6].setGoal(Goal.RED1);
		part.getSquares()[3][7].setLeftNeighbour(null);
		part.getSquares()[4][0].setRightNeighbour(null);
		part.getSquares()[4][1].setLeftNeighbour(null);
		part.getSquares()[4][1].setUpperNeighbour(null);
		part.getSquares()[4][1].setGoal(Goal.BLUE2);
		part.getSquares()[4][6].setLowerNeighbour(null);
		part.getSquares()[5][1].setLowerNeighbour(null);
		part.getSquares()[5][5].setUpperNeighbour(null);
		part.getSquares()[5][7].setUpperNeighbour(null);
		part.getSquares()[6][5].setLowerNeighbour(null);
		part.getSquares()[6][5].setRightNeighbour(null);
		part.getSquares()[6][5].setGoal(Goal.GREEN4);
		part.getSquares()[6][6].setLeftNeighbour(null);
		part.getSquares()[6][7].setLowerNeighbour(null);
		part.getSquares()[7][1].setRightNeighbour(null);
		part.getSquares()[7][2].setLeftNeighbour(null);
		return part;
	}

}
