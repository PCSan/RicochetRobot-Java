package view;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import model.RRBoard;

/** The layer with the four parts of the map*/
@SuppressWarnings("serial")
public class RRBoardPanel extends JPanel
{
	private RRPartPanel[] parts;
	
	public RRBoardPanel(RRBoard boardModel)
	{
		parts = new RRPartPanel[4];
		
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = new RRPartPanel(boardModel.getParts()[i], i);
		}
		
		LayoutManager layout = new GridLayout(2,2);
		setLayout(layout);
		
		setParts();
	}
	
	private void setParts()
	{
		add(parts[1]);
		add(parts[0]);
		add(parts[2]);
		add(parts[3]);
	}
}
