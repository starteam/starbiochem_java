package imports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import utils.ActionJList;
import app.Messages;

public class ImportsDialog extends JDialog implements ActionListener
{	
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String SPLIT_TOKENS = ",; "; //$NON-NLS-1$
	
	transient ActionJList idList = null;
	transient JLabel searchString;
	transient TextField searchText;
	transient JButton searchClose;
	transient JButton searchOpen;
	transient String keywords = null;
	transient JFrame frame = null;
	
	public ImportsDialog(JFrame frame, boolean modal)
	{
		super(frame, modal);
		this.frame = frame;
		setLocationRelativeTo(this.frame);
		setVisible(true);
	}

	public void addNotify()
	{
		super.addNotify();
		this.ids = null;
		initDialog(this.ids);
	}

	public void removeNotify()
	{
		if(null != searchText)
		{
			searchText.removeActionListener(this);
		}
		if(null != searchOpen)
		{
			searchOpen.removeActionListener(this);
		}
		if(null != searchClose)
		{
			searchClose.removeActionListener(this);
		}
		removeAll();
		super.removeNotify();
	}

	public Dimension getMinimalSize()
	{
		return getPreferredSize();
	}

	boolean inActionPerformed = false;
	public void  actionPerformed(ActionEvent e)
	{
		try
		{
			if(!inActionPerformed)
			{
				inActionPerformed = true;
				if((null != e) && (null != e.getSource()))
				{
					if((null != searchText) && e.getSource().equals(searchText))
					{
						handleSearch();
					}
					else if(e.getSource().equals(idList))
					{
						if(null != idList)
						{
							Object[] selectedValues = idList.getSelectedValues();
							if(null != selectedValues)
							{
								int size = selectedValues.length;
								ids = new ArrayList<String>();
								for(int i=0; size != i; i++)
								{
									ids.add((String) selectedValues[i]);
								}
							}
						}
						this.dispose();
					}
					else
					{
						String actionCommand = e.getActionCommand();
						if(actionCommand.equals(SEARCH_CLOSE_TEXT))
						{
							this.ids = null;
							this.dispose();
						}
						else if(actionCommand.equals(SEARCH_OPEN_TEXT))
						{
							if((null == ids) && (null != searchText) && !EMPTY_STRING.equals( searchText.getText().trim() ) && ((null == keywords) || !keywords.equalsIgnoreCase(searchText.getText().trim())))
							{
								handleSearch();
							}
							else
							{
								if(null != idList)
								{
									Object[] selectedValues = idList.getSelectedValues();
									if(null != selectedValues)
									{
										int size = selectedValues.length;
										ids = new ArrayList<String>();
										for(int i=0; size != i; i++)
										{
											ids.add((String) selectedValues[i]);
										}
									}
									this.dispose();
								}
							}
						}
					}
				}
				inActionPerformed = false;
			}
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(this, Messages.getString("ImportsDialog.2") + "\n" + ex.getLocalizedMessage()); //$NON-NLS-1$ //$NON-NLS-2$
			this.ids = null;
			this.dispose();
		}
	}
	
	private List<String> ids = null;
	public String[] getIds()
	{
		if(null != ids)
		{
			return ids.toArray(new String[0]);
		}
		return null;
		
	}
	
	private void handleSearch() throws IOException
	{
		if((null != searchText) && (null != searchText.getText()))
		{
			this.keywords = searchText.getText().trim();
			if((null != keywords) && !EMPTY_STRING.equals(keywords))
			{
				List<String> ids = ImportsSource.searchPdbs(keywords.split(SPLIT_TOKENS));
				initDialog(ids);
			}
		}
	}


    private void initDialog(List<String> ids)
	{
		getContentPane().removeAll();
		
		searchString = new JLabel(SEARCH_STRING_TEXT); 
		searchText = new TextField(20);
		searchClose = new JButton(SEARCH_CLOSE_TEXT);
		searchOpen = new JButton(SEARCH_OPEN_TEXT);
	
		JPanel searchTextWrapper = new JPanel(new BorderLayout()){ static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(4,4,4,4);}};
		searchTextWrapper.add(BorderLayout.NORTH, searchString);
		searchTextWrapper.add(BorderLayout.CENTER, searchText);

		JPanel importPanel = new JPanel(new BorderLayout());
		importPanel.add(BorderLayout.NORTH, searchTextWrapper);

		if((null != ids) && !ids.isEmpty())
		{
			this.idList = getIdList(ids.toArray(new String[0]));
			if(null != this.idList)
			{
				JPanel searchResults = new JPanel(new BorderLayout()) { static final long serialVersionUID = 1L; public Insets getInsets() { return new Insets(4,4,4,4); }};
				searchResults.add(BorderLayout.NORTH, new JLabel(SEARCH_RESULTS_TEXT));
	
				JScrollPane searchScrollPane = new JScrollPane(this.idList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				searchResults.add(BorderLayout.CENTER, searchScrollPane);
	
				importPanel.add(BorderLayout.CENTER, searchResults);
			}
		}

		JPanel searchClosePanel = new JPanel(new FlowLayout());
		searchClosePanel.add(searchOpen);
		searchClosePanel.add(searchClose);
		importPanel.add(BorderLayout.SOUTH, searchClosePanel);
	
		searchText.addActionListener(this);
		searchClose.addActionListener(this);
		searchOpen.addActionListener(this);
		getContentPane().add(importPanel);

		invalidate();
		validate();
		pack();	
		SwingUtilities.invokeLater( new Runnable()
        {
	        public void run()
	        {
	        	searchText.requestFocus();
		        
	        }
        });

	}

    private ActionJList getIdList(String[] ids)
	{
		if((null != ids) && (0 != ids.length))
		{
			ActionJList idList = new ActionJList(ids);
			idList.addActionListener(this);
			idList.setVisibleRowCount(Integer.parseInt(SEARCH_RESULTS_NUMBER_OF_ROWS));
			idList.setVisible(true);
			return idList;
		}
		return null;
	}

	public static String SEARCH_RESULTS_NUMBER_OF_ROWS = "8"; //$NON-NLS-1$
	public String SEARCH_RESULTS_STRING_TEXT = Messages.getString("ImportsDialog.7"); //$NON-NLS-1$
	public String SEARCH_STRING_TEXT = Messages.getString("ImportsDialog.8"); //$NON-NLS-1$
	public String SEARCH_OPEN_TEXT = Messages.getString("ImportsDialog.9"); //$NON-NLS-1$
	public String SEARCH_CLOSE_TEXT = Messages.getString("ImportsDialog.10"); //$NON-NLS-1$
	public String SEARCH_RESULTS_TEXT = Messages.getString("ImportsDialog.11"); //$NON-NLS-1$

}
