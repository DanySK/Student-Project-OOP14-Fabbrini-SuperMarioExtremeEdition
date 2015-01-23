package fabbroniko.error;

public class CorruptedAudioFileError extends Exception{
	
	private final String file;
	
	private static final long serialVersionUID = 1L;
	
	public CorruptedAudioFileError(final String file){
		super();
		this.file = file;
	}
	
	@Override
	public String toString(){
		return "Something went wrong trying to play " + file + ".";
	}
}
