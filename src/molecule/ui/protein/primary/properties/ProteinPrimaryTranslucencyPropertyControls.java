package molecule.ui.protein.primary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimaryTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryTranslucencyRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinPrimaryTranslucencyRaiser.class})
public class ProteinPrimaryTranslucencyPropertyControls extends ProteinPrimaryTranslucencyPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinPrimaryTranslucencyPropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inHandleProteinTertiaryTranslucencyRaiser = false;
	@Handles(raises = {})
	protected void handleProteinTertiaryTranslucencyRaiser(ProteinTertiaryTranslucencyRaiser raiser)
	{
		if(!inHandleProteinTertiaryTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inHandleProteinTertiaryTranslucencyRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinPrimaryTranslucencyRaiser = false;
			raiseEvent();
			inHandleProteinTertiaryTranslucencyRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inHandleProteinTertiaryTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinPrimaryTranslucencyRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inHandleProteinTertiaryTranslucencyRaiser && !inStateChanged && !inInitTree)
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
		if(!inHandleProteinTertiaryTranslucencyRaiser && !inStateChanged && !inReset)
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
		this.raise_ProteinPrimaryTranslucencyEvent();
	}

	transient private boolean isFromProteinPrimaryTranslucencyRaiser = true;
	public boolean isFromProteinPrimaryTranslucencyRaiser()
    {
	    return isFromProteinPrimaryTranslucencyRaiser;
    }
	
}
