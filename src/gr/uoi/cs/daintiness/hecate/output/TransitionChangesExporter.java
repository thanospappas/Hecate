package gr.uoi.cs.daintiness.hecate.output;

import java.io.File;

public abstract class TransitionChangesExporter {
	
	private String path;
	
	public TransitionChangesExporter(String path){
		this.path = path;
	}
	
	public abstract void exportTransitions();
	
	public String getPath(){
		return this.path;
	}
	
	public String getDirectory(){
		String parent = (new File(getPath())).getParent();
		File directory = new File(parent + File.separator + "results");
		if (!directory.exists()) {
			directory.mkdir();
		}
		return directory.getPath();
	}
}
