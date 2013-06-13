package xls;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class MessagesBuilder 
{
	
	public static void main(String[] args) 
	{
		new MessagesBuilder();
	}
	
	public MessagesBuilder() 
	{
		super();
		InputStream isXls = lanXls();
		InputStream defaultProp = defaultProp();
		load(isXls, defaultProp);
	}
	
	private InputStream lanXls()
	{
		FileInputStream is = null;
		try
		{
			is = new FileInputStream("src/xls/Workbook1.xls");

		}
		catch (Exception e) {
			System.out.println(e);
		}
		return is;
	}
	
	private InputStream defaultProp()
	{
		FileInputStream is = null;
		try
		{
			is = new FileInputStream("src/app/messages.properties");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return is;
	}
	
	private void load(InputStream xls, InputStream defaultProp) 
	{
		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(xls);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("Sheet1");
			Iterator<HSSFRow> rows = sheet.rowIterator();
			String[] lanArray = null;
			Properties[] props = null;
			
			if(rows.hasNext())
			{
				HSSFRow r = rows.next();
				lanArray = headerArray(r);
				props = createProps(lanArray);
				while(rows.hasNext())
				{
					r = rows.next();
					mapBuilder(r, props);
				}
			}
			writeMessageFiles(props, defaultProp, lanArray);
			
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	private void writeMessageFiles(Properties[] props, InputStream defaultProp, String[] lanArray)
	{
		if(props != null && props.length != 0)
		{
			if(defaultProp != null)
			{				
				Properties myProp = new Properties();
				try
				{
					myProp.load(defaultProp);
					
					boolean hasDefault = hasKeys(props[1], myProp);
					boolean hasXls = hasKeys(myProp, props[1]);
					if(!hasXls)
					{
						System.err.println("WARNING: ONE OR MORE KEYS NOT FOUND IN XLS FILE AND WILL NOT BE TRANSLATED. " +  whichKeys(myProp, props[1]));
					}
					if(hasDefault)
					{
						for(int i = 0; props.length != i; i++)
						{
							if(props[i] != null)
							{
								writePropFile(props[i], lanArray[i]);
							}
						}
					}
					else if(!hasDefault)
					{
						System.err.println("Default property file src/app/messages.properties is missing one or more keys " + whichKeys(props[1], myProp));
						throw new Exception();
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}			
		}
	}
	private boolean hasKeys(Properties prop1, Properties prop0)
	{
		boolean hasKeys = true;
		if(prop0 != null && prop1 != null)
		{
			Enumeration<Object> keys = prop1.keys();
			while(keys.hasMoreElements())
			{
				Object key = keys.nextElement();
				if(!prop0.containsKey(key))
				{
					hasKeys = false;
					System.err.println("Missing Key: " + key);
					return hasKeys;
				}
			}
		}
		return hasKeys;
	}
	
	private Object whichKeys(Properties prop1, Properties prop0)
	{
		Object key = null;
		if(prop0 != null && prop1 != null)
		{
			Enumeration<Object> keys = prop1.keys();
			while(keys.hasMoreElements())
			{
				key = keys.nextElement();
				if(!prop0.containsKey(key))
				{
					return key.toString();
				}
			}
		}
		return key.toString();
	}
	
	private void writePropFile(Properties props, String lan) 
	{	
		try
		{
			FileOutputStream myProp = new FileOutputStream("src/app/messages_" + lan + ".properties");
			props.store(myProp, lan + "property file ");
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	
	private static Properties[] createProps(String[] propsArray)
	{
		Properties[] props = new Properties[propsArray.length];
		for(int i = 1; i != props.length; i++)
		{
			props[i] = new Properties();
		}
		return props;
	}

	private void mapBuilder(HSSFRow r, Properties[] props)
	{
		if(r != null && props.length !=0)
		{
			Iterator<HSSFCell> cells = r.cellIterator();
			if(cells.hasNext())
			{	
				String key = String.valueOf(cells.next());		
				while(cells.hasNext())
				{
					HSSFCell value = cells.next();
					String val = String.valueOf(value);
					if(key != null && (value != null && !val.equals("")))
					{
						int colId = value.getColumnIndex();
						props[colId].setProperty(key, val);
					}
				}
			}
		}
	}
	
	private String[] headerArray(HSSFRow r)
	{
		String[] myStringArray = null;
		Iterator<HSSFCell> cells = r.cellIterator();
		ArrayList<String> lan = new ArrayList<String>();
		while(cells.hasNext())
		{
			HSSFCell cell = cells.next();
			String c = String.valueOf(cell);
			lan.add(c);
		}
		if(lan.size() != 0)
		{
			myStringArray = lan.toArray(new String[lan.size()]);
		}
		return myStringArray;
	}

}
