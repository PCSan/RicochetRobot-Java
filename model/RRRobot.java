package model;

public class RRRobot
{
	private RRSquare position;
	private RRSquare startPos;
	private Color colour;
	
	public RRRobot(RRSquare position, Color colour)
	{
		this.position = position;
		this.colour = colour;
		position.setRobot(this);
	}
	
	/**
	 * Set the starting position of the current round
	 * @param pos
	 */
	public void setStartPos(RRSquare pos)
	{
		startPos = pos;
	}
	
	/**
	 * Get the starting position of the current round
	 * @return
	 */
	public RRSquare getStartPos()
	{
		return startPos;
	}
	
	/**
	 * Move the robot in a specific direction according to the square it rests in
	 * @param direction
	 */
	public void move(int direction)
	{
		if (direction == RRSquare.UP)
			moveUp();
		else if (direction == RRSquare.DOWN)
			moveDown();
		else if (direction == RRSquare.LEFT)
			moveLeft();
		else if (direction == RRSquare.RIGHT)
			moveRight();
		else
			System.out.println("Invalid move");	
	}
	
	public Goal goalState()
	{
		return position.getGoal();
	}
	
	private void moveLeft()
	{
		position.setRobot(null);
		position = position.findGlobalLeft();
		position.setRobot(this);
	}
	
	private void moveDown()
	{
		position.setRobot(null);
		position = position.findGlobalDown();
		position.setRobot(this);
	}
	
	private void moveUp()
	{
		position.setRobot(null);
		position = position.findGlobalUp();
		position.setRobot(this);
	}
	
	private void moveRight()
	{
		position.setRobot(null);
		position = position.findGlobalRight();
		position.setRobot(this);
	}
	
	public String toString()
	{
		return position.toString();
	}

	public RRSquare getSquare()
	{
		return position;
	}
	
	public Color getColour(){ return colour;}
	
	/**
	 * Move the robot back to its original position 
	 */
	public void resetPosition()
	{
		position.setRobot(null);
		position = startPos;
		position.setRobot(this);
	}
}
