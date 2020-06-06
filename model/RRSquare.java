package model;

/**
 * One square which connects to at most four other squares
 * 
 * @author John
 * 
 */
public class RRSquare
{
	private RRRobot robot;
	private Goal goal;
	private final RRPart parentPart;
	private int posX;
	private int posY;
	private int globalX=-1;
	private int globalY=-1;

	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;

	private RRSquare[] neighbours;

	public RRSquare(RRRobot robot, Goal goal, RRPart parentPart, int posX,
			int posY)
	{
		setRobot(robot);
		setGoal(goal);
		this.parentPart = parentPart;
		neighbours = new RRSquare[4];
		this.posX = posX;
		this.posY = posY;
	}

	/**
	 * @param robot the robot to set
	 */
	public void setRobot(RRRobot robot)
	{
		this.robot = robot;
	}

	/**
	 * @return the robot
	 */
	public RRRobot getRobot()
	{
		return robot;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Goal goal)
	{
		this.goal = goal;
	}

	/**
	 * @return the goal
	 */
	public Goal getGoal()
	{
		return goal;
	}
	
	private void setNeighbour(RRSquare square, int dir){
		if (dir >= 0 && dir < 4)
			neighbours[dir] = square;
	}
	
	private RRSquare getNeighbour(int dir){
		return neighbours[dir];
	}

	public void setUpperNeighbour(RRSquare square)
	{
		setNeighbour(square, UP);
	}

	public void setLowerNeighbour(RRSquare square)
	{
		setNeighbour(square, DOWN);
	}

	public void setLeftNeighbour(RRSquare square)
	{
		setNeighbour(square, LEFT);
	}

	public void setRightNeighbour(RRSquare square)
	{
		setNeighbour(square, RIGHT);
	}

	public RRSquare getUpperNeighbour()
	{
		return getNeighbour(UP);
	}

	public RRSquare getLowerNeighbour()
	{
		return getNeighbour(DOWN);
	}

	public RRSquare getLeftNeighbour()
	{
		return getNeighbour(LEFT);
	}

	public RRSquare getRightNeighbour()
	{
		return getNeighbour(RIGHT);
	}
	
	public RRSquare getGlobalUpperNeighbour(){
		if (parentPart.getOrientation()==RRPart.UPPERRIGHT)
			return getNeighbour(UP);
		if (parentPart.getOrientation()==RRPart.UPPERLEFT)
			return getNeighbour(RIGHT);
		if (parentPart.getOrientation()==RRPart.LOWERLEFT)
			return getNeighbour(DOWN);
		if (parentPart.getOrientation()==RRPart.LOWERRIGHT)
			return getNeighbour(LEFT);
		return null;
	}
	
	public RRSquare getGlobalLowerNeighbour(){
		if (parentPart.getOrientation()==RRPart.UPPERRIGHT)
			return getNeighbour(DOWN);
		if (parentPart.getOrientation()==RRPart.UPPERLEFT)
			return getNeighbour(LEFT);
		if (parentPart.getOrientation()==RRPart.LOWERLEFT)
			return getNeighbour(UP);
		if (parentPart.getOrientation()==RRPart.LOWERRIGHT)
			return getNeighbour(RIGHT);
		return null;
	}
	
	public RRSquare getGlobalLeftNeighbour(){
		if (parentPart.getOrientation()==RRPart.UPPERRIGHT)
			return getNeighbour(LEFT);
		if (parentPart.getOrientation()==RRPart.UPPERLEFT)
			return getNeighbour(UP);
		if (parentPart.getOrientation()==RRPart.LOWERLEFT)
			return getNeighbour(RIGHT);
		if (parentPart.getOrientation()==RRPart.LOWERRIGHT)
			return getNeighbour(DOWN);
		return null;
	}
	
	public RRSquare getGlobalRightNeighbour(){
		if (parentPart.getOrientation()==RRPart.UPPERRIGHT)
			return getNeighbour(RIGHT);
		if (parentPart.getOrientation()==RRPart.UPPERLEFT)
			return getNeighbour(DOWN);
		if (parentPart.getOrientation()==RRPart.LOWERLEFT)
			return getNeighbour(LEFT);
		if (parentPart.getOrientation()==RRPart.LOWERRIGHT)
			return getNeighbour(UP);
		return null;
	}
	
	private RRSquare getGlobalNeighbour(int dir){
		if (dir==UP) 
			return getGlobalUpperNeighbour();
		else if (dir==DOWN) 
			return getGlobalLowerNeighbour();
		else if (dir==LEFT)
			return getGlobalLeftNeighbour();
		else if (dir==RIGHT)
			return getGlobalRightNeighbour();
		return null;
	}
	
