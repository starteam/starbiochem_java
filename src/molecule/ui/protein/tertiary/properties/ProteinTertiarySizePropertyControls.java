package molecule.ui.protein.tertiary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimarySizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySizeRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinTertiarySizeRaiser.class})
public class ProteinTertiarySizePropertyControls extends ProteinTertiarySizePropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinTertiarySizePropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inHandleProteinPrimarySizeRaiser = false;
	@Handles(raises = {})
	protected void handleProteinPrimarySizeRaiser(ProteinPrimarySizeRaiser raiser)
	{
		if(!inHandleProteinPrimarySizeRaiser && !inStateChanged && !inReset)
		{
			inHandleProteinPrimarySizeRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinTertiarySizeRaiser = false;
			raiseEvent();
			inHandleProteinPrimarySizeRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inHandleProteinPrimarySizeRaiser && !inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinTertiarySizeRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inHandleProteinPrimarySizeRaiser && !inStateChanged && !inInitTree)
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
		if(!inHandleProteinPrimarySizeRaiser && !inStateChanged && !inReset)
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
		this.raise_ProteinTertiarySizeEvent();
	}
	
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		this.getPropertySlider().setEnabled(enabled);
	}

	transient private boolean isFromProteinTertiarySizeRaiser = true;
	public boolean isFromProteinTertiarySizeRaiser()
    {
	    return isFromProteinTertiarySizeRaiser;
    }

}
