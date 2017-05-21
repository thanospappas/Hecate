package gr.uoi.cs.daintiness.hecate.hecatemanager;

public class HecateApiFactory {
	
	public HecateAPI createHecateManager(String path){
		return new HecateManager(path);
	}
	
}
