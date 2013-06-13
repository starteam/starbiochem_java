package molecule.ui.protein.tertiary;

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

import molecule.ui.protein.primary.signal.ProteinPrimaryRenderingModeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryApplyRenderingRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryRenderingModeRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {ProteinTertiaryRenderingModeRaiser.class, ProteinTertiaryApplyRenderingRaiser.class})
public class ProteinTertiaryRenderingModePanel extends ProteinTertiaryRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton proteintertiary_rendering_automatic_mode = null;
	JRadioButton proteintertiary_rendering_button_mode = null;
	JButton proteintertiary_apply_rendering_button = null;
	
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

		proteintertiary_rendering_automatic_mode = new JRadioButton(proteintertiary_rendering_automatic_mode_string);
		proteintertiary_rendering_automatic_mode.setMnemonic(proteintertiary_rendering_automatic_mode_vkey);
		proteintertiary_rendering_automatic_mode.setSelected(proteintertiary_rendering_automatic_mode_isselected);

		proteintertiary_rendering_button_mode = new JRadioButton(proteintertiary_rendering_button_mode_string);
		proteintertiary_rendering_button_mode.setMnemonic(proteintertiary_rendering_button_mode_vkey);
		proteintertiary_rendering_button_mode.setSelected(proteintertiary_rendering_button_mode_isselected);

		proteintertiary_apply_rendering_button = new JButton(proteintertiary_apply_rendering_button_string);
		proteintertiary_apply_rendering_button.setMnemonic(proteintertiary_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(proteintertiary_rendering_automatic_mode);
		group.add(proteintertiary_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(proteintertiary_rendering_automatic_mode);
		add(proteintertiary_rendering_button_mode);
		add(proteintertiary_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		proteintertiary_rendering_automatic_mode.removeItemListener(this);
		proteintertiary_rendering_button_mode.removeItemListener(this);
		proteintertiary_apply_rendering_button.removeActionListener(this);
		proteintertiary_rendering_automatic_mode = null;
		proteintertiary_rendering_button_mode = null;
		proteintertiary_apply_rendering_button = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_ProteinTertiaryApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset && !inHandleProteinPrimaryRenderingMode)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(proteintertiary_rendering_automatic_mode))
	        {
	        	this.proteintertiary_rendering_automatic_mode_isselected = proteintertiary_rendering_automatic_mode.isSelected();
	        	if(this.proteintertiary_rendering_automatic_mode_isselected)
	        	{
	        		this.proteintertiary_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(proteintertiary_rendering_button_mode))
	        {
		        this.proteintertiary_rendering_button_mode_isselected = proteintertiary_rendering_button_mode.isSelected();
		        if(this.proteintertiary_rendering_button_mode_isselected)
		        {
		        	this.proteintertiary_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_ProteinTertiaryRenderingModeEvent();
	        inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree && !inHandleProteinPrimaryRenderingMode)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != proteintertiary_rendering_automatic_mode)
			{
				proteintertiary_rendering_automatic_mode.setSelected(true);
				proteintertiary_rendering_automatic_mode.addItemListener(this);
			}
			if(null != proteintertiary_apply_rendering_button)
			{
				proteintertiary_apply_rendering_button.setEnabled(false);
				proteintertiary_apply_rendering_button.addActionListener(this);
			}
			if(null != proteintertiary_rendering_button_mode)
			{
				proteintertiary_rendering_button_mode.addItemListener(this);
			}
	        this.proteintertiary_rendering_automatic_mode_isselected = proteintertiary_rendering_automatic_mode.isSelected();
	        inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		proteintertiary_rendering_automatic_mode_isselected = true;
		proteintertiary_rendering_button_mode_isselected = false;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset && !inHandleProteinPrimaryRenderingMode)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteintertiary_rendering_automatic_mode)
			{
				proteintertiary_rendering_automatic_mode.setSelected(true);
			}
			if(null != proteintertiary_apply_rendering_button)
			{
				proteintertiary_apply_rendering_button.setEnabled(false);
			}
	        this.proteintertiary_rendering_automatic_mode_isselected = proteintertiary_rendering_automatic_mode.isSelected();
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		proteintertiary_rendering_automatic_mode_isselected = true;
		proteintertiary_rendering_button_mode_isselected = false;
	}
	
	private boolean inHandleProteinPrimaryRenderingMode = false;
	@Handles(raises = {})
	protected void handleProteinPrimaryRenderingModeRaiser(ProteinPrimaryRenderingModeRaiser raiser)
	{
		if(!inItemStateChanged && !inReset && !inHandleProteinPrimaryRenderingMode)
		{
			inHandleProteinPrimaryRenderingMode = true;
			proteintertiary_rendering_automatic_mode_isselected = raiser.isAutomaticallyRendered();
			if(null != proteintertiary_apply_rendering_button)
			{
				proteintertiary_apply_rendering_button.setEnabled(!proteintertiary_rendering_automatic_mode_isselected);
			}
			if(null != proteintertiary_rendering_automatic_mode)
			{
				proteintertiary_rendering_automatic_mode.setSelected(proteintertiary_rendering_automatic_mode_isselected);
			}
			if(null != proteintertiary_rendering_button_mode)
			{
				proteintertiary_rendering_button_mode.setSelected(!proteintertiary_rendering_automatic_mode_isselected);
			}
			inHandleProteinPrimaryRenderingMode = false;
		}
	}

	protected boolean default_proteintertiary_rendering_automatic_mode_isselected = true;
	protected boolean proteintertiary_rendering_automatic_mode_isselected = true;
	protected boolean proteintertiary_rendering_button_mode_isselected = false;
	
	protected String proteintertiary_rendering_automatic_mode_string = Messages.getString("ProteinTertiaryRenderingModePanel.2"); //$NON-NLS-1$
	protected String proteintertiary_rendering_button_mode_string = Messages.getString("ProteinTertiaryRenderingModePanel.3"); //$NON-NLS-1$
	protected String proteintertiary_apply_rendering_button_string = Messages.getString("ProteinTertiaryRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int proteintertiary_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int proteintertiary_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int proteintertiary_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteintertiary_rendering_button_mode_isselected", Boolean.toString(proteintertiary_rendering_button_mode_isselected)); //$NON-NLS-1$
		proteintertiary_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteintertiary_rendering_automatic_mode_isselected", Boolean.toString(proteintertiary_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_proteintertiary_rendering_automatic_mode_isselected = proteintertiary_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		proteintertiary_rendering_button_mode_string = getPreferences(preferencesName).get("proteintertiary_rendering_button_mode_string", proteintertiary_rendering_button_mode_string); //$NON-NLS-1$
		proteintertiary_rendering_automatic_mode_string = getPreferences(preferencesName).get("proteintertiary_rendering_automatic_mode_string", proteintertiary_rendering_automatic_mode_string); //$NON-NLS-1$
		proteintertiary_apply_rendering_button_string = getPreferences(preferencesName).get("proteintertiary_apply_rendering_button_string",proteintertiary_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteintertiary_rendering_button_mode_vkey", Integer.toString(proteintertiary_rendering_button_mode_vkey)); //$NON-NLS-1$
		proteintertiary_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteintertiary_rendering_automatic_mode_vkey", Integer.toString(proteintertiary_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		proteintertiary_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteintertiary_apply_rendering_button_vkey", Integer.toString(proteintertiary_apply_rendering_button_vkey)); //$NON-NLS-1$
		proteintertiary_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	public boolean isAutomaticallyRendered()
    {
		return proteintertiary_rendering_automatic_mode.isSelected();
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_proteintertiary_rendering_automatic_mode_isselected;
    }

}
