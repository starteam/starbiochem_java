package molecule.ui;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JPanel;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences

@SignalComponent(extend=JPanel.class)
public abstract class AbstractAtomRenderingProperties extends AbstractAtomRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;

	public abstract String getTitle();
	public abstract String getPropertyName();
	public abstract int getPropertyValue();
	
	public void addPropertyControls(PropertyControls pc, GridBagConstraints constraints)
	{
		propertyControlsArray.add(pc);
		
		constraints.ipadx = 5;
		constraints.weightx = 7;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		add(pc.getPropertySlider(), constraints);

		constraints.weightx = 0.2;
		constraints.gridx = GridBagConstraints.RELATIVE;
		add(pc.getPropertyValue(), constraints);

		constraints.weightx = 2;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		add(pc.getPropertyLabel(), constraints);

		getAdapter().addComponent(pc);
	}

	ArrayList<PropertyControls> propertyControlsArray = new ArrayList<PropertyControls>();

	protected int minscale = -1;
	protected int maxscale = -1;
	protected int minsize = -1;
	protected int maxsize = -1;

	protected int selectedTranslucency = -1;
	protected int selectedBondTranslucency = -1;
	protected int selectedSize = -1;

	
	protected int defaultTranslucency = -1;
	protected int defaultBondTranslucency = -1;
	protected int defaultSize = -1;

	protected String translucency_string=Messages.getString("AbstractAtomRenderingProperties.0"); //$NON-NLS-1$
	protected String bondTranslucency_string=Messages.getString("AbstractAtomRenderingProperties.1"); //$NON-NLS-1$
	protected String size_string = Messages.getString("AbstractAtomRenderingProperties.2"); //$NON-NLS-1$
	protected String ssbond_size_string = Messages.getString("AbstractAtomRenderingProperties.3"); //$NON-NLS-1$
	protected String ssbond_translucency_string = Messages.getString("AbstractAtomRenderingProperties.4");	 //$NON-NLS-1$
	protected String hbond_size_string = Messages.getString("AbstractAtomRenderingProperties.5"); //$NON-NLS-1$
	protected String hbond_translucency_string = Messages.getString("AbstractAtomRenderingProperties.6");	 //$NON-NLS-1$

	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("minscale", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		minscale = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("maxscale", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		maxscale = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("minsize", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		minsize = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("maxsize", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		maxsize = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("translucency", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedTranslucency = defaultTranslucency = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("bondtranslucency", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedBondTranslucency = defaultBondTranslucency = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("size", "20"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedSize = defaultSize = Integer.parseInt(s);
		
		translucency_string = getPreferences(preferencesName).get("translucency_string", translucency_string); //$NON-NLS-1$
		bondTranslucency_string = getPreferences(preferencesName).get("bondtranslucency_string", bondTranslucency_string); //$NON-NLS-1$
		size_string = getPreferences(preferencesName).get("size_string", size_string); //$NON-NLS-1$
		ssbond_size_string = getPreferences(preferencesName).get("ssbond_size_string", ssbond_size_string); //$NON-NLS-1$
		ssbond_translucency_string = getPreferences(preferencesName).get("ssbond_translucency_string", ssbond_translucency_string); //$NON-NLS-1$
		hbond_size_string = getPreferences(preferencesName).get("hbond_size_string", hbond_size_string); //$NON-NLS-1$
		hbond_translucency_string = getPreferences(preferencesName).get("hbond_translucency_string", hbond_translucency_string); //$NON-NLS-1$
	}

}
