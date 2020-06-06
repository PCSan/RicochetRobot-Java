package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Goal;
import model.RRPart;

/** Layer with one part of the map with goals*/
@SuppressWarnings("serial")
public class RRPartPanel extends JPanel
{
	private RRPart partModel;
	private int quadrant;
	public int BORDER_WIDTH = 1;
	public static int UPPERRIGHT = 0, UPPERLEFT = 1, LOWERLEFT = 2, LOWERRIGHT = 3;
	public final Image[] symbols;
	
	private Goal[] available = {Goal.RED1, Goal.RED2, Goal.RED3, Goal.RED4,
			Goal.BLUE1, Goal.BLUE2, Goal.BLUE3, Goal.BLUE4,
			Goal.GREEN1, Goal.GREEN2, Goal.GREEN3, Goal.GREEN4,
			Goal.YELLOW1, Goal.YELLOW2, Goal.YELLOW3, Goal.YELLOW4,
			Goal.WILDCARD} ;

	public RRPartPanel(RRPart partModel, int quadrant)
	{
		this.partModel = partModel;
		this.quadrant = quadrant;
		symbols = new Image[17];
		
		for (int i = 0; i < 4; i++)
			symbols[i] = new ImageIcon("symbols/RED"+i+".png").getImage();
		for (int i = 0; i < 4; i++)
			symbols[i+4] = new ImageIcon("symbols/BLUE"+i+".png").getImage();
		for (int i = 0; i < 4; i++)
			symbols[i+8] = new ImageIcon("symbols/GREEN"+i+".png").getImage();
		for (int i = 0; i < 4; i++)
			symbols[i+12] = new ImageIcon("symbols/YELLOW"+i+".png").getImage();
		symbols[16] = new ImageIcon("symbols/WILD.png").getImage();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBackground(g);
	}

	private void drawGoal(Graphics g, Goal goal, int i , int j)
	{
		int x = getGlobalX(i, j);
		int y = getGlobalY(i, j);

		
		for (int count = 0; count < available.length; count++)
		{
			if (goal == available[count])
				g.drawImage(symbols[count], x+5, y+5, 30, 30,  null);
		}
	}

	private void drawBackground(Graphics g)
	{
		int x, y;
		for (int i = 0; i < partModel.getSquares().length; i++)
		{
			for (int j = 0; j < partModel.getSquares().length; j++)
			{
				x = getGlobalX(i, j);
				y = getGlobalY(i, j);

				if (i == 0 && j == 0)
				{
					g.setColor(Color.DARK_GRAY);
					g.fillRect(x, y, getWidth()/8, getHeight()/8);
					continue;
				}

				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(x, y, getWidth()/8, getHeight()/8);
				g.setColor(Color.BLACK);
				g.drawRect(x,y,getWidth()/8-BORDER_WIDTH,getHeight()/8-BORDER_WIDTH);
				
				drawWalls(i, j, g);
				
				if (partModel.getSquares()[i][j].getGoal() != null)
					drawGoal(g, partModel.getSquares()[i][j].getGoal(), i, j);
			}
		}
	}

	private void drawWalls(int i, int j, Graphics g)
	{
		int x = getGlobalX(i, j);
		int y = getGlobalY(i, j);
		int width = BORDER_WIDTH*4;
		
		// Upper wall
		if (partModel.getSquares()[i][j].getUpperNeighbour() == null)
		{
			if (quadrant == UPPERRIGHT)
				g.fillRect(x, y, getWidth()/8, width);
			else if (quadrant == UPPERLEFT)
				g.fillRect(x, y, width, getWidth()/8);
			else if (quadrant == LOWERLEFT)
				g.fillRect(x, y + getWidth()/8-width, getWidth()/8, width);
			else
				g.fillRect(x + getWidth()/8 - width, y, width, getWidth()/8);
		}
		
		// Lower wall
		if (partModel.getSquares()[i][j].getLowerNeighbour() == null)
		{	
			if (quadrant == UPPERRIGHT)
				g.fillRect(x, y + getWidth()/8-width, getWidth()/8, width);
			else if (quadrant == UPPERLEFT)
				g.fillRect(x + getWidth()/8 - width, y, width, getWidth()/8);
			else if (quadrant == LOWERLEFT)
				g.fillRect(x, y, getWidth()/8, width);
			else
				g.fillRect(x, y, width, getWidth()/8);
		}
		
		// Left wall
		if (partModel.getSquares()[i][j].getLeftNeighbour() == null)
		{		
			if (quadrant == UPPERRIGHT)
				g.fillRect(x, y, width, getWidth()/8);
			else if (quadrant == UPPERLEFT)
				g.fillRect(x, y + getWidth()/8-width, getWidth()/8, width);
			else if (quadrant == LOWERLEFT)
				g.fillRect(x + getWidth()/8 - width, y, width, getWidth()/8);
			else
				g.fillRect(x, y, getWidth()/8, width);
		}
		
		// Right wall
		if (partModel.getSquares()[i][j].getRightNeighbour() == null)
		{	
			if (quadrant == UPPERRIGHT)
				g.fillRect(x + getWidth()/8 - width, y, width, getWidth()/8);
			else if (quadrant == UPPERLEFT)
				g.fillRect(x, y, getWidth()/8, width);
			else if (quadrant == LOWERLEFT)
				g.fillRect(x, y, width, getWidth()/8);
			else
				g.fillRect(x, y + getWidth()/8-width, getWidth()/8, width);
				
		}
		
	}

	private int getGlobalX(int i, int j)
	{
		if (quadrant == UPPERRIGHT)
		{
			return toLinearMovement(j);

		}
		else if (quadrant == UPPERLEFT)
		{
			return toDecreasingMovement(i);

		}
		else if (quadrant == LOWERLEFT)
		{
			return toDecreasingMovement(j);

		}
		return toLinearMovement(i);	
	}

	private int getGlobalY(int i, int j)
	{
		if (quadrant == UPPERRIGHT)

			return toDecreasingMovement(i);

		else if (quadrant == UPPERLEFT)
			return toDecreasingMovement(j);

		else if (quadrant == LOWERLEFT)
			return toLinearMovement(i);

		return toLinearMovement(j);

	}

	private int toLinearMovement(int x)
	{
		int squareWidth = getWidth()/8;
		return x*squareWidth;
	}

	private int toDecreasingMovement(int y)
	{
		int squareWidth = getWidth()/8;
		return getHeight() - (y+1)*squareWidth;
	}



}
