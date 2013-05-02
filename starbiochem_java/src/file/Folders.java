package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.filechooser.FileSystemView;

public class Folders {

	private static File downloadsFolder = null;
	public static final File getDownloadsFolder()
	{
		if(null == downloadsFolder)
		{
			try
			{
				FileSystemView fsv = FileSystemView.getFileSystemView();
				File home = fsv.getHomeDirectory();
				if(null != home)
				{
					File myDownloadsFolder = new File(home, "Downloads"); //$NON-NLS-1$
					if(!myDownloadsFolder.exists())
					{
						myDownloadsFolder.createNewFile();
						myDownloadsFolder.mkdir();
					}
					if(myDownloadsFolder.isDirectory())
					{
						downloadsFolder = myDownloadsFolder;
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return downloadsFolder;
	}
	
	public static BufferedWriter getPdbBufferedWriter(File pdbfile)
	{
		try
		{
			if(null != pdbfile)
			{
				return new BufferedWriter(new FileWriter(pdbfile));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public static File getEmptyPdbFile(File targetFolder, String id)
	{
		try
		{
			if(null != id)
			{
				String extension = ".pdb"; //$NON-NLS-1$
				String name = (id.endsWith(extension) ? id.substring(0, id.length() - extension.length()) : id);
				String filename = name + extension;
				File importFile = new File(targetFolder, filename);
				if(null != importFile)
				{
					int index = 0;
					while(true)
					{
						if(importFile.createNewFile())
						{
							return importFile;
						}
						filename = name + "(" + ++index + ")" + extension; //$NON-NLS-1$ //$NON-NLS-2$
						importFile = new File(targetFolder, filename);
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	private static File getExistingPdbFile(File targetFolder, String id)
	{
		try
		{
			if(null != id)
			{
				String extension = ".pdb"; //$NON-NLS-1$
				String name = (id.endsWith(extension) ? id.substring(0, id.length() - extension.length()) : id);
				String filename = name + extension;
				File aFile = new File(targetFolder, filename);
				File existingFile = null;
				int index = 0;
				while(true)
				{
					if(!aFile.exists())
					{
						return existingFile;
					}
					existingFile = aFile;
					filename = name + "(" + ++index + ")" + extension; //$NON-NLS-1$ //$NON-NLS-2$
					aFile = new File(targetFolder, filename);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public static BufferedReader getPdbBufferedReader(File targetFolder, String id)
	{
		try
		{
			if(null != id)
			{
				File existingFile = getExistingPdbFile(targetFolder,id);
				if(null != existingFile)
				{
					FileReader fr = new FileReader(existingFile);
					return new BufferedReader(fr);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

}
