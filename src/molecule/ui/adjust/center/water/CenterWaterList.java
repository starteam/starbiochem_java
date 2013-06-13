package molecule.ui.adjust.center.water;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.adjust.center.water.signal.CenterWaterSelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {CenterWaterSelectionRaiser.class})
public class CenterWaterList extends CenterWaterList_generated implements ListSelectionListener
{

	private static final long serialVersionUID = 1L;

	private String[] waters = null;
	
	public CenterWaterList(String[] waters)
	{
		super();
		this.waters = waters;
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
		if(null != this.waters)
		{
			setListData(this.waters);
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
			boolean isThisSource = (lse.getSource().hashCode() == this.hashCode());
			if (isThisSource)
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
					this.raise_CenterWaterSelectionEvent();
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

	protected String[] selection = null;
	public String[] getSelection()
    {
	    return selection;
    }
	public String[] getDefaultSelection()
	{
		return waters;
	}
	
}
