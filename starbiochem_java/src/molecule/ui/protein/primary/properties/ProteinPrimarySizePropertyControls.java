package molecule.ui.protein.primary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimarySizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySizeRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinPrimarySizeRaiser.class})
public class ProteinPrimarySizePropertyControls extends ProteinPrimarySizePropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinPrimarySizePropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inHandleProteinTertiarySizeRaiser = false;
	@Handles(raises = {})
	protected void handleProteinTertiarySizeRaiser(ProteinTertiarySizeRaiser raiser)
	{
		if(!inHandleProteinTertiarySizeRaiser && !inStateChanged && !inReset)
		{
			inHandleProteinTertiarySizeRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinPrimarySizeRaiser = false;
			raiseEvent();
			inHandleProteinTertiarySizeRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inHandleProteinTertiarySizeRaiser && !inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinPrimarySizeRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inHandleProteinTertiarySizeRaiser && !inStateChanged && !inInitTree)
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
		if(!inHandleProteinTertiarySizeRaiser && !inStateChanged && !inReset)
		{
			inReset = true;
			this.getPropertySlider().setValueIsAdjusting(true);
			this.getPropertySlider().setValue(value);
			this.getPropertyValue().setValue(value);
			this.getPropertySlider().setValueIsAdjusting(false);
			inReset = false;
		}
	}

	public void raiseEvent()
	{
		this.raise_ProteinPrimarySizeEvent();
	}

	transient private boolean isFromProteinPrimarySizeRaiser = true;
	public boolean isFromProteinPrimarySizeRaiser()
    {
	    return isFromProteinPrimarySizeRaiser;
    }

}
