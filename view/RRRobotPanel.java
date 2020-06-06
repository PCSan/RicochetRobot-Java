package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.RRBoard;
import model.RRRobot;
import model.RRSquare;

/** The layer with robots and their highlights */
public class RRRobotPanel extends JPanel
{
	private RRBoard board;
	private RRRobot selectedRobot;
	
	// Start position of the selected robot (not to be confused with starting position of each robot in a round)
	private RRSquare startPosition;
	private double movementProgress;
	
	private final Image symbols[];
	
	public RRRobotPanel(RRBoard model)
	{	
		board = model;
		resetProgress();

		symbols = new Image[5];
		// The order is red, blue, green, yellow, black
		symbols[0] = new ImageIcon("symbols/ROBOT_RED.png").getImage();
		symbols[1] = new ImageIcon("symbols/ROBOT_BLUE.png").getImage();
		symbols[2] = new ImageIcon("symbols/ROBOT_GREEN.png").getImage();
		symbols[3] = new ImageIcon("symbols/ROBOT_YELLOW.png").getImage();
		symbols[4] = new ImageIcon("symbols/ROBOT_BLACK.png").getImage();
		
		setOpaque(false);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawRobots(g);
		
		if (selectedRobot != null)
			highLightPositions(g);
		
		if (movementProgress == 1)
			resetProgress();
		
	}
	
	/**
	 * Set the currently selected robot to highlight the paths from
	 * @param robot
	 */
	public void setSelectedRobot(RRRobot robot)
	{
		selectedRobot = robot;
	}
	
	private void highLightPositions(Graphics g)
	{
		RRSquare dest;
		int maxX = getWidth();
		int maxY = getHeight();
		double cellWidth = maxX / (double) board.size();
		double cellHeight = maxY /(double) board.size();
		int width= (int) cellWidth;
		int height= (int) cellHeight;
		
		g.setColor(Color.CYAN);
		dest = selectedRobot.getSquare().findGlobalUp();
		//g.drawRect(findGlobalX(dest), findGlobalY(dest), getWidth()/16, getWidth()/16);
		g.drawRect((int)((dest.getGlobalX()-1)*cellWidth), 
				(int)((dest.getGlobalY()-1)*cellHeight), width,height);
		
		dest = selectedRobot.getSquare().findGlobalDown();
		//g.drawRect(findGlobalX(dest), findGlobalY(dest), getWidth()/16, getWidth()/16);
		g.drawRect((int)((dest.getGlobalX()-1)*cellWidth), 
				(int)((dest.getGlobalY()-1)*cellHeight), width,height);
		
		dest = selectedRobot.getSquare().findGlobalLeft();
		//g.drawRect(findGlobalX(dest), findGlobalY(dest), getWidth()/16, getWidth()/16);
		g.drawRect((int)((dest.getGlobalX()-1)*cellWidth), 
				(int)((dest.getGlobalY()-1)*cellHeight), width,height);
		
		dest = selectedRobot.getSquare().findGlobalRight();
		//g.drawRect(findGlobalX(dest), findGlobalY(dest), getWidth()/16, getWidth()/16);
		g.drawRect((int)((dest.getGlobalX()-1)*cellWidth), 
				(int)((dest.getGlobalY()-1)*cellHeight), width,height);
	}
	
	private void drawRobots(Graphics g)
	{
		
		
		for (int i = 0; i < board.getRobots().length; i++)
		{
			RRRobot robot = board.getRobots()[i];

			// For a robot on the move
			if (startPosition != null && robot == selectedRobot)
			{
				int startProgressX = (int)((1 - movementProgress)*robotPosX(startPosition.getGlobalX()));
				int endProgressX = (int)(robotPosX(robot.getSquare().getGlobalX())*movementProgress);
				int startProgressY = (int)((1 - movementProgress)*robotPosY(startPosition.getGlobalY()));
				int endProgressY = (int)(robotPosY(robot.getSquare().getGlobalY())*movementProgress); 
				int x = startProgressX+endProgressX;
				int y = startProgressY+endProgressY;

				g.drawImage(symbols[i], x, y, 40, 40, null);

			}
			// For a robot standing still
			else{
				int x = robotPosX(robot.getSquare().getGlobalX());
				int y = robotPosY(robot.getSquare().getGlobalY());
				g.drawImage(symbols[i], x, y, 40, 40, null);
			}
			
		}
	}
	
	/**
	 * Increase the progress with i. The progress can not exceed 1
	 * @param i
	 */
	public void increaseProgress(double i)
	{
		movementProgress = Math.min(movementProgress+i, 1);
	}
	
	public void resetProgress()
	{
		movementProgress = 0;
	}
	
	/**
	 * Set the destination a robot should move to
	 * @param startPos
	 */
	public void setStartPosition(RRSquare startPos)
	{
		startPosition = startPos;
	}
	
	public double getProgress()
	{
		return movementProgress;
	}
	
	private int robotPosX(int x){
		double cellWidth = getWidth()/16.0;
		return (int)((x-1)*cellWidth) + (int)(cellWidth/16);
	}
	
	private int robotPosY(int y){
		double cellHeight = getHeight()/16.0;
		return (int)((y-1)*cellHeight) + (int)(cellHeight/16);
	}
	
//	private int findGlobalX(RRSquare square)
//	{
//		if (square.getParentPart() == board.getParts()[0]){
//			return getWidth()/2 + toLinearMovement(square.getPosY());
//		}
//		else if(square.getParentPart() == board.getParts()[1])
//			return toDecreasingMovement(square.getPosX());
//		else if (square.getParentPart() == board.getParts()[2])
//			return toDecreasingMovement(square.getPosY());
//		else
//			return getWidth()/2 + toLinearMovement(square.getPosX());
//	}
//	
//	private int findGlobalY(RRSquare square)
//	{
//		if (square.getParentPart() == board.getParts()[0])
//			return toDecreasingMovement(square.getPosX());
//		else if(square.getParentPart() == board.getParts()[1])
//			return toDecreasingMovement(square.getPosY());
//		else if (square.getParentPart() == board.getParts()[2])
//			return getHeight()/2 + toLinearMovement(square.getPosX());
//		else
//			return getHeight()/2 + toLinearMovement(square.getPosY());
//	}
//	
//	private int toLinearMovement(int x)
//	{
//		int squareWidth = getWidth()/16;
//		return x*squareWidth;
//	}
//	
//	private int toDecreasingMovement(int y)
//	{
//		int squareWidth = getWidth()/16;
//		return getHeight()/2 - (y+1)*squareWidth;
//	}
}
