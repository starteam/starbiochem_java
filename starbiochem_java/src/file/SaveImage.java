package file;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.TreeSet;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import mit.swing.StarOptionPane;

import molecule.ui.jmol.Viewer;

import org.jmol.api.JmolViewer;

import app.Messages;

public class SaveImage {

	private static final String PNG = "png"; //$NON-NLS-1$
	private static final String PNG_EXTENSION = "." + PNG; //$NON-NLS-1$
	
	public static synchronized void saveImage(Viewer viewer, JmolViewer jmolViewer) {
		if((null != viewer) && (null != jmolViewer))
		{
			try
			{
				jmolViewer.scriptWait("frank OFF;"); //$NON-NLS-1$
				String userHomeDirectory = System.getProperty("user.home"); //$NON-NLS-1$
				SaveImageJFileChooser fc = new SaveImageJFileChooser(userHomeDirectory);
				if(null != fc)
				{
					fc.setFileHidingEnabled(true);
					fc.setMultiSelectionEnabled(false);
					fc.setAcceptAllFileFilterUsed(false);
					addFileFilters(fc);
					int rval = fc.showSaveDialog(viewer);
					if (rval == SaveImageJFileChooser.APPROVE_OPTION)
					{
						FileFilter ff = fc.getFileFilter();
						if(null != ff)
						{
							int width = fc.getImageWidth();
							int height = fc.getImageHeight();
							int dpi = fc.getImageResolution();
							BufferedImage bi = fc.getBufferedImage();
							if(null != bi)
							{
								Graphics2D g2 = bi.createGraphics();
								if(null != g2)
								{
									Dimension dimSize = new Dimension(width, height);
									Rectangle rect = g2.getClipBounds();
									jmolViewer.renderScreenImage(g2, dimSize, rect);
									String errorMessage = jmolViewer.getErrorMessage();
									if(null == errorMessage)
									{
										File selectedFile = fc.getSelectedFile();
										if((null != selectedFile))
										{
											String parent = selectedFile.getParent();
											File folder = new File(parent);
											if(!folder.exists())
											{
												folder.mkdir();
											}
											String child = selectedFile.getName();
											if (null != child)
											{
												if(!child.toLowerCase().endsWith(PNG_EXTENSION))
												{
													child += PNG_EXTENSION;
												}
												File renamedFile = new File(parent, child);
												if(null != renamedFile)
												{
													selectedFile.renameTo(renamedFile);
													selectedFile = renamedFile;
													if(selectedFile.exists())
													{
														if(JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(viewer, Messages.getString("SaveImage.4") + selectedFile.getName() + "?")) //$NON-NLS-1$ //$NON-NLS-2$
														{
															// Need to cover all potential ifs here.
															return;
														}
													}
													saveImageToFile(bi, ff.getDescription(), selectedFile, dpi);
												}
											}
										}
									}
									else
									{
										//StarOptionPane pain = new Star
										int pane = StarOptionPane.starOptionDialog(viewer, errorMessage + "\n" + Messages.getString("SaveImage.7"), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, StarOptionPane.DEFAULT);
//										JOptionPane.showMessageDialog(viewer, errorMessage + "\n" + Messages.getString("SaveImage.7")); //$NON-NLS-1$ //$NON-NLS-2$
									}
								}
							}
						}
					}
				}
				jmolViewer.scriptWait("frank ON;"); //$NON-NLS-1$
			} catch (Exception ex) {
				ex.printStackTrace();
				jmolViewer.scriptWait("frank ON;"); //$NON-NLS-1$
			}
		}
	}

	private static void addFileFilters(JFileChooser fc) {
		String[] formats = getFormats();
		if (null != formats) {
			for (int i = 0; formats.length != i; i++) {
				if (formats[i].trim().toLowerCase().equals(PNG)) {
					fc.addChoosableFileFilter(new MyFileFilter(formats[i]));
				}
			}
		}
	}

	private static String[] getFormats() {
		String[] formats = ImageIO.getWriterFormatNames();
		TreeSet<String> formatSet = new TreeSet<String>();
		for (String s : formats) {
			formatSet.add(s.toLowerCase());
		}
		return formatSet.toArray(new String[0]);
	}

	private static class MyFileFilter extends FileFilter {
		private String format = null;

		public MyFileFilter(String format) {
			super();
			this.format = format;
		}

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String name = f.getName();
			return (name.toLowerCase().endsWith(format));
		}

		@Override
		public String getDescription() {
			return format;
		}
	}

	private static void setIIOMetadataDimensionValues(IIOMetadata metadata, int dpi)
			throws IIOInvalidTreeException {
		// for PNG, it's dots per millimeter
		double dotsPerMilli = 1.0 * dpi * 0.039;

		IIOMetadataNode horiz = new IIOMetadataNode(Messages.getString("SaveImage.10")); //$NON-NLS-1$
		horiz.setAttribute(Messages.getString("SaveImage.11"), Double.toString(dotsPerMilli)); //$NON-NLS-1$

		IIOMetadataNode vert = new IIOMetadataNode(Messages.getString("SaveImage.12")); //$NON-NLS-1$
		vert.setAttribute(Messages.getString("SaveImage.13"), Double.toString(dotsPerMilli)); //$NON-NLS-1$

		IIOMetadataNode dim = new IIOMetadataNode(Messages.getString("SaveImage.14")); //$NON-NLS-1$
		dim.appendChild(horiz);
		dim.appendChild(vert);

		IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0"); //$NON-NLS-1$
		root.appendChild(dim);

		metadata.mergeTree("javax_imageio_1.0", root); //$NON-NLS-1$
	}

	private static void saveImageToFile(BufferedImage image, String description, File output, int dpi) throws IOException {
		output.delete();

		final String formatName = "png"; //$NON-NLS-1$

		for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
			ImageWriter writer = iw.next();
			ImageWriteParam writeParam = writer.getDefaultWriteParam();
			ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
			IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
			if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
				continue;
			}

			setIIOMetadataDimensionValues(metadata, dpi);

			final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
			try {
				writer.setOutput(stream);
				writer.write(metadata, new IIOImage(image, null, metadata), writeParam);
			} 
			catch(Throwable ex) {
				ex.printStackTrace();
				stream.close();
			} finally {
				stream.close();
			}
			break;
		}
	}

}
