package molecule.ui.structureoptions.center.foreground.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.jmol.properties.MoleculeJmolUISpecularPropertyControls_generated;
import molecule.ui.jmol.signal.MoleculeJmolUISpecularRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={MoleculeJmolUISpecularRaiser.class})
public class MoleculeJmolUISpecularPropertyControls extends MoleculeJmolUISpecularPropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public MoleculeJmolUISpecularPropertyControls(String name, int min, int max, int value)
    {
	    super(name, min, max, value);
	    super.initTree();
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
			inReset = false;
		}
	}

	public void raiseEvent()
	{
		this.raise_MoleculeJmolUISpecularEvent();
	}

}
