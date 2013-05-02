package molecule.ui.structureoptions.center.backgroundcolor;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import star.annotations.SignalComponent;
import app.signal.MoleculesBackgroundColorRaiser;

@SignalComponent(extend = JColorChooser.class, raises={MoleculesBackgroundColorRaiser.class})
public class BackgroundColorChooser extends BackgroundColorChooser_generated implements ChangeListener
{

	private static final long serialVersionUID = 1L;
	public Color tempColor;
	
	public Color myDefaultColor = null;
	public BackgroundColorChooser(Color defaultColor) 
	{
		super(defaultColor);
		this.myDefaultColor = defaultColor;
		this.getSelectionModel().addChangeListener(this);
	}

	public void stateChanged(ChangeEvent arg0)
	{
		try
		{
			if(null != arg0)
			{
				tempColor = this.getSelectionModel().getSelectedColor();
				if ((null != tempColor) && !tempColor.equals(moleculesBackgroundColor))
				{
					this.moleculesBackgroundColor = tempColor;
					this.raise_MoleculesBackgroundColorEvent();
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Color moleculesBackgroundColor = this.myDefaultColor;
	public Color getMoleculesBackgroundColor()
	{
		return this.moleculesBackgroundColor;
	}

}
