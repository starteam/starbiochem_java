package molecule.ui.structureoptions.center.foreground;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import molecule.ui.AbstractJmolViewerRenderingProperties;
import molecule.ui.PropertyControls;
import molecule.ui.jmol.MoleculeJmolRenderingProperties_generated;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUIBrightnessPropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUIDiffusePropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUISegmentsPropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUISpecularPropertyControls;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractJmolViewerRenderingProperties.class, raises = {})
public class MoleculeJmolRenderingProperties extends MoleculeJmolRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;

	private static final String JMOLVIEWER = "Jmol Viewer Properties"; //$NON-NLS-1$

	public MoleculeJmolRenderingProperties()
	{
		loadPreferences("jmolviewerproperties"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return JMOLVIEWER;
	}

	public void addNotify()
	{
		super.addNotify();

		initialize();
	}

	public void removeNotify()
	{
		super.removeNotify();
		removeAll();
	}

	public ArrayList<PropertyControls> getPropertyControlsArrayList()
	{
		return this.propertyControlsArray;
	}
	
	private MoleculeJmolUIDiffusePropertyControls diffuse = null;
	private MoleculeJmolUISpecularPropertyControls specular = null;
	private MoleculeJmolUIBrightnessPropertyControls brightness = null;
	private MoleculeJmolUISegmentsPropertyControls segments = null;
	
	private void initialize()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		diffuse = new MoleculeJmolUIDiffusePropertyControls(diffuse_string, minscale, maxscale, selectedDiffuse);
		addPropertyControls(diffuse, constraints);

		constraints.gridy = 2;
		specular = new MoleculeJmolUISpecularPropertyControls(specular_string, minscale, maxscale, selectedSpecular);
		addPropertyControls(specular, constraints);

		constraints.gridy = 3;
		brightness = new MoleculeJmolUIBrightnessPropertyControls(brightness_string, minscale, maxscale, selectedBrightness);
		addPropertyControls(brightness, constraints);

		constraints.gridy = 4;
		segments = new MoleculeJmolUISegmentsPropertyControls(segments_string, minsegments, maxsegments, selectedSegments);
		addPropertyControls(segments, constraints);

		invalidate();
		validateTree();
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != diffuse)
			{
				diffuse.reset(this.defaultDiffuse);
			}
			if(null != specular)
			{
				specular.reset(this.defaultSpecular);
			}
			if(null != brightness)
			{
				brightness.reset(this.defaultBrightness);
			}
			if(null != segments)
			{
				segments.reset(this.defaultSegments);
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		selectedDiffuse = defaultDiffuse;
		selectedSpecular = defaultSpecular;
		selectedBrightness = defaultBrightness;
		selectedSegments = defaultSegments;
	}
}
