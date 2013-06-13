package file;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import app.Messages;

public class SaveImageJFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	
	MyAccessoryPanel myAccessoryPanel = null;
	public SaveImageJFileChooser(String userHomeDirectory) {
		super(userHomeDirectory);
		this.myAccessoryPanel = new MyAccessoryPanel();
		this.setAccessory(myAccessoryPanel);
	}

	static class MyAccessoryPanel extends JPanel implements ItemListener
	{
		private static final long serialVersionUID = 1L;
		
		private static final String DPI_72 = "72"; //$NON-NLS-1$
		private static final String DPI_150 = "150"; //$NON-NLS-1$
		private static final String DPI_300 = "300"; //$NON-NLS-1$

		private Vector<String> possibleResolutions = null;
		private JComboBox resolutionSelections = null;
		protected int resolution = Integer.parseInt(DPI_72);

		private static final String WIDTH_3 = "3"; //$NON-NLS-1$
		private static final String WIDTH_5 = "5"; //$NON-NLS-1$
		private static final String WIDTH_8_5 = "8.5"; //$NON-NLS-1$
		private static final String WIDTH_11 = "11"; //$NON-NLS-1$

		private Vector<String> possibleWidths = null;
		private JComboBox widthSelections = null;
		protected int width = (int) (Float.parseFloat(WIDTH_3) * resolution);

		private static final String HEIGHT_3 = "3"; //$NON-NLS-1$
		private static final String HEIGHT_5 = "5"; //$NON-NLS-1$
		private static final String HEIGHT_8_5 = "8.5"; //$NON-NLS-1$
		private static final String HEIGHT_11 = "11"; //$NON-NLS-1$

		private Vector<String> possibleHeights = null;
		private JComboBox heightSelections = null;
		protected int height = (int) (Float.parseFloat(HEIGHT_3) * resolution);
		
		protected BufferedImage bufferedImage = null;
		private boolean validBuffer = false;
		
		public MyAccessoryPanel()
		{
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			setLayout(gridbag);

			constraints.fill = GridBagConstraints.BOTH;

			constraints.weighty = 1.0;
			constraints.gridheight = 1;
			constraints.weightx = 1;

			constraints.ipadx = 2;
			constraints.gridx = 0;
			constraints.gridy = 0;
			JLabel resolutionsLabel = new JLabel(Messages.getString("SaveImageJFileChooser.11")); //$NON-NLS-1$
			add(resolutionsLabel, constraints);

				constraints.gridx = 1;
				constraints.gridy = 0;
				
				possibleResolutions = new Vector<String>();
				possibleResolutions.add(DPI_72);
				possibleResolutions.add(DPI_150);
				possibleResolutions.add(DPI_300);
				resolutionSelections = new JComboBox(possibleResolutions);
				resolutionSelections.addItemListener(this);
				add(resolutionSelections, constraints);
				
			constraints.gridx = 0;
			constraints.gridy = 1;
			JLabel widthLabel = new JLabel(Messages.getString("SaveImageJFileChooser.12")); //$NON-NLS-1$
			add(widthLabel, constraints);

				constraints.gridx = 1;
				constraints.gridy = 1;
				
				possibleWidths = new Vector<String>();
				possibleWidths.add(WIDTH_3);
				possibleWidths.add(WIDTH_5);
				possibleWidths.add(WIDTH_8_5);
				possibleWidths.add(WIDTH_11);
				widthSelections = new JComboBox(possibleWidths);
				widthSelections.addItemListener(this);
				add(widthSelections, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			JLabel heightLabel = new JLabel(Messages.getString("SaveImageJFileChooser.13")); //$NON-NLS-1$
			add(heightLabel, constraints);

				constraints.gridx = 1;
				constraints.gridy = 2;
				
				possibleHeights = new Vector<String>();
				possibleHeights.add(HEIGHT_3);
				possibleHeights.add(HEIGHT_5);
				possibleHeights.add(HEIGHT_8_5);
				possibleHeights.add(HEIGHT_11);
				heightSelections = new JComboBox(possibleHeights);
				heightSelections.addItemListener(this);
				add(heightSelections, constraints);
			
		}

		public void itemStateChanged(ItemEvent ie)
		{
			if((null != ie) && (ie.getStateChange() == ItemEvent.SELECTED))
			{
				Object source = ie.getSource();
				if(null != source)
				{
					if(source.equals(resolutionSelections))
					{
						this.resolution = Integer.parseInt((String) resolutionSelections.getSelectedItem());
						int testWidth = (int) (Float.parseFloat((String) widthSelections.getSelectedItem()) * this.resolution);
						int testHeight = (int) (Float.parseFloat((String) heightSelections.getSelectedItem()) * this.resolution);
						this.bufferedImage = new BufferedImage(testWidth, testHeight, BufferedImage.TYPE_INT_RGB);
						this.width = testWidth;
						this.height = testHeight;
						validBuffer = true;
					}
					else if(source.equals(widthSelections))
					{
						this.width = (int) (Float.parseFloat((String) widthSelections.getSelectedItem()) * resolution);
						this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
						validBuffer = true;
					}
					else if(source.equals(heightSelections))
					{
						this.height = (int) (Float.parseFloat((String) heightSelections.getSelectedItem()) * resolution);
						this.bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
						validBuffer = true;
					}
				}
			}
		}
	}
	
	public int getImageResolution()
	{
		return this.myAccessoryPanel.resolution;
	}
	
	public int getImageWidth()
	{
		return this.myAccessoryPanel.width;
	}

	public int getImageHeight()
	{
		return this.myAccessoryPanel.height;
	}

	public BufferedImage getBufferedImage()
	{
		if(!this.myAccessoryPanel.validBuffer)
		{
			return new BufferedImage(this.myAccessoryPanel.width, this.myAccessoryPanel.height, BufferedImage.TYPE_INT_RGB);
		}
		return this.myAccessoryPanel.bufferedImage;
	}
	public int showSaveDialog(Component parent) throws HeadlessException {
		setDialogType(SAVE_DIALOG);
		return showDialog(parent, Messages.getString("StarOptionPane.5"));
	    }
}
