package molecule.ui.structureoptions.center.foreground.properties;

import javax.swing.event.ChangeEvent;

import molecule.ui.PropertyControls;
import molecule.ui.jmol.properties.MoleculeJmolUIDiffusePropertyControls_generated;
import molecule.ui.jmol.signal.MoleculeJmolUIDiffuseRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend=PropertyControls.class, raises={MoleculeJmolUIDiffuseRaiser.class})
public class MoleculeJmolUIDiffusePropertyControls extends MoleculeJmolUIDiffusePropertyControls_generated
{
    private static final long serialVersionUID = 1L;

	public MoleculeJmolUIDiffusePropertyControls(String name, int min, int max, int value)
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
		this.raise_MoleculeJmolUIDiffuseEvent();
	}

}
