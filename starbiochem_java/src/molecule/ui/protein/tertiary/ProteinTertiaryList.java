package molecule.ui.protein.tertiary;

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
import molecule.ui.protein.primary.signal.ProteinPrimarySelectionRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryFilterRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySelectionRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {ProteinTertiarySelectionRaiser.class})
public class ProteinTertiaryList extends ProteinTertiaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private String[] aminoAcids = null;
	
	public ProteinTertiaryList(Molecule molecule)
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

		enablingBitSet.clear();
		
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void end()
	{
		removeAll();
		this.removeListSelectionListener(this);
	}
	
	private boolean inValueChanged = false;
	public void valueChanged(ListSelectionEvent lse)
	{
		if(!inValueChanged && !inReset && !inHandleProteinPrimarySelection)
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
							isFromProteinTertiarySelectionRaiser = true;
							this.raise_ProteinTertiarySelectionEvent();
						}
						else
						{
							setSelection(this.selection);
						}
					}
				}
			}
			inValueChanged = false;
		}
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inValueChanged && !inInitTree && !inHandleProteinPrimarySelection)
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
			setSelection(aminoAcids);
			setValueIsAdjusting(false);
			this.enablingBitSet.clear();
			for(int i=0; this.aminoAcids.length != i; i++)
			{
				setEnabled(this.aminoAcids[i], i);
			}
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset && !inHandleProteinPrimarySelection)
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
			setSelection(aminoAcids);
			setValueIsAdjusting(false);
			this.enablingBitSet.clear();
			for(int i=0; this.aminoAcids.length != i; i++)
			{
				setEnabled(this.aminoAcids[i], i);
			}
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
	protected void handleProteinTertiaryFilterRaiser(ProteinTertiaryFilterRaiser raiser)
	{
		this.filterOptions = raiser.getFilterOptions();
		this.enablingBitSet.clear();
		if(null != aminoAcids)
		{
			for(int i=0; this.aminoAcids.length != i; i++)
			{
				setEnabled(this.aminoAcids[i], i);
			}
		}
	}

	protected String[] selection = null;
	public String[] getSelection()
    {
	    return selection;
    }
	
	public String[] getDefaultSelection()
	{
		return aminoAcids;
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
	
	private boolean inHandleProteinPrimarySelection = false;
	@Handles(raises = {})
	protected void handleProteinPrimarySelectionRaiser(ProteinPrimarySelectionRaiser raiser)
	{
		if(!inValueChanged && !inReset && !inHandleProteinPrimarySelection)
		{
			inHandleProteinPrimarySelection = true;
			if(!raiser.equals(this))
			{
				this.setValueIsAdjusting(true);
				this.setSelectedIndices(raiser.getSelectedIndices());
				int[] selectedIndices = raiser.getSelectedIndices();
				this.selection = new String[selectedIndices.length];
				for(int i=0; selectedIndices.length != i; i++)
				{
					selection[i] = aminoAcids[selectedIndices[i]];
				}
				this.setValueIsAdjusting(false);
				invalidate();
				isFromProteinTertiarySelectionRaiser = false;
				this.raise_ProteinTertiarySelectionEvent();
			}
			inHandleProteinPrimarySelection = false;
		}
	}

	transient private boolean isFromProteinTertiarySelectionRaiser = true;
	public boolean isFromProteinTertiarySelectionRaiser()
    {
	    return isFromProteinTertiarySelectionRaiser;
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
			ProteinTertiaryList myList = (ProteinTertiaryList) list;
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
