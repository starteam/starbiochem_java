package molecule.ui.protein.secondary;

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

import molecule.ui.protein.secondary.signal.ProteinSecondaryApplyRenderingRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondaryRenderingModeRaiser;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {ProteinSecondaryRenderingModeRaiser.class, ProteinSecondaryApplyRenderingRaiser.class})
public class ProteinSecondaryRenderingModePanel extends ProteinSecondaryRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton proteinsecondary_rendering_automatic_mode = null;
	JRadioButton proteinsecondary_rendering_button_mode = null;
	JButton proteinsecondary_apply_rendering_button = null;
	
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

		proteinsecondary_rendering_automatic_mode = new JRadioButton(proteinsecondary_rendering_automatic_mode_string);
		proteinsecondary_rendering_automatic_mode.setMnemonic(proteinsecondary_rendering_automatic_mode_vkey);
		proteinsecondary_rendering_automatic_mode.setSelected(proteinsecondary_rendering_automatic_mode_isselected);

		proteinsecondary_rendering_button_mode = new JRadioButton(proteinsecondary_rendering_button_mode_string);
		proteinsecondary_rendering_button_mode.setMnemonic(proteinsecondary_rendering_button_mode_vkey);
		proteinsecondary_rendering_button_mode.setSelected(proteinsecondary_rendering_button_mode_isselected);

		proteinsecondary_apply_rendering_button = new JButton(proteinsecondary_apply_rendering_button_string);
		proteinsecondary_apply_rendering_button.setMnemonic(proteinsecondary_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(proteinsecondary_rendering_automatic_mode);
		group.add(proteinsecondary_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(proteinsecondary_rendering_automatic_mode);
		add(proteinsecondary_rendering_button_mode);
		add(proteinsecondary_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		proteinsecondary_rendering_automatic_mode.removeItemListener(this);
		proteinsecondary_rendering_button_mode.removeItemListener(this);
		proteinsecondary_apply_rendering_button.removeActionListener(this);
		proteinsecondary_rendering_automatic_mode = null;
		proteinsecondary_rendering_button_mode = null;
		proteinsecondary_apply_rendering_button = null;
		
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_ProteinSecondaryApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(proteinsecondary_rendering_automatic_mode))
	        {
	        	this.proteinsecondary_rendering_automatic_mode_isselected = proteinsecondary_rendering_automatic_mode.isSelected();
	        	if(this.proteinsecondary_rendering_automatic_mode_isselected)
	        	{
	        		this.proteinsecondary_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(proteinsecondary_rendering_button_mode))
	        {
		        this.proteinsecondary_rendering_button_mode_isselected = proteinsecondary_rendering_button_mode.isSelected();
		        if(this.proteinsecondary_rendering_button_mode_isselected)
		        {
		        	this.proteinsecondary_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_ProteinSecondaryRenderingModeEvent();
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
			if(null != proteinsecondary_rendering_automatic_mode)
			{
				proteinsecondary_rendering_automatic_mode.setSelected(true);
				proteinsecondary_rendering_automatic_mode.addItemListener(this);
			}
			if(null != proteinsecondary_apply_rendering_button)
			{
				proteinsecondary_apply_rendering_button.setEnabled(false);
				proteinsecondary_apply_rendering_button.addActionListener(this);
			}
			if(null != proteinsecondary_rendering_button_mode)
			{
				proteinsecondary_rendering_button_mode.addItemListener(this);
			}
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		proteinsecondary_rendering_automatic_mode_isselected = true;
		proteinsecondary_rendering_button_mode_isselected = false;
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteinsecondary_rendering_automatic_mode)
			{
				proteinsecondary_rendering_automatic_mode.setSelected(true);
			}
			if(null != proteinsecondary_apply_rendering_button)
			{
				proteinsecondary_apply_rendering_button.setEnabled(false);
			}
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		proteinsecondary_rendering_automatic_mode_isselected = true;
		proteinsecondary_rendering_button_mode_isselected = false;
	}

	protected boolean default_proteinsecondary_rendering_automatic_mode_isselected = true;
	protected boolean proteinsecondary_rendering_automatic_mode_isselected = true;
	protected boolean proteinsecondary_rendering_button_mode_isselected = false;
	
	protected String proteinsecondary_rendering_automatic_mode_string = Messages.getString("ProteinSecondaryRenderingModePanel.2"); //$NON-NLS-1$
	protected String proteinsecondary_rendering_button_mode_string = Messages.getString("ProteinSecondaryRenderingModePanel.3"); //$NON-NLS-1$
	protected String proteinsecondary_apply_rendering_button_string = Messages.getString("ProteinSecondaryRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int proteinsecondary_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int proteinsecondary_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int proteinsecondary_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinsecondary_rendering_button_mode_isselected", Boolean.toString(proteinsecondary_rendering_button_mode_isselected)); //$NON-NLS-1$
		proteinsecondary_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinsecondary_rendering_automatic_mode_isselected", Boolean.toString(proteinsecondary_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_proteinsecondary_rendering_automatic_mode_isselected = proteinsecondary_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		proteinsecondary_rendering_button_mode_string = getPreferences(preferencesName).get("proteinsecondary_rendering_button_mode_string", proteinsecondary_rendering_button_mode_string); //$NON-NLS-1$
		proteinsecondary_rendering_automatic_mode_string = getPreferences(preferencesName).get("proteinsecondary_rendering_automatic_mode_string", proteinsecondary_rendering_automatic_mode_string); //$NON-NLS-1$
		proteinsecondary_apply_rendering_button_string = getPreferences(preferencesName).get("proteinsecondary_apply_rendering_button_string",proteinsecondary_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteinsecondary_rendering_button_mode_vkey", Integer.toString(proteinsecondary_rendering_button_mode_vkey)); //$NON-NLS-1$
		proteinsecondary_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinsecondary_rendering_automatic_mode_vkey", Integer.toString(proteinsecondary_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		proteinsecondary_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinsecondary_apply_rendering_button_vkey", Integer.toString(proteinsecondary_apply_rendering_button_vkey)); //$NON-NLS-1$
		proteinsecondary_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	
	public boolean isAutomaticallyRendered()
    {
		return proteinsecondary_rendering_automatic_mode_isselected;
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_proteinsecondary_rendering_automatic_mode_isselected;
    }

}
