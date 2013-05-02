package file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Jarme
{

	public static void write(JarOutputStream zipStream, File file) throws IOException
	{
		if((null != zipStream) && (null != file))
		{
			final int BUFFER = 64 * 1024;
			byte[] data = new byte[BUFFER];

			ZipEntry entry = new ZipEntry(file.getName());
			zipStream.putNextEntry(entry);

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);
			int count;
			while ((count = bis.read(data, 0, BUFFER)) != -1)
			{
				zipStream.write(data, 0, count);
			}
			bis.close();
			fis.close();
		}
	}
}
