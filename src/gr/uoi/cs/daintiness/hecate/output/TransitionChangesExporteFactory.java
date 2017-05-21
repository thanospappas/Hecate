package gr.uoi.cs.daintiness.hecate.output;

import java.util.ArrayList;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;

public class TransitionChangesExporteFactory {
	
	
	public XmlExporter createXmlExporter(Transitions transitions,String path){
		return new XmlExporter(transitions,path);
	}
	
	public CsvExporter createCsvExporter(Transitions transitions, String path){
		return new CsvExporter(transitions, path);
	}
	
	public ArrayList<TransitionChangesExporter> createExporters(Transitions transitions, String path){
		ArrayList<TransitionChangesExporter> transitionsExporter = new ArrayList<TransitionChangesExporter>();
		transitionsExporter.add(createXmlExporter(transitions, path));
		transitionsExporter.add(createCsvExporter(transitions, path));
		return transitionsExporter;
	}
	
}