	private RRSquare findGlobalMove(int dir){
		RRSquare square = this;
		
		boolean freeNeighbour = square.getGlobalNeighbour(dir)!= null
				&& square.getGlobalNeighbour(dir) != square
				&& square.getGlobalNeighbour(dir).getRobot() == null;

		while (freeNeighbour)
		{
			square = square.getGlobalNeighbour(dir);
			freeNeighbour = square.getGlobalNeighbour(dir) != null
					&& square.getGlobalNeighbour(dir) != square
					&& square.getGlobalNeighbour(dir).getRobot() == null;
		}

		return square;
	}
	
	public RRSquare findGlobalUp(){
		return findGlobalMove(UP);
	}
	
	public RRSquare findGlobalDown(){
		return findGlobalMove(DOWN);
	}
	
	public RRSquare findGlobalLeft(){
		return findGlobalMove(LEFT);
	}
	
	public RRSquare findGlobalRight(){
		return findGlobalMove(RIGHT);
	}

//	/**
//	 * Get the stop if you move left from this square
//	 * 
//	 * @return
//	 */
//	private RRSquare findMoveLeft()
//	{
//		RRSquare square = this;
//
//		boolean freeNeighbour = square.getNeighbour(LEFT)!= null
//				&& square.getNeighbour(LEFT) != square
//				&& square.getNeighbour(LEFT).getRobot() == null;
//
//		while (freeNeighbour)
//		{
//			if (square.getNeighbour(LEFT).getParentPart() == 
//					this.getParentPart())
//				square = square.getNeighbour(LEFT);
//			else
//				return square.getNeighbour(LEFT).findMoveUp();
//			freeNeighbour = square.getNeighbour(LEFT) != null
//					&& square.getNeighbour(LEFT) != square
//					&& square.getNeighbour(LEFT).getRobot() == null;
//		}
//
//		return square;
//	}
//
//	/**
//	 * Get the stop if you move left from this square
//	 * 
//	 * @return
//	 */
//	private RRSquare findMoveDown()
//	{
//		RRSquare square = this;
//
//		boolean conditions = square.getLowerNeighbour() != null
//				&& square.getLowerNeighbour() != square
//				&& square.getLowerNeighbour().getRobot() == null;
//
//		while (conditions)
//		{
//			if (square.getLowerNeighbour().getParentPart() == this
//					.getParentPart())
//				square = square.getLowerNeighbour();
//			else
//				return square.getLowerNeighbour().findMoveRight();
//			
//			conditions = square.getLowerNeighbour() != null
//			&& square.getLowerNeighbour() != square
//			&& square.getLowerNeighbour().getRobot() == null;
//		}
//		
//		return square;
//	}
//
//	/**
//	 * Get the stop if you move up from this square
//	 * 
//	 * @return
//	 */
//	private RRSquare findMoveUp()
//	{
//		RRSquare square = this;
//
//		boolean conditions = square.getUpperNeighbour() != null
//				&& square.getUpperNeighbour() != square
//				&& square.getUpperNeighbour().getRobot() == null;
//
//		while (conditions)
//		{
//			square = square.getUpperNeighbour();
//
//			conditions = square.getUpperNeighbour() != null
//					&& square.getUpperNeighbour() != square
//					&& square.getUpperNeighbour().getRobot() == null;
//		}
//
//		return square;
//	}
//
//	/**
//	 * Get the stop if you move right from this square
//	 * 
//	 * @return
//	 */
//	private RRSquare findMoveRight()
//	{
//		RRSquare square = this;
//
//		boolean conditions = square.getRightNeighbour() != null
//				&& square.getRightNeighbour() != square
//				&& square.getRightNeighbour().getRobot() == null;
//
//		while (conditions)
//		{
//			square = square.getRightNeighbour();
//
//			conditions = square.getRightNeighbour() != null
//					&& square.getRightNeighbour() != square
//					&& square.getRightNeighbour().getRobot() == null;
//		}
//
//		return square;
//	}

	/**
	 * @return the parentPart
	 */
	public RRPart getParentPart()
	{
		return parentPart;
	}

	public void setPos(int x,int y)
	{
		if (globalX == -1 && globalY ==-1){
			globalX = x;
			globalY = y;
		}
	}
	
	public int getGlobalX(){
		return globalX;
	}
	public int getGlobalY(){
		return globalY;
	}

//	/**
//	 * @return the posX
//	 */
//	public int getPosX()
//	{
//		return posX;
//	}
//
//	/**
//	 * @return the posY
//	 */
//	public int getPosY()
//	{
//		return posY;
//	}

	public String toString()
	{
		return posX + ", " + posY;
	}
}
