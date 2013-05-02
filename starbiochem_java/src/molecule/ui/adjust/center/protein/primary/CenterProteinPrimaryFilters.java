package molecule.ui.adjust.center.protein.primary;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;

import app.Messages;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {CenterProteinPrimaryFilterRaiser.class})
public class CenterProteinPrimaryFilters extends CenterProteinPrimaryFilters_generated  implements ItemListener
{
	private static final long serialVersionUID = 1L;

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
		loadPreferences("adjust_primary"); //$NON-NLS-1$
		
		proteinprimary_backbone = new JCheckBox(filters_proteinprimary_backbone_string);
		proteinprimary_backbone.setMnemonic(filters_proteinprimary_backbone_vkey);
		proteinprimary_backbone.setSelected(filters_proteinprimary_backbone_isselected);

		proteinprimary_sidechain = new JCheckBox(filters_proteinprimary_sidechain_string);
		proteinprimary_sidechain.setMnemonic(filters_proteinprimary_sidechain_vkey);
		proteinprimary_sidechain.setSelected(filters_proteinprimary_sidechain_isselected);

		setLayout(new FlowLayout());
		
		add(proteinprimary_backbone);
		add(proteinprimary_sidechain);
	}

	private void end()
	{
		if(null != proteinprimary_backbone)
		{
	        proteinprimary_backbone.removeItemListener(this);
		}
		if(null != proteinprimary_sidechain)
		{
	        proteinprimary_sidechain.removeItemListener(this);
		}
		removeAll();
		proteinprimary_backbone = null;
		proteinprimary_sidechain = null;
	}
	
	int filterOptions = 0;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	private void setFilterOptions()
	{
		this.filterOptions = 0;
        if (this.filters_proteinprimary_backbone_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE;
        }
        if (this.filters_proteinprimary_sidechain_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE;
	    }
	}
	
	transient private boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
			boolean isSourceBackbone = (source.hashCode() == proteinprimary_backbone.hashCode());
			boolean isSourceSidechain = (source.hashCode() == proteinprimary_sidechain.hashCode());
			if( isSourceBackbone || isSourceSidechain)
			{
		        if (isSourceBackbone)
			    {
		        	this.filters_proteinprimary_backbone_isselected = proteinprimary_backbone.isSelected();
		        }
		        if (isSourceSidechain)
		        {
		        	this.filters_proteinprimary_sidechain_isselected = proteinprimary_sidechain.isSelected();
		        }
				setFilterOptions();
		        this.raise_CenterProteinPrimaryFilterEvent();
			}
			inItemStateChanged = false;
		}
    }

	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			setFilterOptions();
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
			inInitTree = false;
		}
	}

	transient private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
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
			setFilterOptions();
			inReset = false;
		}
	}

	private void initLocalVariables()
	{
		filters_proteinprimary_backbone_isselected = true;
		filters_proteinprimary_sidechain_isselected = true;
	}
	
	private void resetLocalVariables()
	{
		filters_proteinprimary_backbone_isselected = true;
		filters_proteinprimary_sidechain_isselected = true;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE);
	}

	protected boolean filters_proteinprimary_backbone_isselected = true;
	protected boolean filters_proteinprimary_sidechain_isselected = true;
	
	protected String filters_proteinprimary_backbone_string = Messages.getString("CenterProteinPrimaryFilters.0"); //$NON-NLS-1$
	protected String filters_proteinprimary_sidechain_string = Messages.getString("CenterProteinPrimaryFilters.1"); //$NON-NLS-1$
		
	protected int filters_proteinprimary_backbone_vkey = KeyEvent.VK_B;
	protected int filters_proteinprimary_sidechain_vkey = KeyEvent.VK_S;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("filters_proteinprimary_backbone_isselected", Boolean.toString(filters_proteinprimary_backbone_isselected)); //$NON-NLS-1$
		filters_proteinprimary_backbone_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("filters_proteinprimary_sidechain_isselected", Boolean.toString(filters_proteinprimary_sidechain_isselected)); //$NON-NLS-1$
		filters_proteinprimary_sidechain_isselected = Boolean.parseBoolean(s);

		filters_proteinprimary_backbone_string = getPreferences(preferencesName).get("filters_proteinprimary_backbone_string", filters_proteinprimary_backbone_string); //$NON-NLS-1$
		filters_proteinprimary_sidechain_string = getPreferences(preferencesName).get("filters_proteinprimary_sidechain_string", filters_proteinprimary_sidechain_string); //$NON-NLS-1$

		try
		{
			s = getPreferences(preferencesName).get("filters_proteinprimary_backbone_vkey", KeyEvent.getKeyText(filters_proteinprimary_backbone_vkey)); //$NON-NLS-1$
			filters_proteinprimary_backbone_vkey = Integer.parseInt(s);
			
			s = getPreferences(preferencesName).get("filters_proteinprimary_sidechain_vkey", KeyEvent.getKeyText(filters_proteinprimary_sidechain_vkey)); //$NON-NLS-1$
			filters_proteinprimary_sidechain_vkey = Integer.parseInt(s);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
		}
	}

}
