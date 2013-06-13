package molecule.ui.protein.primary;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.protein.primary.signal.ProteinPrimarySelectionRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {ProteinPrimarySelectionRaiser.class})
public class ProteinPrimaryList extends ProteinPrimaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private String[] aminoAcids = null;
	
	public ProteinPrimaryList(String[] aminoAcids)
	{
		super();
		this.aminoAcids = aminoAcids;
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
		setPrototypeCellValue("[WWW]999:ABC"); //$NON-NLS-1$

		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(-1);
		setValueIsAdjusting(true);
		if(null != this.aminoAcids)
		{
			setListData(this.aminoAcids);
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
		if(!inValueChanged && !inReset && !inHandleProteinTertiarySelection)
		{
			inValueChanged = true;
			if (lse.getSource().hashCode() == this.hashCode())
			{
				if (!this.getValueIsAdjusting())
				{
					this.selection = null;
					Object[] values = this.getSelectedValues();
					if((null != values) && (values.length > 0))
					{
						this.selection = new String[values.length];
						for (int i = 0; values.length != i; i++)
						{
							this.selection[i] = (String) values[i];
						}
					}
					isFromProteinPrimarySelectionRaiser = true;
					this.raise_ProteinPrimarySelectionEvent();
				}
			}
			inValueChanged = false;
		}
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inValueChanged && !inInitTree && !inHandleProteinTertiarySelection)
		{
			inInitTree = true;
			if(null != aminoAcids)
			{
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[aminoAcids.length];
				this.selection = new String[aminoAcids.length];
				for(int i=0; aminoAcids.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = aminoAcids[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
			}
			this.addListSelectionListener(this);
			inInitTree = false;
		}
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset && !inHandleProteinTertiarySelection)
		{
			inReset = true;
			if(null != aminoAcids)
			{
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[aminoAcids.length];
				this.selection = new String[aminoAcids.length];
				for(int i=0; aminoAcids.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = aminoAcids[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
			}
			inReset = false;
		}
	}

	private boolean inHandleProteinTertiarySelection = false;
	@Handles(raises = {})
	protected synchronized void handleProteinTertiarySelectionRaiser(ProteinTertiarySelectionRaiser raiser)
	{
		if(!inValueChanged && !inReset && !inHandleProteinTertiarySelection)
		{
			inHandleProteinTertiarySelection = true;
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
				isFromProteinPrimarySelectionRaiser = false;
				this.raise_ProteinPrimarySelectionEvent();
			}
			inHandleProteinTertiarySelection = false;
		}
	}

	protected String[] selection = null;
	public synchronized String[] getSelection()
    {
	    return selection;
    }
	
	public String[] getDefaultSelection()
	{
		return aminoAcids;
	}

	transient private boolean isFromProteinPrimarySelectionRaiser = true;
	public boolean isFromProteinPrimarySelectionRaiser()
    {
	    return isFromProteinPrimarySelectionRaiser;
    }

}
