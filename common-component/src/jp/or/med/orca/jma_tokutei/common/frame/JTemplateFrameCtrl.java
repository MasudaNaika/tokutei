package jp.or.med.orca.jma_tokutei.common.frame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;

public class JTemplateFrameCtrl extends JTemplateFrame
{
	/*
	 * 
	 */
	public void pushedEndButton( ActionEvent e )
	{
		
	}
	
	/*
	 * 
	 */
	public void actionPerformed( ActionEvent e )
	{
		if( (JButton)(e.getSource()) == jButton_End )
		{
			pushedEndButton( e );
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

