package molecule.ui.protein.primary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.protein.primary.signal.ProteinPrimaryFilterRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {ProteinPrimaryFilterRaiser.class})
public class ProteinPrimaryFilters extends ProteinPrimaryFilters_generated  implements ItemListener
{
	private static final long serialVersionUID = 1L;

	ProteinPrimaryRenderingModePanel modes = null;
	
	JCheckBox proteinprimary_backbone = null;
	JCheckBox proteinprimary_sidechain = null;
	
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
		
		proteinprimary_backbone = new JCheckBox(proteinprimary_backbone_string);
		proteinprimary_backbone.setMnemonic(proteinprimary_backbone_vkey);
		proteinprimary_backbone.setSelected(proteinprimary_backbone_isselected);

		proteinprimary_sidechain = new JCheckBox(proteinprimary_sidechain_string);
		proteinprimary_sidechain.setMnemonic(proteinprimary_sidechain_vkey);
		proteinprimary_sidechain.setSelected(proteinprimary_sidechain_isselected);

        JPanel parts = new JPanel();
		parts.setLayout(new FlowLayout());
		
		parts.add(proteinprimary_backbone);
		parts.add(proteinprimary_sidechain);
	
		modes = new ProteinPrimaryRenderingModePanel();

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, parts);
		add(BorderLayout.EAST, modes);
	}
	
	private void end()
	{
        proteinprimary_backbone.removeItemListener(this);
        proteinprimary_sidechain.removeItemListener(this);
		removeAll();
		proteinprimary_backbone = null;
		proteinprimary_sidechain = null;
	}

	int value = 0;
	public int getValue()
	{
		return this.value;
	}
	private void setValue()
	{
		this.value = 0;
        if (this.proteinprimary_backbone_isselected)
        {
        	this.value |= molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE;
        }
        if (this.proteinprimary_sidechain_isselected)
        {
        	this.value |= molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE;
	    }
	}
	
	transient private boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inHandleProteinTertiaryFilterRaiser && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
			boolean isSourceBackbone = (source.hashCode() == proteinprimary_backbone.hashCode());
			boolean isSourceSidechain = (source.hashCode() == proteinprimary_sidechain.hashCode());
			if( isSourceBackbone || isSourceSidechain)
			{
		        if (isSourceBackbone)
			    {
		        	this.proteinprimary_backbone_isselected = proteinprimary_backbone.isSelected();
		        }
		        if (isSourceSidechain)
		        {
		        	this.proteinprimary_sidechain_isselected = proteinprimary_sidechain.isSelected();
		        }
				setValue();
		        this.raise_ProteinPrimaryFilterEvent();
			}
			inItemStateChanged = false;
		}
    }

	transient private boolean inHandleProteinTertiaryFilterRaiser = false;
	@Handles(raises = {})
	protected void handleProteinTertiaryFilterRaiser(ProteinTertiaryFilterRaiser raiser)
	{
		if(!inHandleProteinTertiaryFilterRaiser && !inItemStateChanged && !inReset)
		{
			inHandleProteinTertiaryFilterRaiser = true;
			proteinprimary_backbone_isselected = (0 != (raiser.getFilterOptions() & molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE));
    		proteinprimary_backbone.setSelected(proteinprimary_backbone_isselected);
			proteinprimary_sidechain_isselected = (0 != (raiser.getFilterOptions() & molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE));
    		proteinprimary_sidechain.setSelected(proteinprimary_sidechain_isselected);
    		setValue();
    		inHandleProteinTertiaryFilterRaiser = false;
		}
	}

	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inHandleProteinTertiaryFilterRaiser && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != proteinprimary_backbone)
			{
				proteinprimary_backbone.setSelected(true);
		        proteinprimary_backbone.addItemListener(this);
			}
			if(null != proteinprimary_sidechain)
			{
				proteinprimary_sidechain.setSelected(true);
		        proteinprimary_sidechain.addItemListener(this);
			}
			if(null != modes)
			{
				modes.initTree();
			}
			setValue();
	        this.raise_ProteinPrimaryFilterEvent();
	        inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		proteinprimary_backbone_isselected = true;
		proteinprimary_sidechain_isselected = true;
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inHandleProteinTertiaryFilterRaiser && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteinprimary_backbone)
			{
				proteinprimary_backbone.setSelected(true);
			}
			if(null != proteinprimary_sidechain)
			{
				proteinprimary_sidechain.setSelected(true);
			}
			if(null != modes)
			{
				modes.reset();
			}
			setValue();
	        this.raise_ProteinPrimaryFilterEvent();
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		proteinprimary_backbone_isselected = true;
		proteinprimary_sidechain_isselected = true;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE);
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != modes)
		{
			return modes.isDefaultAutomaticallyRendered();
		}
		return true;
	}
	
	protected boolean proteinprimary_backbone_isselected = true;
	protected boolean proteinprimary_sidechain_isselected = true;
	
	protected String proteinprimary_backbone_string = Messages.getString("ProteinPrimaryFilters.0"); //$NON-NLS-1$
	protected String proteinprimary_sidechain_string = Messages.getString("ProteinPrimaryFilters.1"); //$NON-NLS-1$
		
	protected int proteinprimary_backbone_vkey = KeyEvent.VK_B;
	protected int proteinprimary_sidechain_vkey = KeyEvent.VK_S;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinprimary_backbone_isselected", Boolean.toString(proteinprimary_backbone_isselected)); //$NON-NLS-1$
		proteinprimary_backbone_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinprimary_sidechain_isselected", Boolean.toString(proteinprimary_sidechain_isselected)); //$NON-NLS-1$
		proteinprimary_sidechain_isselected = Boolean.parseBoolean(s);

		proteinprimary_backbone_string = getPreferences(preferencesName).get("proteinprimary_backbone_string", proteinprimary_backbone_string); //$NON-NLS-1$
		proteinprimary_sidechain_string = getPreferences(preferencesName).get("proteinprimary_sidechain_string", proteinprimary_sidechain_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteinprimary_backbone_vkey", Integer.toString(proteinprimary_backbone_vkey)); //$NON-NLS-1$
		proteinprimary_backbone_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinprimary_sidechain_vkey", Integer.toString(proteinprimary_sidechain_vkey)); //$NON-NLS-1$
		proteinprimary_sidechain_vkey = Integer.parseInt(s);

	}

}
