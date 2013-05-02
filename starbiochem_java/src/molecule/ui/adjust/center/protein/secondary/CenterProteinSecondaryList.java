package molecule.ui.adjust.center.protein.secondary;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.Messages;

import molecule.interfaces.Molecule;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryFilterRaiser;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondarySelectionRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JList.class, raises = {CenterProteinSecondarySelectionRaiser.class})
public class CenterProteinSecondaryList extends CenterProteinSecondaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	transient private Molecule molecule = null;

	public CenterProteinSecondaryList(Molecule molecule)
	{
		super();
		this.molecule = molecule;
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
		loadPreferences("adjust_secondary"); //$NON-NLS-1$
		setDefaultFilter();
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(-1);
		setValueIsAdjusting(true);
		if(null != this.molecule)
		{
			this.setListData(molecule.getStructureProteinArray());
			this.setCellRenderer(new MyCellRenderer(this.getBackground()));

			setPrototypeCellValue("WWWWWWW [W]99:WW"); //$NON-NLS-1$

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
	
	transient private boolean inValueChanged = false;
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
							this.raise_CenterProteinSecondarySelectionEvent();
						}
						else
						{
							setSelection(this.selection);
						}
					}
					else
					{
						this.selection = null;
						this.raise_CenterProteinSecondarySelectionEvent();
					}
				}
			}
			inValueChanged = false;
		}
	}

	transient private boolean inInitTree = false;
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
		filter = 0;
		this.enablingBitSet.clear();
		if(null != molecule)
		{
			setValueIsAdjusting(true);
			this.clearSelection();
			this.selection = null;
			setValueIsAdjusting(false);
		}
	}
	
	transient private boolean inReset = false;
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
		filter = 0;
		this.enablingBitSet.clear();
		if(null != molecule)
		{
			setValueIsAdjusting(true);
			this.clearSelection();
			this.selection = null;
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

	transient private int filter = 0;
	@Handles(raises = {})
	public void handleCenterProteinSecondaryFilterRaiser(CenterProteinSecondaryFilterRaiser raiser)
	{
		this.filter = raiser.getFilterOptions();
		this.enablingBitSet.clear();
		String[] aminoAcids = molecule.getStructureProteinArray();
		if(null != aminoAcids)
		{
			for(int i=0; aminoAcids.length != i; i++)
			{
				setEnabled(aminoAcids[i], i);
			}
		}
	}
	
	transient private String[] selection = null;
	public String[] getSelection()
    {
	    return selection;
    }

	public String[] getDefaultSelection()
	{
		return molecule.getStructureProteinArray();
	}

	public void setEnabled(String aminoAcid, int index)
	{
		boolean isEnabled = false;
		if(RenderingInfoRaiser.ALPHAHELIX_VISIBLE == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.2")); //$NON-NLS-1$
		}
		else if(RenderingInfoRaiser.BETASHEET_VISIBLE == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.3")); //$NON-NLS-1$
		}
		else if(RenderingInfoRaiser.COIL_VISIBLE == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.1")); //$NON-NLS-1$
		}
		else if((RenderingInfoRaiser.COIL_VISIBLE | RenderingInfoRaiser.BETASHEET_VISIBLE | RenderingInfoRaiser.ALPHAHELIX_VISIBLE) == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.1")) || aminoAcid.toString().startsWith(Messages.getString("Molecule.3")) || aminoAcid.toString().startsWith(Messages.getString("Molecule.2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		else if((RenderingInfoRaiser.COIL_VISIBLE | RenderingInfoRaiser.BETASHEET_VISIBLE) == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.1")) || aminoAcid.toString().startsWith(Messages.getString("Molecule.3")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if((RenderingInfoRaiser.COIL_VISIBLE | RenderingInfoRaiser.ALPHAHELIX_VISIBLE) == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.1")) || aminoAcid.toString().startsWith(Messages.getString("Molecule.2")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if((RenderingInfoRaiser.BETASHEET_VISIBLE | RenderingInfoRaiser.ALPHAHELIX_VISIBLE) == filter)
		{
			isEnabled = aminoAcid.toString().startsWith(Messages.getString("Molecule.3")) || aminoAcid.toString().startsWith(Messages.getString("Molecule.2")); //$NON-NLS-1$ //$NON-NLS-2$
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
			CenterProteinSecondaryList myList = (CenterProteinSecondaryList) list;
			setText(value.toString());
			boolean isSelectionEnabled = myList.isEnabledSelection(index);
			setBackground(isSelected && isSelectionEnabled ? list.getSelectionBackground() : list.getBackground());
			boolean isEnabled = myList.isEnabled(index); 
			setForeground(isEnabled ? list.getSelectionForeground() : list.getBackground().darker());
			return this;
			
		}
	}

	public void setDefaultFilter()
	{
		filter = 0;
		if(default_proteinsecondary_alphahelix_isselected)
		{
			filter += RenderingInfoRaiser.ALPHAHELIX_VISIBLE;			
		}
		if(default_proteinsecondary_betasheet_isselected)
		{
			filter += RenderingInfoRaiser.BETASHEET_VISIBLE;
		}
		if(default_proteinsecondary_coil_isselected)
		{
			filter += RenderingInfoRaiser.COIL_VISIBLE;
		}
	}
	
	protected boolean default_proteinsecondary_alphahelix_isselected = false;
	protected boolean default_proteinsecondary_betasheet_isselected = false;
	protected boolean default_proteinsecondary_coil_isselected = false;

	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinsecondary_alphahelix_isselected", Boolean.toString(default_proteinsecondary_alphahelix_isselected)); //$NON-NLS-1$
		default_proteinsecondary_alphahelix_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinsecondary_betasheet_isselected", Boolean.toString(default_proteinsecondary_betasheet_isselected)); //$NON-NLS-1$
		default_proteinsecondary_betasheet_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinsecondary_coil_isselected", Boolean.toString(default_proteinsecondary_coil_isselected)); //$NON-NLS-1$
		default_proteinsecondary_coil_isselected = Boolean.parseBoolean(s);
	}	
}
