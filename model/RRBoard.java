package model;

import java.util.HashMap;

public class RRBoard
{
	private RRPart[] parts;
	
	
	
	private Goal[] available = {Goal.RED1, Goal.RED2, Goal.RED3, Goal.RED4,
								Goal.BLUE1, Goal.BLUE2, Goal.BLUE3, Goal.BLUE4,
								Goal.GREEN1, Goal.GREEN2, Goal.GREEN3, Goal.GREEN4,
								Goal.YELLOW1, Goal.YELLOW2, Goal.YELLOW3, Goal.YELLOW4,
								Goal.WILDCARD} ;
	
	private final Color[] colours = {	Color.RED, Color.BLUE, 
										Color.GREEN, Color.YELLOW,
										Color.BLACK};
	
	private Goal currentGoal;
	private int rounds;
	private RRRobot[] robots;
	private HashMap<Point, RRSquare> posMap;
	
	public RRBoard()
	{
		
		parts = new RRPart[4];
		robots = new RRRobot[5];
		posMap = new HashMap<Point, RRSquare>();
		rounds = 0;
		
		parts[0] = RRPart.upperRight();
		posMap.putAll(parts[0].setGlobalUpperRight());
		parts[1] = RRPart.upperLeft();
		posMap.putAll(parts[1].setGlobalUpperLeft());
		parts[2] = RRPart.lowerLeft();
		posMap.putAll(parts[2].setGlobalLowerLeft());
		parts[3] = RRPart.lowerRight();
		posMap.putAll(parts[3].setGlobalLowerRight());
		
		connectBorders();
		spawnRobots();	
		assignStartPositions();
		chooseRandomGoal();
	}
	
	/**
	 * Reset the position of all robots
	 */
	public void resetRobots()
	{
		for (int i = 0; i < robots.length; i++)
			robots[i].resetPosition();
	}
	
	/**
	 * Select one of the unused goals as the current goal
	 */
	public void chooseRandomGoal()
	{
		int goal = (int) (Math.random() * (available.length - rounds));
		currentGoal = available[goal];
		available[goal] = available[available.length - 1 - rounds];
		available[available.length - 1 - rounds] = currentGoal;
	}
	
	/**
	 * Start next round. A new goal is chosen and robots are assigned starting positions
	 */
	public void nextRound()
	{
		if (rounds < available.length)
		{
			rounds++;			
			assignStartPositions();
			chooseRandomGoal();	
		}
	}
	
	/**
	 * Make each robots current position it's starting position in a round 
	 */
	public void assignStartPositions()
	{
		for (int i = 0; i < robots.length; i++)
			robots[i].setStartPos(robots[i].getSquare());
	}
	
	/**
	 * Choose a valid location (not 0,0) on the map by random
	 */
	public RRSquare chooseRandomLoc()
	{
		int part = (int) (Math.random()*parts.length);
		int row = 0;
		int column = 0;
		while (row == 0 && column == 0)
		{
			row = (int) (Math.random()*parts[part].getSquares().length);
			column = (int) (Math.random()*parts[part].getSquares().length);
		}

		return parts[part].getSquares()[row][column];
	}
	
	public RRRobot[] getRobots()
	{
		return robots;
	}
	
	public Goal getCurrentGoal()
	{
		return currentGoal;
	}
	
	public RRPart[] getParts()
	{
		return parts;
	}
	
	public RRSquare squareAt(int x,int y){
		Point p = new Point(x,y);
		if (posMap.containsKey(p))
			return posMap.get(p);
		return null;
	}
	
	public boolean isGoal(){
		for (RRRobot r : robots){
			Goal g = r.getSquare().getGoal();
			if (g==currentGoal){
				if (currentGoal==Goal.WILDCARD)
					return true;
				if (r.getColour() == g.colour)
					return true;
			}
		}
		return false;	
	}
	
	private void spawnRobots()
	{
		for (int i = 0; i < robots.length; i++)
		{	
			RRSquare position = chooseRandomLoc();
			
			// Choose a square without a robot
			while (position.getRobot() != null)
				position = chooseRandomLoc();
			
			robots[i] = new RRRobot(position, colours[i]);
		}
	}
	
	private void connectBorders()
	{
		parts[0].connectLeftSide(parts[1]);
		parts[1].connectBottom(parts[0]);
		parts[1].connectLeftSide(parts[2]);
		parts[2].connectBottom(parts[1]);
		parts[2].connectLeftSide(parts[3]);
		parts[3].connectBottom(parts[2]);
		parts[3].connectLeftSide(parts[0]);
		parts[0].connectBottom(parts[3]);
	}
	
	public int size(){
		return parts[0].partSize*2;
	}
}
