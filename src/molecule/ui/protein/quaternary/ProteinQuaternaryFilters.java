package molecule.ui.protein.quaternary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Messages;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {})
public class ProteinQuaternaryFilters extends ProteinQuaternaryFilters_generated
{
	private static final long serialVersionUID = 1L;

	ProteinQuaternaryRenderingModePanel modes = null;
	
	JLabel quaternary_note = null;

	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		if(!isInitialized)
		{
			init();
			isInitialized = true;
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
	}

	private void init()
	{
		loadPreferences("protein"); //$NON-NLS-1$
		
		quaternary_note = new JLabel(this.quaternary_note_string);
		
        JPanel parts = new JPanel();
		parts.setLayout(new FlowLayout());
		
		parts.add(BorderLayout.CENTER, quaternary_note);

		modes = new ProteinQuaternaryRenderingModePanel();

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, parts);
		add(BorderLayout.EAST, modes);
	}
	
	private void end()
	{
		removeAll();
		quaternary_note = null;
		modes = null;
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			if(null != modes)
			{
				modes.initTree();
			}
			inInitTree = false;
		}
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			if(null != modes)
			{
				modes.reset();
			}
			inReset = false;
		}
	}

	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.ALL_CHAINS_VISIBLE);
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != modes)
		{
			return modes.isDefaultAutomaticallyRendered();
		}
		return true;
	}

	String quaternary_note_string = Messages.getString("ProteinQuaternaryFilters.0"); //$NON-NLS-1$
	
	protected void loadPreferences(String preferencesName)
	{
		quaternary_note_string = getPreferences(preferencesName).get("quaternary_note_string", quaternary_note_string).trim(); //$NON-NLS-1$
	}

}
