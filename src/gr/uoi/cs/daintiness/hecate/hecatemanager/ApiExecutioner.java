package gr.uoi.cs.daintiness.hecate.hecatemanager;

import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.metrics.Metrics;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.sql.Table;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;

import java.io.File;
import java.util.Map.Entry;


public class ApiExecutioner {


	private Schema oldSchema;
	private Schema newSchema;

	private Transitions transitions;
	private HecateAPI hecateApi;

	
	private DiffResult res;
	private HecateApiFactory hecateApiFactory ;

	public ApiExecutioner(String exportPath){
		transitions = new Transitions();
		res = new DiffResult();
		hecateApiFactory = new HecateApiFactory();
		hecateApi = hecateApiFactory.createHecateManager(exportPath);
	}
	
	public Metrics getMetrics(){
		return this.res.getMetrics();
	}
	
	public void handleSchemaPairs(File oldFile,File newFile){
		hecateApi = hecateApiFactory.createHecateManager(null);
		oldSchema = hecateApi.parse(oldFile.getAbsolutePath());
		newSchema = hecateApi.parse(newFile.getAbsolutePath());	
		res = hecateApi.getDifference(oldSchema, newSchema);
		
	}
	
	private void addTables(int i, Schema schema){
		for (Entry<String, Table> e : schema.getTables().entrySet()) {
			String tableName = e.getKey();
			int attributes = e.getValue().getSize();
			res.getTableInfo().addTable(tableName, i, attributes);
		}
	}
	
	public void setOldSchema(String path,int i){
		oldSchema = hecateApi.parse(path);
		addTables(i, oldSchema);
	}
	
	public void setNewSchema(String path,int i, String[] list){
		newSchema = hecateApi.parse(path);
		if (i == list.length-2) {
			addTables(i+1, newSchema);
		}
	}
	
	public void getDifference(){
		res = hecateApi.getDifference(oldSchema, newSchema);
		transitions.add(res.getTransitionList());
			
	}
	
	public void exportMetrics(){
		hecateApi.export(res,transitions,"metrics");
	}
	
	public void exportFiles(String path,String[] list){
		try {
			hecateApi.export(res,transitions, "tables");
		} catch (Exception e) {
			e.printStackTrace();
		}
		hecateApi.export(res,transitions, "transitions");
		
		oldSchema = hecateApi.parse(path + File.separator + list[0]);
		newSchema = hecateApi.parse(path + File.separator + list[list.length-1]);
		res = hecateApi.getDifference(oldSchema, newSchema);
		
	}
	
	
	public Schema getOldSchema(){
		return this.oldSchema;
	}
	
	public Schema getNewSchema(){
		return this.newSchema;
	}
	
}
