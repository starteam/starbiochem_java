package molecule.ui.water;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.water.signal.WaterSelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {WaterSelectionRaiser.class})
public class WaterList extends WaterList_generated implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private String[] waters = null;
	
	public WaterList(String[] waters)
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
		if(!inValueChanged && !inReset)
		{
			inValueChanged = true;
			boolean isThisSource = (lse.getSource().hashCode() == this.hashCode());
			if (isThisSource)
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
					this.raise_WaterSelectionEvent();
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
			if(null != waters)
			{
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[waters.length];
				this.selection = new String[waters.length];
				for(int i=0; waters.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = waters[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
				this.addListSelectionListener(this);
			}
			inInitTree = false;
		}
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset)
		{
			inReset = true;
			if(null != waters)
			{
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[waters.length];
				this.selection = new String[waters.length];
				for(int i=0; waters.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = waters[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
			}
			inReset = false;
		}
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
