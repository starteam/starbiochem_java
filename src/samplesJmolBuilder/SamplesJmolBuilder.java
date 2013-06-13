package samplesJmolBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.swing.JFrame;

import file.Jarme;

public class SamplesJmolBuilder extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final String SAMPLES_PROPERTIES = "SamplesList.properties"; //$NON-NLS-1$

	public SamplesJmolBuilder(String[] args)
	{
		super();
		if((null != args) && (2 == args.length) )
		{
			try
			{
				String sourcePathname = args[0];
				String targetPathname = args[1];
				File source = new File(sourcePathname);
				if(source.isDirectory())
				{
					ArrayList<File> files = getSampleFiles(source);
					if(null != files)
					{
						jar(files, targetPathname);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	ArrayList<File> getSampleFiles(File source)
	{
		if(null != source)
		{
			String[] filenames = source.list();
			if((null != filenames) && (0 != filenames.length))
			{
				ArrayList<File> jmolFiles = new ArrayList<File>();
				for(int i = 0; filenames.length != i; i++)
				{
					File f = new File(source, filenames[i]);
					if(!f.isDirectory())
					{
						String name = f.getName();
						if((name.endsWith(".jmol")) || (name.equals(SAMPLES_PROPERTIES))) //$NON-NLS-1$
						{
							jmolFiles.add(f);
						}
					}
				}
				if((null != jmolFiles) && (!jmolFiles.isEmpty()))
				{
					return jmolFiles;
				}
			}
		}
		return null;
	}
	
	void jar(ArrayList<File> files, String targetPathname)
	{
		File targetDir = new File(targetPathname);
		if ((null != targetDir) && (targetDir.isDirectory()))
		{
			File samplesJar = new File(targetDir, "samples.jar"); //$NON-NLS-1$
			if((null != samplesJar) && samplesJar.isFile() && samplesJar.canWrite() && samplesJar.canRead())
			{
				try
	            {
	                FileOutputStream fos = new FileOutputStream(samplesJar) ;
	                if(null != fos)
	                {
	                	JarOutputStream jos = new JarOutputStream(fos, new Manifest());
	                	if(null != jos)
	                	{
	    					ListIterator<File> fileIterator = files.listIterator();
	    					while(fileIterator.hasNext())
	    					{
	    						File file = fileIterator.next();
	    						if(null != file)
	    						{
	    							Jarme.write(jos, file);
	    						}
	    					}
							jos.flush();
							jos.close();
							fos.close();
							
							File packedSamplesJar = new File(targetDir, "PackedSamples.jar"); //$NON-NLS-1$
							if((null != packedSamplesJar) && packedSamplesJar.isFile() && packedSamplesJar.canWrite())
							{
								FileOutputStream fpos = new FileOutputStream(packedSamplesJar) ;
								if(null != fpos)
								{
									JarOutputStream jpos = new JarOutputStream(fpos, new Manifest());
									Jarme.write(jpos, samplesJar);
									jpos.flush();
									jpos.close();
									fos.close();
								}
							}
	                	}
	                }
	            }
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		if((null != args) && (2 == args.length) )
		{
			System.err.println("SamplesJmolBuilder.main arg1: " + args[0] + " arg2: " + args[1]); //$NON-NLS-1$ //$NON-NLS-2$
			new SamplesJmolBuilder(args);
		}
		else
		{
			System.out.println("SamplesJmolBuilder Usage:" //$NON-NLS-1$
					+ "\nSamplesJmolBuilder <source directory for jmol files> <target directory for samples.jar and PackedSamples.jar files>"); //$NON-NLS-1$
		}
	}
}
