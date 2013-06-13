package molecule.ui.protein.quaternary;

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

import molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeRaiser;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {ProteinQuaternaryRenderingModeRaiser.class, ProteinQuaternaryApplyRenderingRaiser.class})
public class ProteinQuaternaryRenderingModePanel extends ProteinQuaternaryRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton proteinquaternary_rendering_automatic_mode = null;
	JRadioButton proteinquaternary_rendering_button_mode = null;
	JButton proteinquaternary_apply_rendering_button = null;
	
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

		proteinquaternary_rendering_automatic_mode = new JRadioButton(proteinquaternary_rendering_automatic_mode_string);
		proteinquaternary_rendering_automatic_mode.setMnemonic(proteinquaternary_rendering_automatic_mode_vkey);
		proteinquaternary_rendering_automatic_mode.setSelected(proteinquaternary_rendering_automatic_mode_isselected);

		proteinquaternary_rendering_button_mode = new JRadioButton(proteinquaternary_rendering_button_mode_string);
		proteinquaternary_rendering_button_mode.setMnemonic(proteinquaternary_rendering_button_mode_vkey);
		proteinquaternary_rendering_button_mode.setSelected(proteinquaternary_rendering_button_mode_isselected);

		proteinquaternary_apply_rendering_button = new JButton(proteinquaternary_apply_rendering_button_string);
		proteinquaternary_apply_rendering_button.setMnemonic(proteinquaternary_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(proteinquaternary_rendering_automatic_mode);
		group.add(proteinquaternary_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(proteinquaternary_rendering_automatic_mode);
		add(proteinquaternary_rendering_button_mode);
		add(proteinquaternary_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		proteinquaternary_rendering_automatic_mode.removeItemListener(this);
		proteinquaternary_rendering_button_mode.removeItemListener(this);
		proteinquaternary_apply_rendering_button.removeActionListener(this);
		proteinquaternary_rendering_automatic_mode = null;
		proteinquaternary_rendering_button_mode = null;
		proteinquaternary_apply_rendering_button = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_ProteinQuaternaryApplyRenderingEvent();
    }

	private boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(proteinquaternary_rendering_automatic_mode))
	        {
	        	this.proteinquaternary_rendering_automatic_mode_isselected = proteinquaternary_rendering_automatic_mode.isSelected();
	        	if(this.proteinquaternary_rendering_automatic_mode_isselected)
	        	{
	        		this.proteinquaternary_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(proteinquaternary_rendering_button_mode))
	        {
		        this.proteinquaternary_rendering_button_mode_isselected = proteinquaternary_rendering_button_mode.isSelected();
		        if(this.proteinquaternary_rendering_button_mode_isselected)
		        {
		        	this.proteinquaternary_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_ProteinQuaternaryRenderingModeEvent();
	        inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public synchronized void initTree()
	{
		if(!inItemStateChanged && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != proteinquaternary_rendering_automatic_mode)
			{
				proteinquaternary_rendering_automatic_mode.setSelected(default_proteinquaternary_rendering_automatic_mode_isselected);
				proteinquaternary_rendering_automatic_mode.addItemListener(this);
			}
			if(null != proteinquaternary_apply_rendering_button)
			{
				proteinquaternary_apply_rendering_button.setEnabled(default_proteinquaternary_rendering_button_mode_isselected);
				proteinquaternary_apply_rendering_button.addActionListener(this);
			}
			if(null != proteinquaternary_rendering_button_mode)
			{
				proteinquaternary_rendering_button_mode.addItemListener(this);
			}
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		proteinquaternary_rendering_automatic_mode_isselected = default_proteinquaternary_rendering_automatic_mode_isselected;
		proteinquaternary_rendering_button_mode_isselected = default_proteinquaternary_rendering_button_mode_isselected;
	}
	
	private boolean inReset = false;
	public synchronized void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteinquaternary_rendering_automatic_mode)
			{
				proteinquaternary_rendering_automatic_mode.setSelected(default_proteinquaternary_rendering_automatic_mode_isselected);
			}
			if(null != proteinquaternary_apply_rendering_button)
			{
				proteinquaternary_apply_rendering_button.setEnabled(default_proteinquaternary_rendering_button_mode_isselected);
			}
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		proteinquaternary_rendering_automatic_mode_isselected = default_proteinquaternary_rendering_automatic_mode_isselected;
		proteinquaternary_rendering_button_mode_isselected = default_proteinquaternary_rendering_button_mode_isselected;
	}
	
	protected boolean default_proteinquaternary_rendering_automatic_mode_isselected = true;
	protected boolean default_proteinquaternary_rendering_button_mode_isselected = false;
	
	protected boolean proteinquaternary_rendering_automatic_mode_isselected = true;
	protected boolean proteinquaternary_rendering_button_mode_isselected = false;
	
	protected String proteinquaternary_rendering_automatic_mode_string = Messages.getString("ProteinQuaternaryRenderingModePanel.2"); //$NON-NLS-1$
	protected String proteinquaternary_rendering_button_mode_string = Messages.getString("ProteinQuaternaryRenderingModePanel.3"); //$NON-NLS-1$
	protected String proteinquaternary_apply_rendering_button_string = Messages.getString("ProteinQuaternaryRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int proteinquaternary_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int proteinquaternary_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int proteinquaternary_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinquaternary_rendering_button_mode_isselected", Boolean.toString(proteinquaternary_rendering_button_mode_isselected)); //$NON-NLS-1$
		default_proteinquaternary_rendering_button_mode_isselected = proteinquaternary_rendering_button_mode_isselected = Boolean.parseBoolean(s);
		
		s = getPreferences(preferencesName).get("proteinquaternary_rendering_automatic_mode_isselected", Boolean.toString(proteinquaternary_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_proteinquaternary_rendering_automatic_mode_isselected = proteinquaternary_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);
		
		proteinquaternary_rendering_button_mode_string = getPreferences(preferencesName).get("proteinquaternary_rendering_button_mode_string", proteinquaternary_rendering_button_mode_string); //$NON-NLS-1$
		proteinquaternary_rendering_automatic_mode_string = getPreferences(preferencesName).get("proteinquaternary_rendering_automatic_mode_string", proteinquaternary_rendering_automatic_mode_string); //$NON-NLS-1$
		proteinquaternary_apply_rendering_button_string = getPreferences(preferencesName).get("proteinquaternary_apply_rendering_button_string",proteinquaternary_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteinquaternary_rendering_button_mode_vkey", Integer.toString(proteinquaternary_rendering_button_mode_vkey)); //$NON-NLS-1$
		proteinquaternary_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinquaternary_rendering_automatic_mode_vkey", Integer.toString(proteinquaternary_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		proteinquaternary_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinquaternary_apply_rendering_button_vkey", Integer.toString(proteinquaternary_apply_rendering_button_vkey)); //$NON-NLS-1$
		proteinquaternary_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	
	public boolean isAutomaticallyRendered()
    {
		return proteinquaternary_rendering_automatic_mode_isselected;
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_proteinquaternary_rendering_automatic_mode_isselected;
    }

}
