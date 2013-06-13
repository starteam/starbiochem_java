package molecule.ui;

import javax.swing.JLabel;

public class PropertyLabel extends JLabel
{

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public PropertyLabel(String name)
	{
		this.name = name;
		setText(name);
	}

	public void addNotify()
	{
		super.addNotify();
	}

	public void removeNotify()
	{
		super.removeNotify();
		removeAll();
	}

}
