package molecule.ui.protein.tertiary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimaryBondTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryBondTranslucencyRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinTertiaryBondTranslucencyRaiser.class})
public class ProteinTertiaryBondTranslucencyPropertyControls extends ProteinTertiaryBondTranslucencyPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinTertiaryBondTranslucencyPropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }

	transient private boolean inHandleProteinPrimaryBondTranslucencyRaiser = false;
	@Handles(raises = {})
	protected void handleProteinPrimaryBondTranslucencyRaiser(ProteinPrimaryBondTranslucencyRaiser raiser)
	{
		if(!inHandleProteinPrimaryBondTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inHandleProteinPrimaryBondTranslucencyRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinTertiaryBondTranslucencyRaiser = false;
			raiseEvent();
			inHandleProteinPrimaryBondTranslucencyRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inHandleProteinPrimaryBondTranslucencyRaiser && !inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinTertiaryBondTranslucencyRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inHandleProteinPrimaryBondTranslucencyRaiser && !inStateChanged && !inInitTree)
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
		if(!inHandleProteinPrimaryBondTranslucencyRaiser && !inStateChanged && !inReset)
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
		this.raise_ProteinTertiaryBondTranslucencyEvent();
	}

	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		this.getPropertySlider().setEnabled(enabled);
	}

	transient private boolean isFromProteinTertiaryBondTranslucencyRaiser = true;
	public boolean isFromProteinTertiaryBondTranslucencyRaiser()
    {
	    return isFromProteinTertiaryBondTranslucencyRaiser;
    }

}
