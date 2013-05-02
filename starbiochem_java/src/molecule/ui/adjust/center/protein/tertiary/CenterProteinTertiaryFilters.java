package molecule.ui.adjust.center.protein.tertiary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {CenterProteinTertiaryFilterRaiser.class})
public class CenterProteinTertiaryFilters extends CenterProteinTertiaryFilters_generated  implements ItemListener
{
	private static final long serialVersionUID = 1L;

	JCheckBox proteintertiary_backbone = null;
	JCheckBox proteintertiary_sidechain = null;
	
	JRadioButton tertiary_none = null;
	JRadioButton nonpolar_hydrophobic = null;
	JRadioButton polar_hydrophilic = null;
	JRadioButton buried = null;
	JRadioButton surface = null;
	JRadioButton positivelycharged_basic = null;
	JRadioButton negativelycharged_acidic = null;
	JRadioButton notcharged_neutral = null;
	
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
		loadPreferences("adjust_tertiary"); //$NON-NLS-1$
		
		proteintertiary_backbone = new JCheckBox(proteintertiary_backbone_string);
		proteintertiary_backbone.setMnemonic(proteintertiary_backbone_vkey);
		proteintertiary_backbone.setSelected(proteintertiary_backbone_isselected);

		proteintertiary_sidechain = new JCheckBox(proteintertiary_sidechain_string);
		proteintertiary_sidechain.setMnemonic(proteintertiary_sidechain_vkey);
		proteintertiary_sidechain.setSelected(proteintertiary_sidechain_isselected);

		tertiary_none = new JRadioButton(tertiary_none_string);
		tertiary_none.setMnemonic(tertiary_none_vkey);
		tertiary_none.setSelected(tertiary_none_isselected);

		nonpolar_hydrophobic = new JRadioButton(nonpolar_hydrophobic_string);
		nonpolar_hydrophobic.setMnemonic(nonpolar_hydrophobic_vkey);
		nonpolar_hydrophobic.setSelected(nonpolar_hydrophobic_isselected);

		polar_hydrophilic = new JRadioButton(polar_hydrophilic_string);
		polar_hydrophilic.setMnemonic(polar_hydrophilic_vkey);
		polar_hydrophilic.setSelected(polar_hydrophilic_isselected);
		
		buried = new JRadioButton(buried_string);
		buried.setMnemonic(buried_vkey);
		buried.setSelected(buried_isselected);
		
		surface = new JRadioButton(surface_string);
		surface.setMnemonic(surface_vkey);
		surface.setSelected(surface_isselected);
		
		positivelycharged_basic = new JRadioButton(positivelycharged_basic_string);
		positivelycharged_basic.setMnemonic(positivelycharged_basic_vkey);
		positivelycharged_basic.setSelected(positivelycharged_basic_isselected);
		
		negativelycharged_acidic = new JRadioButton(negativelycharged_acidic_string);
		negativelycharged_acidic.setMnemonic(negativelycharged_acidic_vkey);
		negativelycharged_acidic.setSelected(negativelycharged_acidic_isselected);
		
		notcharged_neutral = new JRadioButton(notcharged_neutral_string);
		notcharged_neutral.setMnemonic(notcharged_neutral_vkey);
		notcharged_neutral.setSelected(notcharged_neutral_isselected);
		
		ButtonGroup group = new ButtonGroup();
		group.add(tertiary_none);
		group.add(nonpolar_hydrophobic);
		group.add(polar_hydrophilic);
		group.add(buried);
		group.add(surface);
		group.add(positivelycharged_basic);
		group.add(negativelycharged_acidic);
		group.add(notcharged_neutral);

        JPanel parts = new JPanel();
		parts.setLayout(new FlowLayout());
		
		parts.add(proteintertiary_backbone);
		parts.add(proteintertiary_sidechain);
		
        JPanel colors = new JPanel();
		GridLayout gridlayout = new GridLayout(4,2,4,2);
		gridlayout.setHgap(0);
		colors.setLayout(gridlayout);
		
