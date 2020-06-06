package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Goal;
import model.RRBoard;
import model.RRSquare;

public class RRBackgroundPanel extends JPanel{
	RRBoard board;
	public int BORDER_WIDTH = 2;
	
	private final Goal[] available = {Goal.RED1, Goal.RED2, Goal.RED3, Goal.RED4,
			Goal.BLUE1, Goal.BLUE2, Goal.BLUE3, Goal.BLUE4,
			Goal.GREEN1, Goal.GREEN2, Goal.GREEN3, Goal.GREEN4,
			Goal.YELLOW1, Goal.YELLOW2, Goal.YELLOW3, Goal.YELLOW4,
			Goal.WILDCARD} ;
	private final HashMap<Goal, Image> symbols = new HashMap<Goal, Image>(24);
	
	public RRBackgroundPanel(RRBoard board){
		Image[] imgs = new Image[17];
		for (int i = 0; i < 4; i++){
			imgs[i] = new ImageIcon("symbols/RED"+i+".png").getImage();
			imgs[i+4] = new ImageIcon("symbols/BLUE"+i+".png").getImage();
			imgs[i+8] = new ImageIcon("symbols/GREEN"+i+".png").getImage();
			imgs[i+12] = new ImageIcon("symbols/YELLOW"+i+".png").getImage();
		}
		imgs[16] = new ImageIcon("symbols/WILD.png").getImage();
	
		for (int i = 0; i<available.length; i++){
			Goal g = available[i];
			symbols.put(g, imgs[i]);
		}
		
		this.board = board;
		setBackground(Color.LIGHT_GRAY);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int maxX = getWidth();
		int maxY = getHeight(); 
		double cellWidth = maxX / (double) board.size();
		double cellHeight = maxY /(double) board.size();

		// draw all cells
		g.setColor(Color.BLACK);
		for (int i=0; i<board.size(); i++){
			// cell grid
			g.fillRect(globalX(i), 0, BORDER_WIDTH, maxX);
			g.fillRect(0, globalY(i), maxY, BORDER_WIDTH);
			for (int j=0; j<board.size(); j++){
				drawCell(g,j,i,cellWidth,cellHeight);
			}
		}
		g.fillRect(maxX-BORDER_WIDTH, 0, BORDER_WIDTH, maxX);
		g.fillRect(0,maxY-BORDER_WIDTH, maxY,BORDER_WIDTH);
		
		// draw gray center area
		
		//drawDebugGrid(g);
	}
	
	private void drawCell(Graphics g, int x, int y, double cellWidth, double cellHeight){
		int pixX = globalX(x);
		int pixY = globalY(y);
		RRSquare cell = board.squareAt(x+1,y+1);
		Goal goal = cell.getGoal();
		int wallWidth = BORDER_WIDTH*2;
		int cWidth = (int)(cellWidth*(x+1))-(int)(cellWidth*x);
		int cHeight = (int)(cellHeight*(y+1))-(int)(cellHeight*y);
		
		if (goal != null)
			g.drawImage(symbols.get(goal), pixX+5, pixY+5, 30, 30,  null);
		if (cell.getGlobalUpperNeighbour() == null)
			g.fillRect(pixX, pixY, cWidth, wallWidth);
		if (cell.getGlobalLowerNeighbour() == null)
			g.fillRect(pixX, pixY+cHeight-BORDER_WIDTH, cWidth, wallWidth);
		if (cell.getGlobalLeftNeighbour() == null)
			g.fillRect(pixX, pixY, wallWidth, cHeight);
		if (cell.getGlobalRightNeighbour() == null)
			g.fillRect(pixX+cWidth-BORDER_WIDTH, pixY, wallWidth, cHeight);
		
	}
	
	private void drawDebugGrid(Graphics g){
		int maxX = getWidth()-1;
		int maxY = getHeight()-1;
		int centerX = maxX/2;
		int centerY = maxY/2;
		double cellWidth = maxX/16.0;
		double cellHeight = maxY/16.0;
		g.setColor(Color.MAGENTA);
		g.drawLine(centerX, 0, centerX, maxY);
		g.setColor(Color.RED);
		System.out.println("width: "+getWidth());
		System.out.println("height: "+getHeight());
		for (int i=0; i < 17; i++)
		{
			System.out.println("vertical: "+cellWidth+"*"+i+"="+(int)(cellWidth*i));
			g.drawLine((int) (cellWidth*i), 0, (int)(cellWidth*i), maxY);
		}
		g.setColor(Color.CYAN);
		g.drawLine(0, centerY, maxX, centerY);
		g.setColor(Color.GREEN);
		for (int i=0; i < 17; i++){
			System.out.println("horizontal: "+cellHeight*i);
			g.drawLine(0, (int)(cellHeight*i), maxX, (int)(cellHeight*i));
		}
	}
	
	private int globalX(int x){
		double cellWidth = getWidth() / (double) board.size();
		return (int) (x*cellWidth);
	}
	private int globalY(int y){
		double cellWidth = getHeight() / (double) board.size();
		return (int) (y*cellWidth);
	}
}
