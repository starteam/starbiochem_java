
package utils;

import javax.swing.*;
import java.awt.event.*;

public class ActionJList extends JList
{
	private static final long serialVersionUID = 1L;
	/*
	 * * sends ACTION_PERFORMED event for double-click* and ENTER key
	 */
	ActionListener al;
	ActionJList myList = null;

	
	public ActionJList(String[] it)
	{
		super(it);
		myList = this;
		
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				if (al == null)
					return;
				Object ob[] = getSelectedValues();
				if (ob.length > 1)
					return;
				if (me.getClickCount() == 2)
				{
					if (!me.isControlDown() && !me.isShiftDown())
					{	
						al.actionPerformed(new ActionEvent(myList, ActionEvent.ACTION_PERFORMED, null));
						me.consume();
					}
				}
			}
		});

		addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent ke)
			{
				if (al == null)
					return;
				Object ob[] = getSelectedValues();
				if (ob.length > 1)
					return;
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
				{
					al.actionPerformed(new ActionEvent(myList, ActionEvent.ACTION_PERFORMED, null));
					ke.consume();
				}
			}
		});
		this.setSelectedIndex(0);
	}

	public void addActionListener(ActionListener al)
	{
		this.al = al;
	}
}