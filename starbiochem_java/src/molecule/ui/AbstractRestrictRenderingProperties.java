package molecule.ui;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JPanel;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences

@SignalComponent(extend=JPanel.class)
public abstract class AbstractRestrictRenderingProperties extends AbstractRestrictRenderingProperties_generated
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

	protected int selectedRadius = -1;
	
	protected int defaultRadius = -1;

	protected String restrict_string = Messages.getString("AbstractRestrictRenderingProperties.0"); //$NON-NLS-1$
	
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


		s = getPreferences(preferencesName).get("radius", "100"); //$NON-NLS-1$ //$NON-NLS-2$
		selectedRadius = defaultRadius = Integer.parseInt(s);

		restrict_string = getPreferences(preferencesName).get("restrict_string", restrict_string); //$NON-NLS-1$
	}

}
