package molecule.ui.protein.quaternary;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.Messages;

import molecule.interfaces.Molecule;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {ProteinQuaternarySelectionRaiser.class})
public class ProteinQuaternaryList extends ProteinQuaternaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	transient private Molecule molecule = null;
	
	public ProteinQuaternaryList(Molecule molecule)
	{
		super();
		this.molecule = molecule;
	}
	
	public void addNotify()
	{
		super.addNotify();
		init();
	}
	
	private void init()
	{
		setPrototypeCellValue("chain W [WWW]999:ABC"); //$NON-NLS-1$

		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(-1);
		this.aminoAcids = getAminoAcids(this.molecule);
		setListData(this.aminoAcids);
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
				return Messages.getString("ProteinQuaternaryList.0") + residue.substring(colon + 1) + " " + residue; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return residue;
	}
	
	public void removeNotify()
	{
		this.removeListSelectionListener(this);	
		removeAll();
		super.removeNotify();
	}

	transient private boolean inValueChanged = false;
	public void valueChanged(ListSelectionEvent lse)
	{
		boolean isThisSource = lse.getSource().hashCode() == this.hashCode();
		if (isThisSource)
		{
			if(!lse.getValueIsAdjusting())
			{
				if(!inValueChanged && !inReset)
				{
					int anchorIndex = this.getSelectedIndex();
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
					this.addSelectionInterval(anchorIndex, anchorIndex);
					this.raise_ProteinQuaternarySelectionEvent();
					inValueChanged = false;
				}
			}
		}
	}
	
	private boolean isUnselectEvent(ListSelectionEvent lse)
	{
		boolean isUnselect = false;
		if(null != lse)
		{
			if(!isSelectAll(lse))
			{
				int min = this.getMinSelectionIndex();
				int max = this.getMaxSelectionIndex();
				if((-1 == min) || (-1 == max))
				{
					isUnselect = true;
				}
				else
				{
					int anchor = this.getAnchorSelectionIndex();
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
	
	private boolean isSelectAll(ListSelectionEvent lse)
	{
		return ((lse.getLastIndex() - lse.getFirstIndex() + 1) == this.aminoAcids.length);
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

	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inValueChanged && !inInitTree)
		{
			inInitTree = true;
			if(null != aminoAcids)
			{
				if(!this.getValueIsAdjusting())
				{
					this.setValueIsAdjusting(true);
					int[] indices = new int[this.aminoAcids.length];
					for(int i=0; indices.length != i; i++)
					{
						indices[i] = i;
					}
					this.setSelectedIndices(indices);
					initSelection();
					setValueIsAdjusting(false);
				}
			}
			this.addListSelectionListener(this);	
			inInitTree = false;
		}
	}

	transient private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset)
		{
			inReset = true;
			if(null != aminoAcids)
			{
				if(!this.getValueIsAdjusting())
				{
					this.setValueIsAdjusting(true);
					int[] indices = new int[this.aminoAcids.length];
					for(int i=0; indices.length != i; i++)
					{
						indices[i] = i;
					}
					this.setSelectedIndices(indices);
					initSelection();
					setValueIsAdjusting(false);
				}
			}
			inReset = false;
		}
	}

	private String[] defaultSelection = null;
	public String[] getDefaultSelection()
	{
		return this.defaultSelection;
	}
	
	transient private String[] aminoAcids = null;
	public String[] getAminoAcids()
	{
		return this.aminoAcids;
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
	private void initSelection()
	{
		if(null != this.aminoAcids)
		{
			ArrayList<String> chainArray = new ArrayList<String>();
			for(int i=0; this.aminoAcids.length !=i; i++)
			{
				String selection = this.aminoAcids[i];
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
			this.defaultSelection = this.selection;
		}
	}
}
