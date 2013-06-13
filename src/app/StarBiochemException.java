package app;
import app.Messages;


public class StarBiochemException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String JMOL_OUT_OF_MEMORY = Messages.getString("StarBiochemException.0"); //$NON-NLS-1$
	public static final String STARBIOCHEM_OUT_OF_MEMORY = Messages.getString("StarBiochemException.1"); //$NON-NLS-1$
	public static final String STARBIOCHEM_OUT_OF_MEMORY_FILE_LOAD = Messages.getString("StarBiochemException.2"); //$NON-NLS-1$
	public static final String STARBIOCHEM_OUT_OF_MEMORY_SAMPLE_LOAD = Messages.getString("StarBiochemException.3"); //$NON-NLS-1$
	public static final String STARBIOCHEM_OUT_OF_MEMORY_IMPORT_LOAD = Messages.getString("StarBiochemException.4"); //$NON-NLS-1$
	
	public StarBiochemException() {
	}

	public StarBiochemException(String message) {
		super(message);
	}

	public StarBiochemException(Throwable cause) {
		super(cause);
	}

	public StarBiochemException(String message, Throwable cause) {
		super(message, cause);
	}

}
