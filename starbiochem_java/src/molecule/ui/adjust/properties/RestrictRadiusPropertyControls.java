package molecule.ui.adjust.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyLogControls;
import molecule.ui.adjust.signal.RestrictRadiusRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyLogControls.class, raises={RestrictRadiusRaiser.class})
public class RestrictRadiusPropertyControls extends RestrictRadiusPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public RestrictRadiusPropertyControls(String name, int value)
    {
	    super(name, value);
    }

	transient private boolean inStateChanged = false;
	public void stateChanged(ChangeEvent e)
	{
		if(!inStateChanged && !inReset)
		{
			inStateChanged = true;
			this.setValue(this.getValue());
			if(!this.getPropertyLogSlider().getValueIsAdjusting() || showAdjusting())
			{
				raiseEvent();
			}
			inStateChanged = false;
		}
	}
	
	transient private boolean inReset = false;
	public void reset(int value)
	{
		if(!inStateChanged && !inReset)
		{
			inReset = true;
			this.getPropertyLogSlider().setValueIsAdjusting(true);
			this.setValue(value);
			this.getPropertyLogSlider().setValueIsAdjusting(false);
			inReset = false;
		}
	}

	transient private boolean inInitTree = false;
	public void initTree(int value)
	{
		if(!inStateChanged && !inInitTree)
		{
			inInitTree = true;
			this.getPropertyLogSlider().setValueIsAdjusting(true);
			this.setValue(value);
			this.getPropertyLogSlider().setValueIsAdjusting(false);
			super.initTree();
			inInitTree = false;
		}
	}

	public void raiseEvent()
	{
		this.raise_RestrictRadiusEvent();
	}

}
