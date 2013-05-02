package molecule.ui.adjust.center.protein.quaternary;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.interfaces.Molecule;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternarySelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {CenterProteinQuaternarySelectionRaiser.class})
public class CenterProteinQuaternaryList extends CenterProteinQuaternaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	transient private Molecule molecule = null;
	
	public CenterProteinQuaternaryList(Molecule molecule)
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

	private void init()
	{
		setPrototypeCellValue("chain W [WWW]999:ABC"); //$NON-NLS-1$

		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(-1);
		setValueIsAdjusting(true);

		if(null != this.molecule)
		{
			this.aminoAcids = getAminoAcids(this.molecule);
			setListData(this.aminoAcids);
		}
		setValueIsAdjusting(false);
	}

	private void end()
	{
		removeAll();
		this.removeListSelectionListener(this);
	}
	
	private String[] getAminoAcids(Molecule molecule)
	{
		ArrayList<String> myAminoAcids = new ArrayList<String>();
		if(null != molecule)
		{
			String[] residues = molecule.getProteinArray();
			if(null != residues)
			{
				for(int i=0; residues.length != i; i++)
				{
					myAminoAcids.add(getQuaternaryStructureString(residues[i]));
				}
			}
		}
		return myAminoAcids.toArray(new String[0]);
	}
		
	private String getQuaternaryStructureString(String residue)
	{
		if(null != residue)
		{
			int colon = residue.indexOf(":"); //$NON-NLS-1$
			if((-1 != colon) && (residue.length() > colon))
			{
				return "chain " + residue.substring(colon + 1) + " " + residue; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return residue;
	}
	
	transient private boolean inValueChanged = false;
	public void valueChanged(ListSelectionEvent lse)
	{
		if(!lse.getValueIsAdjusting())
		{
			if(!inValueChanged)
			{
				inValueChanged = true;
				Object[] values = this.getSelectedValues();
				ArrayList<String> selectionListArray = new ArrayList<String>();
				if(isUnselectEvent(lse))
				{
					selectionListArray = getContractedSelectionArray(lse, values);
				}
				else
				{
					selectionListArray = getExpandedSelectionArray(values);
				}
				setSelectionArray(selectionListArray);
				setSelection(selectionListArray);
				this.raise_CenterProteinQuaternarySelectionEvent();
				inValueChanged = false;
			}
		}
	}



	private boolean isUnselectEvent(ListSelectionEvent lse)
	{
		boolean isUnselect = false;
		if(null != lse)
		{
			boolean isThisSource = lse.getSource().hashCode() == this.hashCode();
			if (isThisSource)
			{
				int anchor = this.getAnchorSelectionIndex();
				int min = this.getMinSelectionIndex();
				int max = this.getMaxSelectionIndex();
				if((-1 == min) || (-1 == max))
				{
					isUnselect = true;
				}
				else
				{
					boolean anchorSelected = this.isSelectedIndex(lse.getFirstIndex());
					String anchorChainID = getChainID(anchor);
					String minChainID = getChainID(min);
					String maxChainID = getChainID(max);
					
					if((null != anchorChainID) && (null != minChainID) && (null != maxChainID))
					{
						boolean idMinEqualsMax = (minChainID.equals(maxChainID));
						boolean idMinEqualsAnchor = (minChainID.equals(anchorChainID));
						boolean idMaxEqualsAnchor = (maxChainID.equals(anchorChainID));
						boolean idMinAnchorOrMaxAnchor = (idMinEqualsAnchor || idMaxEqualsAnchor);
				 		boolean valMinMaxNotAnchor = ((anchor != min) && (anchor != max));
						if(valMinMaxNotAnchor)
						{
							if(idMinEqualsMax)
							{
								isUnselect = true;
							}
							else
							{
								if(idMinAnchorOrMaxAnchor)
								{
									isUnselect = true;
								}
								else
								{
									if((!idMinAnchorOrMaxAnchor) && !anchorSelected)
									{
										isUnselect = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return isUnselect;
	}
	
	private void setSelectionArray(ArrayList<String> selectionArray)
	{
		boolean isValueAdjusting = this.getValueIsAdjusting();
		if(!isValueAdjusting)
		{
			this.setValueIsAdjusting(true);
			if(null != selectionArray)
			{
				int size = selectionArray.size();
				int[] indices = new int[size];
				for(int i=0; size != i; i++)
				{
					String selection = selectionArray.get(i);
					if((null != selection) && (null != this.aminoAcids))
					{
						for(int j=0; this.aminoAcids.length != j; j++)
						{
							if(selection.equals(this.aminoAcids[j]))
							{
								indices[i] = j;
								break;
							}
						}
					}
				}
				this.setSelectedIndices(indices);
			}
			this.setValueIsAdjusting(false);
		}
	}
	
	private String getChainID(int index)
	{
		String chainID = this.getModel().getElementAt(index).toString();
		if(null != chainID)
		{
			int indexOfColon = chainID.indexOf(":"); //$NON-NLS-1$
			if(-1 != indexOfColon)
			{
				chainID = chainID.substring(indexOfColon + 1);
			}
		}
		return chainID;
	}
	
	private ArrayList<String> getExpandedSelectionArray(Object[] values)
	{
		ArrayList<String> selectionArray = new ArrayList<String>();
		if((null != values) && (values.length > 0))
		{
			for(int i=0; values.length != i; i++)
			{
				String selectedValue = values[i].toString();
				int index = selectedValue.indexOf(":"); //$NON-NLS-1$
				if(-1 != index)
				{
					String chain = selectedValue.substring(index + 1);
					if((null != chain) && (null != this.aminoAcids) && (0 != this.aminoAcids.length))
					{
						for(int j=0; this.aminoAcids.length != j; j++)
						{
							String anAminoAcid = aminoAcids[j];
							if(anAminoAcid.endsWith(chain))
							{
								if(!selectionArray.contains(anAminoAcid))
								{
									selectionArray.add(anAminoAcid);
								}
							}
						}
					}
				}
			}
		}
		return selectionArray;
	}

	private ArrayList<String> getContractedSelectionArray(ListSelectionEvent lse, Object[] values)
	{
		ArrayList<String> contractedSelectionArray = new ArrayList<String>();
		if((null != lse) && (null != values) && (values.length > 0))
		{
			String firstElement = (String)this.getModel().getElementAt(lse.getFirstIndex());
			int index = firstElement.indexOf(":"); //$NON-NLS-1$
			if(-1 != index)
			{
				String chain = firstElement.substring(index + 1);
				if(null != chain)
				{
					for(int i=0; values.length != i; i++)
					{
						String selectedValue = values[i].toString();
						if(!selectedValue.endsWith(chain))
						{
							contractedSelectionArray.add(selectedValue);
						}
					}
				}
			}
		}
		return contractedSelectionArray;
	}

	
	public void initTree()
	{
		this.selection = null;
		this.clearSelection();
		this.addListSelectionListener(this);
	}

	public void reset()
	{
		this.selection = null;
		this.clearSelection();
	}

	public String[] getDefaultSelection()
	{
		return aminoAcids;
	}
	
	transient private String[] aminoAcids = null;
	public String[] getAminoAcids()
	{
		return this.aminoAcids;
	}
	
	transient private ArrayList<String> selectionArrayList = new ArrayList<String>();
	protected ArrayList<String> getSelectionArrayList()
    {
	    return selectionArrayList;
    }
	
	transient protected String[] selection = null;
	public String[] getSelection()
    {
	    return selection;
    }

	private void setSelection(ArrayList<String> selectionArray)
	{
		if(null != selectionArray)
		{
			ArrayList<String> chainArray = new ArrayList<String>();
			Iterator<String> selectionIterator = selectionArray.iterator();
			while(selectionIterator.hasNext())
			{
				String selection = selectionIterator.next();
				int index = selection.indexOf(":"); //$NON-NLS-1$
				if(-1 != index)
				{
					String chain = selection.substring(index + 1);
					if(!chainArray.contains(chain))
					{
						chainArray.add(chain);
					}
				}
			}
			this.selection = chainArray.toArray(new String[0]);
		}
	}

}