		colors.add(nonpolar_hydrophobic);		colors.add(notcharged_neutral);
		colors.add(polar_hydrophilic);			colors.add(positivelycharged_basic);
		colors.add(surface);						colors.add(negativelycharged_acidic);
		colors.add(buried);						colors.add(tertiary_none);
		
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, parts);
		add(BorderLayout.WEST, colors);
		getLayout().minimumLayoutSize(colors);
		getLayout().minimumLayoutSize(this);
	}
	
	private void end()
	{
		removeAll();
		proteintertiary_backbone.removeItemListener(this);
		proteintertiary_sidechain.removeItemListener(this);
		
		tertiary_none.removeItemListener(this);
		nonpolar_hydrophobic.removeItemListener(this);
		polar_hydrophilic.removeItemListener(this);
		buried.removeItemListener(this);
		surface.removeItemListener(this);
		positivelycharged_basic.removeItemListener(this);
		negativelycharged_acidic.removeItemListener(this);
		notcharged_neutral.removeItemListener(this);

		proteintertiary_backbone = null;
		proteintertiary_sidechain = null;
		
		tertiary_none = null;
		nonpolar_hydrophobic = null;
		polar_hydrophilic = null;
		buried = null;
		surface = null;
		positivelycharged_basic = null;
		negativelycharged_acidic = null;
		notcharged_neutral = null;
	}
	
	int filterOptions = 0;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	private void setFilterOptions()
	{
		this.filterOptions = 0;
        if (this.proteintertiary_backbone_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE;
        }
        if (this.proteintertiary_sidechain_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE;
	    }
		if(tertiary_none.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.TERTIARY_NONE;
		}
		else if(nonpolar_hydrophobic.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE;
        }
		else if(polar_hydrophilic.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE;
        }
		else if(buried.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BURIED_VISIBLE;
        }
		else if(surface.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.SURFACE_VISIBLE;
        }
		else if(positivelycharged_basic.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE;
        }
		else if(negativelycharged_acidic.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE;
        }
		else if(notcharged_neutral.isSelected())
		{
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE;
        }
	}
	
	transient boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
			if(null != source)
			{
				int sourceHashCode = source.hashCode();
				boolean isSourceBackbone = (sourceHashCode == proteintertiary_backbone.hashCode());
				boolean isSourceSidechain = (sourceHashCode == proteintertiary_sidechain.hashCode());
				if( isSourceBackbone || isSourceSidechain)
				{
			        if (isSourceBackbone)
				    {
			        	this.proteintertiary_backbone_isselected = proteintertiary_backbone.isSelected();
			        }
			        if (isSourceSidechain)
			        {
			        	this.proteintertiary_sidechain_isselected = proteintertiary_sidechain.isSelected();
			        }
				}
		        if (sourceHashCode == tertiary_none.hashCode())
		        {
		        	this.tertiary_none_isselected = tertiary_none.isSelected();
		        }
		        else if (sourceHashCode == nonpolar_hydrophobic.hashCode())
		        {
		        	this.nonpolar_hydrophobic_isselected = nonpolar_hydrophobic.isSelected();
		        }
		        else if (sourceHashCode == polar_hydrophilic.hashCode())
		        {
		        	this.polar_hydrophilic_isselected = polar_hydrophilic.isSelected();
		        }
		        else if (sourceHashCode == notcharged_neutral.hashCode())
		        {
		        	this.notcharged_neutral_isselected = notcharged_neutral.isSelected();
		        }
		        else if (sourceHashCode == positivelycharged_basic.hashCode())
		        {
		        	this.positivelycharged_basic_isselected = positivelycharged_basic.isSelected();
		        }
		        else if (sourceHashCode == negativelycharged_acidic.hashCode())
		        {
		        	this.negativelycharged_acidic_isselected = negativelycharged_acidic.isSelected();
		        }
		        else if (sourceHashCode == buried.hashCode())
		        {
		        	this.buried_isselected = buried.isSelected();
		        }
		        else if (sourceHashCode == surface.hashCode())
		        {
		        	this.surface_isselected = surface.isSelected();
		        }
		        
				setFilterOptions();
				this.raise_CenterProteinTertiaryFilterEvent();
			}
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
			if(null != proteintertiary_backbone)
			{
				proteintertiary_backbone.setSelected(true);
		        proteintertiary_backbone.addItemListener(this);
			}
			if(null != proteintertiary_sidechain)
			{
				proteintertiary_sidechain.setSelected(true);
		        proteintertiary_sidechain.addItemListener(this);
			}
	        if (null != tertiary_none)
	        {
	        	tertiary_none.setSelected(true);
				tertiary_none.addItemListener(this);
	        }
	        if (null != nonpolar_hydrophobic)
	        {
	        	nonpolar_hydrophobic.setSelected(false);
				nonpolar_hydrophobic.addItemListener(this);
	        }
	        if (null != notcharged_neutral)
	        {
	        	polar_hydrophilic.setSelected(false);
				polar_hydrophilic.addItemListener(this);
	        }
	        if (null != notcharged_neutral)
	        {
	        	notcharged_neutral.setSelected(false);
				notcharged_neutral.addItemListener(this);
	        }
	        if (null != positivelycharged_basic)
	        {
	        	positivelycharged_basic.setSelected(false);
				positivelycharged_basic.addItemListener(this);
	        }
	        if (null != negativelycharged_acidic)
	        {
	        	negativelycharged_acidic.setSelected(false);
				negativelycharged_acidic.addItemListener(this);
	        }
	        if (null != buried)
	        {
	        	buried.setSelected(false);
				buried.addItemListener(this);
	        }
	        if (null != surface)
	        {
	        	surface.setSelected(false);
				surface.addItemListener(this);
	        }
			setFilterOptions();
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		proteintertiary_backbone_isselected = true;
		proteintertiary_sidechain_isselected = true;
		tertiary_none_isselected = true;
		nonpolar_hydrophobic_isselected = false;
		polar_hydrophilic_isselected = false;
		notcharged_neutral_isselected = false;
		positivelycharged_basic_isselected = false;
		negativelycharged_acidic_isselected = false;
		buried_isselected = false;
		surface_isselected = false;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteintertiary_backbone)
			{
				proteintertiary_backbone.setSelected(true);
			}
			if(null != proteintertiary_sidechain)
			{
				proteintertiary_sidechain.setSelected(true);
			}
	        if (null != tertiary_none)
	        {
	        	tertiary_none.setSelected(true);
	        }
	        if (null != nonpolar_hydrophobic)
	        {
	        	nonpolar_hydrophobic.setSelected(false);
	        }
	        if (null != notcharged_neutral)
	        {
	        	polar_hydrophilic.setSelected(false);
	        }
	        if (null != notcharged_neutral)
	        {
	        	notcharged_neutral.setSelected(false);
	        }
	        if (null != positivelycharged_basic)
	        {
	        	positivelycharged_basic.setSelected(false);
	        }
	        if (null != negativelycharged_acidic)
	        {
	        	negativelycharged_acidic.setSelected(false);
	        }
	        if (null != buried)
	        {
	        	buried.setSelected(false);
	        }
	        if (null != surface)
	        {
	        	surface.setSelected(false);
	        }
			setFilterOptions();
	        inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		proteintertiary_backbone_isselected = true;
		proteintertiary_sidechain_isselected = true;
		tertiary_none_isselected = true;
		nonpolar_hydrophobic_isselected = false;
		polar_hydrophilic_isselected = false;
		notcharged_neutral_isselected = false;
		positivelycharged_basic_isselected = false;
		negativelycharged_acidic_isselected = false;
		buried_isselected = false;
		surface_isselected = false;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.TERTIARY_NONE | RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE);
	}

	protected boolean proteintertiary_backbone_isselected = true;
	protected boolean proteintertiary_sidechain_isselected = true;
	
	protected String proteintertiary_backbone_string = Messages.getString("CenterProteinTertiaryFilters.0"); //$NON-NLS-1$
	protected String proteintertiary_sidechain_string = Messages.getString("CenterProteinTertiaryFilters.1"); //$NON-NLS-1$
		
	protected int proteintertiary_backbone_vkey = KeyEvent.VK_B;
	protected int proteintertiary_sidechain_vkey = KeyEvent.VK_S;
	
	protected boolean tertiary_none_isselected = true;
	protected boolean nonpolar_hydrophobic_isselected = false;
	protected boolean polar_hydrophilic_isselected = false;
	protected boolean notcharged_neutral_isselected = false;
	protected boolean positivelycharged_basic_isselected = false;
	protected boolean negativelycharged_acidic_isselected = false;
	protected boolean buried_isselected = false;
	protected boolean surface_isselected = false;
	
	protected String tertiary_none_string = Messages.getString("CenterProteinTertiaryFilters.3"); //$NON-NLS-1$
	protected String nonpolar_hydrophobic_string = Messages.getString("CenterProteinTertiaryFilters.4"); //$NON-NLS-1$
	protected String polar_hydrophilic_string = Messages.getString("CenterProteinTertiaryFilters.5"); //$NON-NLS-1$
	protected String notcharged_neutral_string = Messages.getString("CenterProteinTertiaryFilters.6"); //$NON-NLS-1$
	protected String positivelycharged_basic_string = Messages.getString("CenterProteinTertiaryFilters.7"); //$NON-NLS-1$
	protected String negativelycharged_acidic_string = Messages.getString("CenterProteinTertiaryFilters.8"); //$NON-NLS-1$
	protected String buried_string = Messages.getString("CenterProteinTertiaryFilters.9"); //$NON-NLS-1$
	protected String surface_string = Messages.getString("CenterProteinTertiaryFilters.10"); //$NON-NLS-1$

	protected int tertiary_none_vkey = KeyEvent.VK_0;
	protected int nonpolar_hydrophobic_vkey = KeyEvent.VK_N;
	protected int polar_hydrophilic_vkey = KeyEvent.VK_P;
	protected int notcharged_neutral_vkey = KeyEvent.VK_E;
	protected int positivelycharged_basic_vkey = KeyEvent.VK_B;
	protected int negativelycharged_acidic_vkey = KeyEvent.VK_A;
	protected int buried_vkey = KeyEvent.VK_R;
	protected int surface_vkey = KeyEvent.VK_F;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteintertiary_backbone_isselected", Boolean.toString(proteintertiary_backbone_isselected)); //$NON-NLS-1$
		proteintertiary_backbone_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteintertiary_sidechain_isselected", Boolean.toString(proteintertiary_sidechain_isselected)); //$NON-NLS-1$
		proteintertiary_sidechain_isselected = Boolean.parseBoolean(s);

		proteintertiary_backbone_string = getPreferences(preferencesName).get("proteintertiary_backbone_string", proteintertiary_backbone_string); //$NON-NLS-1$
		proteintertiary_sidechain_string = getPreferences(preferencesName).get("proteintertiary_sidechain_string", proteintertiary_sidechain_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteintertiary_backbone_vkey", Integer.toString(proteintertiary_backbone_vkey)); //$NON-NLS-1$
		proteintertiary_backbone_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteintertiary_sidechain_vkey", Integer.toString(proteintertiary_sidechain_vkey)); //$NON-NLS-1$
		proteintertiary_sidechain_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("tertiary_none_isselected", Boolean.toString(tertiary_none_isselected)); //$NON-NLS-1$
		tertiary_none_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("nonpolar_hydrophobic_isselected", Boolean.toString(nonpolar_hydrophobic_isselected)); //$NON-NLS-1$
		nonpolar_hydrophobic_isselected = Boolean.parseBoolean(s);
	
		s = getPreferences(preferencesName).get("polar_hydrophilic_isselected", Boolean.toString(polar_hydrophilic_isselected)); //$NON-NLS-1$
		polar_hydrophilic_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("notcharged_neutral_isselected", Boolean.toString(polar_hydrophilic_isselected)); //$NON-NLS-1$
		polar_hydrophilic_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("positivelycharged_basic_isselected", Boolean.toString(positivelycharged_basic_isselected)); //$NON-NLS-1$
		positivelycharged_basic_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("negativelycharged_acidic_isselected", Boolean.toString(negativelycharged_acidic_isselected)); //$NON-NLS-1$
		negativelycharged_acidic_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("buried_isselected", Boolean.toString(buried_isselected)); //$NON-NLS-1$
		buried_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("surface_isselected", Boolean.toString(surface_isselected)); //$NON-NLS-1$
		surface_isselected = Boolean.parseBoolean(s);

		tertiary_none_string = getPreferences(preferencesName).get("tertiary_none_string", tertiary_none_string); //$NON-NLS-1$
		nonpolar_hydrophobic_string = getPreferences(preferencesName).get("nonpolar_hydrophobic_string", nonpolar_hydrophobic_string); //$NON-NLS-1$
		polar_hydrophilic_string = getPreferences(preferencesName).get("polar_hydrophilic_string", polar_hydrophilic_string); //$NON-NLS-1$
		notcharged_neutral_string = getPreferences(preferencesName).get("notcharged_neutral_string", notcharged_neutral_string); //$NON-NLS-1$
		positivelycharged_basic_string = getPreferences(preferencesName).get("positivelycharged_basic_string", positivelycharged_basic_string); //$NON-NLS-1$
		negativelycharged_acidic_string = getPreferences(preferencesName).get("negativelycharged_acidic_string", negativelycharged_acidic_string); //$NON-NLS-1$
		buried_string = getPreferences(preferencesName).get("buried_string", buried_string); //$NON-NLS-1$
		surface_string = getPreferences(preferencesName).get("surface_string", surface_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("tertiary_none_vkey", Integer.toString(tertiary_none_vkey)); //$NON-NLS-1$
		tertiary_none_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("nonpolar_hydrophobic_vkey", Integer.toString(nonpolar_hydrophobic_vkey)); //$NON-NLS-1$
		nonpolar_hydrophobic_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("polar_hydrophilic_vkey", Integer.toString(polar_hydrophilic_vkey)); //$NON-NLS-1$
		polar_hydrophilic_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("notcharged_neutral_vkey", Integer.toString(notcharged_neutral_vkey)); //$NON-NLS-1$
		positivelycharged_basic_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("positivelycharged_basic_vkey", Integer.toString(positivelycharged_basic_vkey)); //$NON-NLS-1$
		positivelycharged_basic_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("negativelycharged_acidic_vkey", Integer.toString(negativelycharged_acidic_vkey)); //$NON-NLS-1$
		negativelycharged_acidic_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("buried_vkey", Integer.toString(buried_vkey)); //$NON-NLS-1$
		buried_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("surface_vkey", Integer.toString(surface_vkey)); //$NON-NLS-1$
		surface_vkey = Integer.parseInt(s);

	}

}
