package control;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Goal;
import model.RRBoard;
import model.RRRobot;
import model.RRSquare;

import view.RRRootLayer;

public class RRFrame extends JFrame implements MouseListener, ActionListener
{
	private RRRootLayer root;
	
	private RRBoard board;
	private RRRobot selectedRobot;
	
	public RRFrame()
	{
		board = new RRBoard();
		
		root = new RRRootLayer(board);
		root.addMouseListener(this);
		add(root,BorderLayout.CENTER);
		
		JButton resetPos = new JButton("Reset positions");
		resetPos.setActionCommand("reset");
		resetPos.addActionListener(this);
		add(resetPos, BorderLayout.SOUTH);
		resetPos.setFocusable(false);
//		JButton newGoal = new JButton("New goal");
//		newGoal.setActionCommand("NewG");
//		newGoal.addActionListener(this);
//		add(newGoal, BorderLayout.SOUTH);
//		newGoal.setFocusable(false);
		
		initFrame();
	}
	
	private void update()
	{
		root.updateRobots(selectedRobot);
		root.repaintRobots();
		
		// Conditions for a completed round are fulfilled
		if (selectedRobot != null && board.isGoal()){
			board.nextRound();
		}
		root.updateCurrentGoal(board.getCurrentGoal());
	}
	
	/**
	 * Setup the frame
	 */
	private void initFrame()
	{
		setTitle("Ricochet Robot");
		setSize(new Dimension(600, 600));
		//setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if (arg0.getSource() == root)
		{	
			// clicked field (considering borders) 
			int x = Math.min((int) ((arg0.getX())/ (root.boardWidth()/16.0))+1,16);
			int y = Math.min((int) ((arg0.getY())/ (root.boardHeight()/16.0))+1,16);
			RRSquare clickedSquare = board.squareAt(x,y);
			
			// If a robot is selected, try to move it
			if (selectedRobot != null)
			{	
				Integer possibleMove = findValidMoveTo(selectedRobot,clickedSquare);
				if ( possibleMove != null )
				{
					root.startTimer(selectedRobot.getSquare());
					selectedRobot.move(possibleMove);
				}
					
			}
			
			// Make whatever was present here the currently selected robot
			selectedRobot = clickedSquare.getRobot();
		}
		
		update();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("reset"))
		{
			board.resetRobots();
		}
		else if (e.getActionCommand().equals("NewG"))
			board.chooseRandomGoal();
		
		update();
	}
	
	private Integer findValidMoveTo(RRRobot robot, RRSquare square){
		RRSquare current = robot.getSquare();
		if (current.findGlobalUp() == square)
			return RRSquare.UP;
		if (current.findGlobalDown() == square)
			return RRSquare.DOWN;
		if (current.findGlobalLeft() == square)
			return RRSquare.LEFT;
		if (current.findGlobalRight() == square)
			return RRSquare.RIGHT;
		return null;
	}
}
