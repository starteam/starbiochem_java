package molecule.ui.protein.primary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimaryBondTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryBondTranslucencyRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinPrimaryBondTranslucencyRaiser.class})
public class ProteinPrimaryBondTranslucencyPropertyControls extends ProteinPrimaryBondTranslucencyPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinPrimaryBondTranslucencyPropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inHandleProteinTertiaryBondTranslucencyRaiser = false;
	@Handles(raises = {})
	protected void handleProteinTertiaryBondTranslucencyRaiser(ProteinTertiaryBondTranslucencyRaiser raiser)
	{
		if(!inHandleProteinTertiaryBondTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inHandleProteinTertiaryBondTranslucencyRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinPrimaryBondTranslucencyRaiser = false;
			raiseEvent();
			inHandleProteinTertiaryBondTranslucencyRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inHandleProteinTertiaryBondTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinPrimaryBondTranslucencyRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inHandleProteinTertiaryBondTranslucencyRaiser && !inStateChanged && !inInitTree)
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
		if(!inHandleProteinTertiaryBondTranslucencyRaiser && !inStateChanged && !inReset)
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
		this.raise_ProteinPrimaryBondTranslucencyEvent();
	}

	transient private boolean isFromProteinPrimaryBondTranslucencyRaiser = true;
	public boolean isFromProteinPrimaryBondTranslucencyRaiser()
    {
	    return isFromProteinPrimaryBondTranslucencyRaiser;
    }

}
