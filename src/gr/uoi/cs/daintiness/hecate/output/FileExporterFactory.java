package gr.uoi.cs.daintiness.hecate.output;

public class FileExporterFactory {
	
	public FileExporter createExportManger(String path){
		return new ExportManager(path);
	}
}
