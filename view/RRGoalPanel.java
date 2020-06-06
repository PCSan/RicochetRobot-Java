package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Goal;

/** The layer with the current goal in the middle*/
public class RRGoalPanel extends JPanel
{
	public int PREFERRED_SIZE = 256;
	
	private final Goal[] available = {Goal.RED1, Goal.RED2, Goal.RED3, Goal.RED4,
								Goal.BLUE1, Goal.BLUE2, Goal.BLUE3, Goal.BLUE4,
								Goal.GREEN1, Goal.GREEN2, Goal.GREEN3, Goal.GREEN4,
								Goal.YELLOW1, Goal.YELLOW2, Goal.YELLOW3, Goal.YELLOW4,
								Goal.WILDCARD} ;
	private final HashMap<Goal, Image> symbols = new HashMap<Goal, Image>(24);
	private Goal currentGoal;
	
	public RRGoalPanel()
	{
		setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
		
		setOpaque(false);
		
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
	}
	
	public RRGoalPanel(Goal g){
		this();
		setCurrentGoal(g);
	}
	
	public void setCurrentGoal(Goal g){
		currentGoal = g;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Goal goal = currentGoal;
		Image img = symbols.get(goal);
		g.drawImage(img, getWidth()/2 - 20, getHeight()/2 - 20, 40, 40,  null);
	}
}
