package molecule.ui.adjust.center.protein.primary;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimarySelectionRaiser;

import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {CenterProteinPrimarySelectionRaiser.class})
public class CenterProteinPrimaryList extends CenterProteinPrimaryList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private String[] aminoAcids = null;
	
	public CenterProteinPrimaryList(String[] aminoAcids)
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
		if(!inValueChanged)
		{
			inValueChanged = true;
			if (lse.getSource().hashCode() == this.hashCode())
			{
				if (!this.getValueIsAdjusting())
				{
					this.selection = null;
					Object[] values = this.getSelectedValues();
					if(null != values)
					{
						this.selection = new String[values.length];
						for (int i = 0; values.length != i; i++)
						{
							this.selection[i] = (String) values[i];
						}
					}
					isFromProteinPrimarySelectionRaiser = true;
					this.raise_CenterProteinPrimarySelectionEvent();
				}
			}
			inValueChanged = false;
		}
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

	transient private String[] selection = null;
	public String[] getSelection()
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
