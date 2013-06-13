package molecule.ui.protein.tertiary.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.protein.primary.signal.ProteinPrimaryTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryTranslucencyRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={ProteinTertiaryTranslucencyRaiser.class})
public class ProteinTertiaryTranslucencyPropertyControls extends ProteinTertiaryTranslucencyPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public ProteinTertiaryTranslucencyPropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
    }
	
	transient private boolean inHandleProteinPrimaryTranslucencyRaiser = false;
	@Handles(raises = {})
	protected void handleProteinPrimaryTranslucencyRaiser(ProteinPrimaryTranslucencyRaiser raiser)
	{
		if(!inStateChanged && !inReset && !inHandleProteinPrimaryTranslucencyRaiser)
		{
			inHandleProteinPrimaryTranslucencyRaiser = true;
			this.getPropertySlider().setValue(raiser.getValue());
			this.getPropertyValue().setValue(raiser.getValue());
	
			this.getPropertySlider().invalidate();
			this.getPropertyValue().invalidate();
	
			this.getPropertySlider().validate();
			this.getPropertyValue().validate();
			isFromProteinTertiaryTranslucencyRaiser = false;
			raiseEvent();
			inHandleProteinPrimaryTranslucencyRaiser = false;
		}
	}

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inStateChanged && !inReset && !inHandleProteinPrimaryTranslucencyRaiser)
		{
			inStateChanged = true;
			this.getPropertyValue().setValue(this.getPropertySlider().getValue());
			if(!this.getPropertySlider().getValueIsAdjusting() || showAdjusting())
			{
				isFromProteinTertiaryTranslucencyRaiser = true;
				raiseEvent();
			}
			inStateChanged = false;
		}
	}

	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inStateChanged && !inInitTree && !inHandleProteinPrimaryTranslucencyRaiser)
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
		if(!inStateChanged && !inReset && !inHandleProteinPrimaryTranslucencyRaiser)
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
		this.raise_ProteinTertiaryTranslucencyEvent();
	}

	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		this.getPropertySlider().setEnabled(enabled);
	}

	transient private boolean isFromProteinTertiaryTranslucencyRaiser = true;
	public boolean isFromProteinTertiaryTranslucencyRaiser()
    {
	    return isFromProteinTertiaryTranslucencyRaiser;
    }
}
