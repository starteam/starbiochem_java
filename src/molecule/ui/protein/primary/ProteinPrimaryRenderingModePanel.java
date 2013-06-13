package molecule.ui.protein.primary;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import molecule.ui.protein.primary.signal.ProteinPrimaryApplyRenderingRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryRenderingModeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryRenderingModeRaiser;

import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {ProteinPrimaryRenderingModeRaiser.class, ProteinPrimaryApplyRenderingRaiser.class})
public class ProteinPrimaryRenderingModePanel extends ProteinPrimaryRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton proteinprimary_rendering_automatic_mode = null;
	JRadioButton proteinprimary_rendering_button_mode = null;
	JButton proteinprimary_apply_rendering_button = null;
	
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
		
		setBorder(new TitledBorder("")); //$NON-NLS-1$

		proteinprimary_rendering_automatic_mode = new JRadioButton(proteinprimary_rendering_automatic_mode_string);
		proteinprimary_rendering_automatic_mode.setMnemonic(proteinprimary_rendering_automatic_mode_vkey);
		proteinprimary_rendering_automatic_mode.setSelected(proteinprimary_rendering_automatic_mode_isselected);

		proteinprimary_rendering_button_mode = new JRadioButton(proteinprimary_rendering_button_mode_string);
		proteinprimary_rendering_button_mode.setMnemonic(proteinprimary_rendering_button_mode_vkey);
		proteinprimary_rendering_button_mode.setSelected(proteinprimary_rendering_button_mode_isselected);

		proteinprimary_apply_rendering_button = new JButton(proteinprimary_apply_rendering_button_string);
		proteinprimary_apply_rendering_button.setMnemonic(proteinprimary_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(proteinprimary_rendering_automatic_mode);
		group.add(proteinprimary_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(proteinprimary_rendering_automatic_mode);
		add(proteinprimary_rendering_button_mode);
		add(proteinprimary_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		proteinprimary_rendering_automatic_mode.removeItemListener(this);
		proteinprimary_rendering_button_mode.removeItemListener(this);
		proteinprimary_apply_rendering_button.removeActionListener(this);
		proteinprimary_rendering_automatic_mode = null;
		proteinprimary_rendering_button_mode = null;
		proteinprimary_apply_rendering_button = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_ProteinPrimaryApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset && !inHandleTertiaryRenderingMode)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(proteinprimary_rendering_automatic_mode))
	        {
	        	this.proteinprimary_rendering_automatic_mode_isselected = proteinprimary_rendering_automatic_mode.isSelected();
	        	if(this.proteinprimary_rendering_automatic_mode_isselected)
	        	{
	        		this.proteinprimary_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(proteinprimary_rendering_button_mode))
	        {
		        this.proteinprimary_rendering_button_mode_isselected = proteinprimary_rendering_button_mode.isSelected();
		        if(this.proteinprimary_rendering_button_mode_isselected)
		        {
		        	this.proteinprimary_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_ProteinPrimaryRenderingModeEvent();
	        inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree && !inHandleTertiaryRenderingMode)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != proteinprimary_rendering_automatic_mode)
			{
				proteinprimary_rendering_automatic_mode.setSelected(true);
				proteinprimary_rendering_automatic_mode.addItemListener(this);
			}
			if(null != proteinprimary_apply_rendering_button)
			{
				proteinprimary_apply_rendering_button.setEnabled(false);
				proteinprimary_apply_rendering_button.addActionListener(this);
			}
			if(null != proteinprimary_rendering_button_mode)
			{
				proteinprimary_rendering_button_mode.addItemListener(this);
			}
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		proteinprimary_rendering_automatic_mode_isselected = true;
		proteinprimary_rendering_button_mode_isselected = false;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset && !inHandleTertiaryRenderingMode)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteinprimary_rendering_automatic_mode)
			{
				proteinprimary_rendering_automatic_mode.setSelected(true);
			}
			if(null != proteinprimary_apply_rendering_button)
			{
				proteinprimary_apply_rendering_button.setEnabled(false);
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		proteinprimary_rendering_automatic_mode_isselected = true;
		proteinprimary_rendering_button_mode_isselected = false;
	}
	
	private boolean inHandleTertiaryRenderingMode = false;
	@Handles(raises = {})
	protected void handleProteinTertiaryRenderingModeRaiser(ProteinTertiaryRenderingModeRaiser raiser)
	{
		if(!inItemStateChanged && !inReset && !inHandleTertiaryRenderingMode)
		{
			inHandleTertiaryRenderingMode = true;
			proteinprimary_rendering_automatic_mode_isselected = raiser.isAutomaticallyRendered();
			if(null != proteinprimary_apply_rendering_button)
			{
				proteinprimary_apply_rendering_button.setEnabled(!proteinprimary_rendering_automatic_mode_isselected);
			}
			if(null != proteinprimary_rendering_automatic_mode)
			{
				proteinprimary_rendering_automatic_mode.setSelected(proteinprimary_rendering_automatic_mode_isselected);
			}
			if(null != proteinprimary_rendering_button_mode)
			{
				proteinprimary_rendering_button_mode.setSelected(!proteinprimary_rendering_automatic_mode_isselected);
			}
			inHandleTertiaryRenderingMode = false;
		}
	}

	protected boolean default_proteinprimary_rendering_automatic_mode_isselected = true;
	protected boolean proteinprimary_rendering_automatic_mode_isselected = true;
	protected boolean proteinprimary_rendering_button_mode_isselected = false;
	
	protected String proteinprimary_rendering_automatic_mode_string = Messages.getString("ProteinPrimaryRenderingModePanel.2"); //$NON-NLS-1$
	protected String proteinprimary_rendering_button_mode_string = Messages.getString("ProteinPrimaryRenderingModePanel.3"); //$NON-NLS-1$
	protected String proteinprimary_apply_rendering_button_string = Messages.getString("ProteinPrimaryRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int proteinprimary_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int proteinprimary_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int proteinprimary_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinprimary_rendering_button_mode_isselected", Boolean.toString(proteinprimary_rendering_button_mode_isselected)); //$NON-NLS-1$
		proteinprimary_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinprimary_rendering_automatic_mode_isselected", Boolean.toString(proteinprimary_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_proteinprimary_rendering_automatic_mode_isselected = proteinprimary_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		proteinprimary_rendering_button_mode_string = getPreferences(preferencesName).get("proteinprimary_rendering_button_mode_string", proteinprimary_rendering_button_mode_string); //$NON-NLS-1$
		proteinprimary_rendering_automatic_mode_string = getPreferences(preferencesName).get("proteinprimary_rendering_automatic_mode_string", proteinprimary_rendering_automatic_mode_string); //$NON-NLS-1$
		proteinprimary_apply_rendering_button_string = getPreferences(preferencesName).get("proteinprimary_apply_rendering_button_string",proteinprimary_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteinprimary_rendering_button_mode_vkey", Integer.toString(proteinprimary_rendering_button_mode_vkey)); //$NON-NLS-1$
		proteinprimary_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinprimary_rendering_automatic_mode_vkey", Integer.toString(proteinprimary_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		proteinprimary_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinprimary_apply_rendering_button_vkey", Integer.toString(proteinprimary_apply_rendering_button_vkey)); //$NON-NLS-1$
		proteinprimary_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}
	
 	public boolean isAutomaticallyRendered()
    {
		return proteinprimary_rendering_automatic_mode_isselected;
    }

 	public boolean isDefaultAutomaticallyRendered()
    {
		return default_proteinprimary_rendering_automatic_mode_isselected;
    }

}
