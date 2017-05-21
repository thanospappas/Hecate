package gr.uoi.cs.daintiness.hecate.output;

import gr.uoi.cs.daintiness.hecate.metrics.tables.MetricsOverVersion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public abstract class TableMetricsExporter {
	
	protected String filePath;
	protected BufferedWriter fileWriter; 
	
	public abstract void writeChanges(MetricsOverVersion metricsOverVersion, int i);
	public abstract void writeLastColumn();
	public abstract void writeEmptyCells();
	public abstract void writeHeader(int versions);
	
	public static String getDir(String path) {
		String parent = (new File(path)).getParent();
		File directory = new File(parent + File.separator + "results");
		if (!directory.exists()) {
			directory.mkdir();
		}
		return directory.getPath();
	}
	
	public TableMetricsExporter(String path, String fileName){
		String slashedPath = getDir(path) + File.separator;
		filePath = slashedPath + fileName;
		initWriter();	
	}
	
	private void initWriter(){
		try {
			fileWriter = new BufferedWriter(new FileWriter(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void writeVersionsLine(int versions){
		writeText(";");
		for (int i = 0; i < versions; i++) {
			writeText(i + ";");
		}
		writeText("\n");
	}
	
	public void writeText(String table){
		try {
			fileWriter.write(table);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile(){
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
