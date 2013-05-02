package molecule.ui.nucleic;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import molecule.ui.nucleic.signal.NucleicSelectionRaiser;
import star.annotations.SignalComponent;

@SignalComponent(extend = JList.class, raises = {NucleicSelectionRaiser.class})
public class NucleicList extends NucleicList_generated implements ListSelectionListener
{

	private static final long serialVersionUID = 1L;

	private String[] nucleics = null;
	
	public NucleicList(String[] nucleics)
	{
		super();
		this.nucleics = nucleics;
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
		if(null != nucleics)
		{
			setListData(nucleics);
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
					this.raise_NucleicSelectionEvent();
				}
			}
			inValueChanged = false;
		}
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(null != nucleics)
		{
			if(!inValueChanged && !inInitTree)
			{
				inInitTree = true;
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[nucleics.length];
				this.selection = new String[nucleics.length];
				for(int i=0; nucleics.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = nucleics[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
				this.addListSelectionListener(this);
				inInitTree = false;
			}
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(null != nucleics)
		{
			if(!inValueChanged && !inReset)
			{
				inReset = true;
				setValueIsAdjusting(true);
				int[] selectedIndices = new int[nucleics.length];
				this.selection = new String[nucleics.length];
				for(int i=0; nucleics.length != i; i++)
				{
					selectedIndices[i] = i;
					selection[i] = nucleics[i];
				}
				this.setSelectedIndices(selectedIndices);
				setValueIsAdjusting(false);
				inReset = false;
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
		return nucleics;
	}
	
}
