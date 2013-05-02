package molecule.ui;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JPanel;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend=JPanel.class)
public abstract class AbstractJmolViewerRenderingProperties extends AbstractJmolViewerRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;

	public abstract String getTitle();
	
	public void addNotify()
	{
		super.addNotify();
	}

	public void removeNotify()
	{
		removeAll();
		super.removeNotify();
	}

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

	protected ArrayList<PropertyControls> propertyControlsArray = new ArrayList<PropertyControls>();

	protected int minscale = -1;
	protected int maxscale = -1;
	protected int minsize = -1;
	protected int maxsize = -1;
	protected int minsegments = -1;
	protected int maxsegments = -1;
	protected int minhvz = -100;
	protected int maxhvz = 100;

	protected int selectedBrightness = -1;
	protected int selectedDiffuse = -1;
	protected int selectedSegments = -1;
	protected int selectedSpecular = -1;
	protected int selectedCenterHorizontal = 0;
	protected int selectedCenterVertical = 0;
	protected int selectedCenterZoom = 0;
	protected int selectedLocationHorizontal = 0;
	protected int selectedLocationVertical = 0;
	
	protected int defaultBrightness = -1;
	protected int defaultDiffuse = -1;
	protected int defaultSegments = -1;
	protected int defaultSpecular = -1;
	protected int defaultCenterHorizontal = 0;
	protected int defaultCenterVertical = 0;
	protected int defaultCenterZoom = 0;
	protected int defaultLocationHorizontal = 0;
	protected int defaultLocationVertical = 0;

	protected String brightness_string=Messages.getString("AbstractJmolViewerRenderingProperties.0"); //$NON-NLS-1$
	protected String diffuse_string=Messages.getString("AbstractJmolViewerRenderingProperties.1"); //$NON-NLS-1$
	protected String segments_string=Messages.getString("AbstractJmolViewerRenderingProperties.2"); //$NON-NLS-1$
	protected String specular_string=Messages.getString("AbstractJmolViewerRenderingProperties.3"); //$NON-NLS-1$
	protected String centerhorizontal_string=Messages.getString("AbstractJmolViewerRenderingProperties.4"); //$NON-NLS-1$
	protected String centervertical_string=Messages.getString("AbstractJmolViewerRenderingProperties.5"); //$NON-NLS-1$
	protected String centerzoom_string=Messages.getString("AbstractJmolViewerRenderingProperties.6"); //$NON-NLS-1$
	protected String locationhorizontal_string=Messages.getString("AbstractJmolViewerRenderingProperties.7"); //$NON-NLS-1$
	protected String locationvertical_string=Messages.getString("AbstractJmolViewerRenderingProperties.8"); //$NON-NLS-1$

	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("minscale", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		minscale = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("maxscale", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		maxscale = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("minsegments", "-3"); //$NON-NLS-1$ //$NON-NLS-2$
		minsegments = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("maxsegments", "3"); //$NON-NLS-1$ //$NON-NLS-2$
		maxsegments = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("minhvz", "-10"); //$NON-NLS-1$ //$NON-NLS-2$
		minsegments = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("maxhvz", "10"); //$NON-NLS-1$ //$NON-NLS-2$
		maxsegments = Integer.parseInt(s);


		s = getPreferences(preferencesName).get("brightness", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedBrightness = defaultBrightness = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("diffuse", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedDiffuse = defaultDiffuse = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("segments", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedSegments = defaultSegments = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("specular", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedSpecular = defaultSpecular = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("centerhorizontal", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedCenterHorizontal = defaultCenterHorizontal = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("centervertical", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedCenterVertical = defaultCenterVertical = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("centerzoom", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedCenterZoom = defaultCenterZoom = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("locationhorizontal", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedLocationHorizontal = defaultLocationHorizontal = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("locationvertical", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedLocationVertical = defaultLocationVertical = Integer.parseInt(s);

		brightness_string = getPreferences(preferencesName).get("brightness_string", brightness_string); //$NON-NLS-1$
		diffuse_string = getPreferences(preferencesName).get("diffuse_string", diffuse_string); //$NON-NLS-1$
		segments_string = getPreferences(preferencesName).get("segments_string", segments_string); //$NON-NLS-1$
		specular_string = getPreferences(preferencesName).get("specular_string", specular_string); //$NON-NLS-1$
		centerhorizontal_string = getPreferences(preferencesName).get("centerhorizontal_string", centerhorizontal_string); //$NON-NLS-1$
		centervertical_string = getPreferences(preferencesName).get("centervertical_string", centervertical_string); //$NON-NLS-1$
		centerzoom_string = getPreferences(preferencesName).get("centerzoom_string", centerzoom_string); //$NON-NLS-1$
		locationhorizontal_string = getPreferences(preferencesName).get("locationhorizontal_string", locationhorizontal_string); //$NON-NLS-1$
		locationvertical_string = getPreferences(preferencesName).get("locationvertical_string", locationvertical_string); //$NON-NLS-1$
	}

}
