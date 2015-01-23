package fabbroniko.error;

/**
 * Called when a file is corrupted (something went wrong while reading a file)
 * @author fabbroniko
 *
 */
public class CorruptedFileError extends RuntimeException{

	private final String fileName;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs the error
	 * @param fileName The corrupted file path
	 */
	public CorruptedFileError(final String fileName){
		super();
		this.fileName = fileName;
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString(){
		return "Something went wrong trying to read " + this.fileName;
	}
}
