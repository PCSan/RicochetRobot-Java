package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import model.*;

/** Layer which combines all other layers*/
@SuppressWarnings("serial")
public class RRRootLayer extends JPanel implements ActionListener
{
	private JPanel boardPanel;
	private RRRobotPanel robotPanel;
	private RRGoalPanel goalPanel;
	private Timer timer;
	
	public int PREFERRED_SIZE = 700;
	
	public RRRootLayer(RRBoard boardModel)
	{
		setLayout(new BorderLayout());
		JLayeredPane layers = new JLayeredPane();
		layers.setPreferredSize(new Dimension(PREFERRED_SIZE, PREFERRED_SIZE));
		
		boardPanel = new RRBoardPanel(boardModel);
		boardPanel = new RRBackgroundPanel(boardModel);
		robotPanel = new RRRobotPanel(boardModel);
		goalPanel = new RRGoalPanel(boardModel.getCurrentGoal());
		
		boardPanel.setBounds(0, 0, PREFERRED_SIZE, PREFERRED_SIZE);
		robotPanel.setBounds(0, 0, PREFERRED_SIZE, PREFERRED_SIZE);
		goalPanel.setBounds(7*boardPanel.getWidth()/16, 7*boardPanel.getHeight()/16, boardPanel.getWidth()/8, 
				boardPanel.getHeight()/8);
		layers.add(boardPanel, new Integer(0));
		layers.add(robotPanel, new Integer(1));
		layers.add(goalPanel, new Integer(2));	
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		add(layers,BorderLayout.CENTER);
		timer = new Timer(30, this);
	}
	
	/**
	 * Update the robot movement panel by setting the selected robot
	 */
	public void updateRobots(RRRobot selected)
	{
		robotPanel.setSelectedRobot(selected);
	}
	
	public void updateCurrentGoal(Goal g){
		goalPanel.setCurrentGoal(g);
	}
	
	public void repaintRobots()
	{
		robotPanel.repaint();
		
		
		if (robotPanel.getProgress() == 1)
		{
			robotPanel.setStartPosition(null);
			robotPanel.resetProgress();
			timer.stop();
		}
	}
	
	/**
	 * Start the movement timer moving from the starting position to the current destination
	 */
	public void startTimer(RRSquare startPos)
	{
		robotPanel.setStartPosition(startPos);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == timer)
		{
			robotPanel.increaseProgress(0.05);
			repaintRobots();
			
		}
		
	}
	
	public int boardWidth(){
		return boardPanel.getWidth();
	}
	public int boardHeight(){
		return boardPanel.getHeight();
	}
	public int boardX(){
		return boardPanel.getX();
	}
	public int boardY(){
		return boardPanel.getY();
	}
}
