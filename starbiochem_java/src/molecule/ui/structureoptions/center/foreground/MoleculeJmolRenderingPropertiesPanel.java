package molecule.ui.structureoptions.center.foreground;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.Messages;

import molecule.interfaces.RenderingInfo;
import molecule.ui.PropertyControls;
import molecule.ui.jmol.signal.MoleculeJmolUIBrightnessRaiser;
import molecule.ui.jmol.signal.MoleculeJmolUIDiffuseRaiser;
import molecule.ui.jmol.signal.MoleculeJmolUISegmentsRaiser;
import molecule.ui.jmol.signal.MoleculeJmolUISpecularRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUIBrightnessPropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUIDiffusePropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUISegmentsPropertyControls;
import molecule.ui.structureoptions.center.foreground.properties.MoleculeJmolUISpecularPropertyControls;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises={RenderingInfoRaiser.class})
public class MoleculeJmolRenderingPropertiesPanel extends MoleculeJmolRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String render_string = Messages.getString("MoleculeJmolRenderingPropertiesPanel.0"); //$NON-NLS-1$
	
	private MoleculeJmolRenderingProperties properties = null;
	
	public MoleculeJmolRenderingPropertiesPanel()
	{
		super();
	}
	
	public void addNotify()
	{
		super.addNotify();

		loadPreferences("jmolrendering"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		
		JLabel label = new JLabel(render_string, SwingConstants.LEFT);
			add(BorderLayout.NORTH, label);

		properties = new MoleculeJmolRenderingProperties();
			add(BorderLayout.CENTER, properties);
	}

	public void removeNotify()
	{
		removeAll();
		super.removeNotify();
	}
	
	public void reset()
	{
		if(null != properties)
		{
			properties.reset();
		}
	}
	
	@Handles(raises = {})
    protected void handleMoleculeJmolUIBrightnessRaiser(MoleculeJmolUIBrightnessRaiser raiser)
    {
		if (this.isShowing())
		{
			this.raise_RenderingInfoEvent();
		}
	}
	
	@Handles(raises = {})
    protected void handleMoleculeJmolUIDiffuseRaiser(MoleculeJmolUIDiffuseRaiser raiser)
    {
		if (this.isShowing())
		{
			this.raise_RenderingInfoEvent();
		}
	}
	
	@Handles(raises = {})
    protected void handleMoleculeJmolUISegmentsRaiser(MoleculeJmolUISegmentsRaiser raiser)
    {
		if (this.isShowing())
		{
			this.raise_RenderingInfoEvent();
		}
	}
	
	@Handles(raises = {})
    protected void handleMoleculeJmolUISpecularRaiser(MoleculeJmolUISpecularRaiser raiser)
    {
		if (this.isShowing())
		{
			this.raise_RenderingInfoEvent();
		}
	}
	
	RenderingInfo renderingInfo = null;
	public RenderingInfo getRenderingInfo()
	{
		setRenderingInfo();
		return this.renderingInfo;
	}
	
	protected void setRenderingInfo()
	{
		ArrayList<PropertyControls> controls = properties.getPropertyControlsArrayList();
		if(null != controls)
		{
			int brightness = -1;
			int diffuse = -1;
			int segments = 0;
			int specular = -1;

			Iterator<PropertyControls> myControls = controls.iterator();
			while(myControls.hasNext())
			{
				PropertyControls control = myControls.next();
				if(control instanceof MoleculeJmolUIDiffusePropertyControls)
				{
					diffuse = control.getValue();
				}
				else if(control instanceof MoleculeJmolUISpecularPropertyControls)
				{
					specular = control.getValue();
				}
				else if(control instanceof MoleculeJmolUIBrightnessPropertyControls)
				{
					brightness = control.getValue();
				}
				else if(control instanceof MoleculeJmolUISegmentsPropertyControls)
				{
					segments = control.getValue();
				}
			}
			this.renderingInfo = new molecule.RenderingInfo("Jmol", RenderingInfoRaiser.JMOLPROPERTIES, //$NON-NLS-1$
					null,
					null,
					-1,
					brightness,
					diffuse,
					-1,
					-1,
					segments,
					-1,
					specular,
					0,
					0,
					0,
					-1,
					-1,
					-1,
					-1);
		}
	}

	protected void loadPreferences(String preferencesName)
	{
	}

}

