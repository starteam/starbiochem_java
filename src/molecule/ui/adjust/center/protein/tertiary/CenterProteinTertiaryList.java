package molecule.ui.adjust.center.protein.tertiary;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.interfaces.Molecule;
import molecule.interfaces.TertiaryStringsAndColors;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterRaiser;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiarySelectionRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {CenterProteinTertiarySelectionRaiser.class})
public class CenterProteinTertiaryList extends CenterProteinTertiaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private String[] aminoAcids = null;
	
	public CenterProteinTertiaryList(Molecule molecule)
	{
		super();
		this.aminoAcids = molecule.getProteinArray();
	}
	
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

	private java.util.BitSet enablingBitSet = new java.util.BitSet();
	protected void setEnabled(int index, boolean b)
	{
		enablingBitSet.set(index, b);
	}
	protected boolean isEnabled(int index)
	{
		return enablingBitSet.get(index);
	}
	
	private void init()
	{
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(-1);
		setValueIsAdjusting(true);
		if(null != this.aminoAcids)
		{
			setListData(this.aminoAcids);
			this.setCellRenderer(new MyCellRenderer(this.getBackground()));
			
			setPrototypeCellValue("[WWW]999:ABC"); //$NON-NLS-1$
			
		}
		setValueIsAdjusting(false);
	}

	private void end()
	{
		removeAll();
		this.removeListSelectionListener(this);
	}
	
	private boolean inValueChanged = false;
	public void valueChanged(ListSelectionEvent lse)
	{
		if(!inValueChanged && !inReset)
		{
			inValueChanged = true;
			boolean isThisSource = (lse.getSource().hashCode() == this.hashCode());
			if (isThisSource)
			{
				if (!this.getValueIsAdjusting())
				{
					int[] indices = this.getSelectedIndices();
					Vector<String> enabledSelection = new Vector<String>();
					if((null != indices) && (indices.length > 0))
					{
						for (int i = 0; indices.length != i; i++)
						{
							int index = indices[i];
							if(isEnabled(index))
							{
								enabledSelection.add((String) this.getModel().getElementAt(index));
							}
						}
						if(!enabledSelection.isEmpty())
						{
							this.selection = enabledSelection.toArray(new String[enabledSelection.size()]);
							this.raise_CenterProteinTertiarySelectionEvent();
						}
						else
						{
							setSelection(this.selection);
						}
					}
					else
					{
						this.selection = null;
						this.raise_CenterProteinTertiarySelectionEvent();
					}
				}
			}
			inValueChanged = false;
		}
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inValueChanged && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			this.addListSelectionListener(this);
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		filterOptions = defaultfilter;
		if(null != aminoAcids)
		{
			setValueIsAdjusting(true);
			this.clearSelection();
			this.selection = null;
			enablingBitSet.clear();
			if(null != this.aminoAcids)
			{
				for(int i = 0; this.aminoAcids.length != i; i++)
				{
					setEnabled(i, true);
				}
			}
			setValueIsAdjusting(false);
		}	
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		filterOptions = defaultfilter;
		if(null != aminoAcids)
		{
			setValueIsAdjusting(true);
			this.clearSelection();
			this.selection = null;
			enablingBitSet.clear();
			if(null != this.aminoAcids)
			{
				for(int i = 0; this.aminoAcids.length != i; i++)
				{
					setEnabled(i, true);
				}
			}
			setValueIsAdjusting(false);
		}	
	}
	
	private void setSelection(String[] aminoAcids)
	{
		if((null != aminoAcids) && (0 != aminoAcids.length))
		{
			int[] selectedIndices = new int[aminoAcids.length];
			this.selection = new String[aminoAcids.length];
			for(int i=0; aminoAcids.length != i; i++)
			{
				selectedIndices[i] = i;
				selection[i] = aminoAcids[i];
			}
			this.setSelectedIndices(selectedIndices);
		}
		else
		{
			this.clearSelection();
		}
	}
	
	private final int defaultfilter = RenderingInfoRaiser.TERTIARY_NONE;
	private transient int filterOptions = defaultfilter;
	@Handles(raises = {})
	protected void handleCenterProteinTertiaryFilterRaiser(CenterProteinTertiaryFilterRaiser raiser)
	{
		this.filterOptions = raiser.getFilterOptions();
		this.enablingBitSet.clear();
		for(int i=0; this.aminoAcids.length != i; i++)
		{
			setEnabled(this.aminoAcids[i], i);
		}
	}
	
	protected String[] selection = null;
	public String[] getSelection()
    {
	    return selection;
    }

	public String[] getDefaultSelection()
	{
		return null;
	}

	public void setEnabled(String aminoAcid, int index)
	{
		TertiaryStringsAndColors tsac = utils.TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
		boolean isEnabled = false;
		if(0 != (RenderingInfoRaiser.TERTIARY_NONE & filterOptions))
		{
			isEnabled = true;
		}
		else if((null != aminoAcid) && !"".equals(aminoAcid)) //$NON-NLS-1$
		{
			int indexStart = aminoAcid.indexOf("["); //$NON-NLS-1$
			int indexEnd = aminoAcid.lastIndexOf("]"); //$NON-NLS-1$
			if((-1 != indexStart) && (-1 != indexEnd))
			{
				String residue = aminoAcid.substring(indexStart + 1, indexEnd);
				if(0 != (RenderingInfoRaiser.BURIED_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isBuried(residue);
				}
				else if(0 != (RenderingInfoRaiser.SURFACE_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isSurface(residue);
				}
				else if(0 != (RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isHydrophobic(residue);
				}
				else if(0 != (RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isHydrophilic(residue);
				}
				else if(0 != (RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isBasic(residue);
				}
				else if(0 != (RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isAcidic(residue);
				}
				else if(0 != (RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE & filterOptions))
				{
					isEnabled = tsac.isNeutral(residue);
				}
			}
		}
		setEnabled(index, isEnabled);
	}
	
	public boolean isEnabledSelection(int index)
	{
		ListModel myModel = this.getModel();
		if(null != myModel)
		{
			String listElement = (String) myModel.getElementAt(index);
			if((null != listElement) && (null != selection))
			{
				for(int i=0; selection.length != i; i++)
				{
					if(selection[i].equals(listElement))
					{
						if(isEnabled(index))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}	
	
	private class MyCellRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;
		public MyCellRenderer(Color parentBackground)
		{
			setOpaque(true);
		}
		public Component getListCellRendererComponent(
			final JList list,
			final Object value,
			final int index,
			final boolean isSelected,
			final boolean cellHasFocus)
		{
			CenterProteinTertiaryList myList = (CenterProteinTertiaryList) list;
			String valueStr = value.toString();
			setText(valueStr);
			boolean isSelectionEnabled = myList.isEnabledSelection(index);
			setBackground(isSelected && isSelectionEnabled ? list.getSelectionBackground() : list.getBackground());
			boolean isEnabled = myList.isEnabled(index); 
			setForeground(isEnabled ? list.getSelectionForeground() : list.getBackground().darker());
			return this;
			
		}
	}

}
