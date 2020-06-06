package control;

import javax.swing.UIManager;

public class RRDriver
{
	public static void main(String[] args)
	{	
		// first we set the LookAndFeel to the operation system's default.
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}
		
		// Make a frame and show it
		RRFrame frame = new RRFrame();
		frame.setVisible(true);
	}
	
}
