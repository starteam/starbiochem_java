package molecule.ui;

import javax.swing.JSlider;

public class PropertySlider extends JSlider
{
	private static final long serialVersionUID = 1L;

	public PropertySlider(int min, int max, int value)
	{
		super(JSlider.HORIZONTAL, min, max, value);
	}
	


}
