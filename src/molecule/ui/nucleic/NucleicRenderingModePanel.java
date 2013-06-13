package molecule.ui.nucleic;

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

import molecule.ui.nucleic.signal.NucleicApplyRenderingRaiser;
import molecule.ui.nucleic.signal.NucleicRenderingModeRaiser;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {NucleicRenderingModeRaiser.class, NucleicApplyRenderingRaiser.class})
public class NucleicRenderingModePanel extends NucleicRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton nucleic_rendering_automatic_mode = null;
	JRadioButton nucleic_rendering_button_mode = null;
	JButton nucleic_apply_rendering_button = null;
	
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
		loadPreferences("nucleic"); //$NON-NLS-1$
		
		setBorder(new TitledBorder("")); //$NON-NLS-1$

		nucleic_rendering_automatic_mode = new JRadioButton(nucleic_rendering_automatic_mode_string);
		nucleic_rendering_automatic_mode.setMnemonic(nucleic_rendering_automatic_mode_vkey);
		nucleic_rendering_automatic_mode.setSelected(nucleic_rendering_automatic_mode_isselected);

		nucleic_rendering_button_mode = new JRadioButton(nucleic_rendering_button_mode_string);
		nucleic_rendering_button_mode.setMnemonic(nucleic_rendering_button_mode_vkey);
		nucleic_rendering_button_mode.setSelected(nucleic_rendering_button_mode_isselected);

		nucleic_apply_rendering_button = new JButton(nucleic_apply_rendering_button_string);
		nucleic_apply_rendering_button.setMnemonic(nucleic_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(nucleic_rendering_automatic_mode);
		group.add(nucleic_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(nucleic_rendering_automatic_mode);
		add(nucleic_rendering_button_mode);
		add(nucleic_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		nucleic_rendering_automatic_mode.removeItemListener(this);
		nucleic_rendering_button_mode.removeItemListener(this);
		nucleic_apply_rendering_button.removeActionListener(this);
		nucleic_rendering_automatic_mode = null;
		nucleic_rendering_button_mode = null;
		nucleic_apply_rendering_button = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_NucleicApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(nucleic_rendering_automatic_mode))
	        {
	        	this.nucleic_rendering_automatic_mode_isselected = nucleic_rendering_automatic_mode.isSelected();
	        	if(this.nucleic_rendering_automatic_mode_isselected)
	        	{
	        		this.nucleic_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(nucleic_rendering_button_mode))
	        {
		        this.nucleic_rendering_button_mode_isselected = nucleic_rendering_button_mode.isSelected();
		        if(this.nucleic_rendering_button_mode_isselected)
		        {
		        	this.nucleic_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_NucleicRenderingModeEvent();
	        inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != nucleic_rendering_automatic_mode)
			{
				nucleic_rendering_automatic_mode.setSelected(true);
				nucleic_rendering_automatic_mode.addItemListener(this);
			}
			if(null != nucleic_apply_rendering_button)
			{
				nucleic_apply_rendering_button.setEnabled(false);
				nucleic_apply_rendering_button.addActionListener(this);
			}
			if(null != nucleic_rendering_button_mode)
			{
				nucleic_rendering_button_mode.addItemListener(this);
			}
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		nucleic_rendering_automatic_mode_isselected = true;
		nucleic_rendering_button_mode_isselected = false;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != nucleic_rendering_automatic_mode)
			{
				nucleic_rendering_automatic_mode.setSelected(true);
			}
			if(null != nucleic_apply_rendering_button)
			{
				nucleic_apply_rendering_button.setEnabled(false);
			}
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		nucleic_rendering_automatic_mode_isselected = true;
		nucleic_rendering_button_mode_isselected = false;
	}
	
	protected boolean default_nucleic_rendering_automatic_mode_isselected = true;
	protected boolean nucleic_rendering_automatic_mode_isselected = true;
	protected boolean nucleic_rendering_button_mode_isselected = false;
	
	protected String nucleic_rendering_automatic_mode_string = Messages.getString("NucleicRenderingModePanel.2"); //$NON-NLS-1$
	protected String nucleic_rendering_button_mode_string = Messages.getString("NucleicRenderingModePanel.3"); //$NON-NLS-1$
	protected String nucleic_apply_rendering_button_string = Messages.getString("NucleicRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int nucleic_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int nucleic_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int nucleic_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("nucleic_rendering_button_mode_isselected", Boolean.toString(nucleic_rendering_button_mode_isselected)); //$NON-NLS-1$
		nucleic_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("nucleic_rendering_automatic_mode_isselected", Boolean.toString(nucleic_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_nucleic_rendering_automatic_mode_isselected = nucleic_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		nucleic_rendering_button_mode_string = getPreferences(preferencesName).get("nucleic_rendering_button_mode_string", nucleic_rendering_button_mode_string); //$NON-NLS-1$
		nucleic_rendering_automatic_mode_string = getPreferences(preferencesName).get("nucleic_rendering_automatic_mode_string", nucleic_rendering_automatic_mode_string); //$NON-NLS-1$
		nucleic_apply_rendering_button_string = getPreferences(preferencesName).get("nucleic_apply_rendering_button_string",nucleic_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("nucleic_rendering_button_mode_vkey", Integer.toString(nucleic_rendering_button_mode_vkey)); //$NON-NLS-1$
		nucleic_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("nucleic_rendering_automatic_mode_vkey", Integer.toString(nucleic_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		nucleic_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("nucleic_apply_rendering_button_vkey", Integer.toString(nucleic_apply_rendering_button_vkey)); //$NON-NLS-1$
		nucleic_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	
	public boolean isAutomaticallyRendered()
    {
		return nucleic_rendering_automatic_mode_isselected;
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_nucleic_rendering_automatic_mode_isselected;
    }

}
