package plugin;

public class Loader
{
	private static LoaderInterface defaultLoader = null;

	public static LoaderInterface getDefaultLoader()
	{
		if (null == defaultLoader)
		{
			defaultLoader = new DefaultLoader();
		}
		return defaultLoader;
	}

}
