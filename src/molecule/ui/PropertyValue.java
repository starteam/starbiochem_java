package molecule.ui;

import java.awt.Dimension;

import javax.swing.JLabel;

public class PropertyValue extends JLabel
{
	private static final long serialVersionUID = 1L;

	private int value;

	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
		setText(Integer.toString(this.value));
	}

	public PropertyValue(int value)
	{
		this.value = value;
		setText(Integer.toString(this.value));
	}

	public Dimension getMaximumSize()
	{
		return getPreferredSize();
	}

	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}

}
