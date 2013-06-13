package molecule.ui.adjust.center.hetero;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.adjust.center.hetero.signal.CenterHeteroSelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {CenterHeteroSelectionRaiser.class})
public class CenterHeteroList extends CenterHeteroList_generated implements ListSelectionListener
{

	private static final long serialVersionUID = 1L;

	private String[] heteros = null;
	
	public CenterHeteroList(String[] heteros)
	{
		super();
		this.heteros = heteros;
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
		if(null != heteros)
		{
			setListData(heteros);
		}
		setValueIsAdjusting(false);
	}

	private void end()
	{
		removeAll();
		this.removeListSelectionListener(this);
		this.selection = null;
	}

	private boolean inValueChanged = false;
	public void valueChanged(ListSelectionEvent lse)
	{
		if(!inValueChanged  && !inReset)
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
					this.raise_CenterHeteroSelectionEvent();
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
			this.selection = null;
			this.clearSelection();
			this.addListSelectionListener(this);
			inInitTree = false;
		}
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inValueChanged && !inReset)
		{
			inReset = true;
			this.selection = null;
			this.clearSelection();
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
		return heteros;
	}
	
}
