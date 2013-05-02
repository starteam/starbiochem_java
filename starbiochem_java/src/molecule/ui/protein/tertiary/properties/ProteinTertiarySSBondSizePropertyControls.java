package molecule.ui.protein.tertiary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySSBondSizeRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinTertiarySSBondSizeRaiser.class})
public class ProteinTertiarySSBondSizePropertyControls extends ProteinTertiarySSBondSizePropertyControls_generated
{
    private static final long serialVersionUID = 1L;

    public ProteinTertiarySSBondSizePropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inStateChanged && !inInitTree)
		{
			inInitTree = true;
			this.getPropertySlider().setValueIsAdjusting(true);
			this.getPropertySlider().setValue(value);
			this.getPropertyValue().setValue(value);
			this.getPropertySlider().setValueIsAdjusting(false);
			super.initTree();
			inInitTree = false;
		}
	}
	
	transient private boolean inReset = false;
	public void reset(int value)
	{
		if(!inStateChanged && !inReset)
		{
			inReset = true;
			this.getPropertySlider().setValueIsAdjusting(true);
			this.getPropertySlider().setValue(value);
			this.getPropertyValue().setValue(value);
			this.getPropertySlider().setValueIsAdjusting(false);
			super.initTree();
			inReset = false;
		}
	}
	
	public void disableSizeSlider()
	{
		this.getPropertySlider().setEnabled(false);
		this.getPropertyLabel().setEnabled(false);
		this.getPropertyValue().setEnabled(false);
	}

	public void raiseEvent()
	{		
		this.raise_ProteinTertiarySSBondSizeEvent();
	}

}