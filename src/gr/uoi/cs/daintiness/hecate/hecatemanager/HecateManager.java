package gr.uoi.cs.daintiness.hecate.hecatemanager;

import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.diff.DifferenceExtractor;
import gr.uoi.cs.daintiness.hecate.diff.DifferenceExtractorFactory;
import gr.uoi.cs.daintiness.hecate.output.FileExporter;
import gr.uoi.cs.daintiness.hecate.output.FileExporterFactory;
import gr.uoi.cs.daintiness.hecate.parser.ParserFactory;
import gr.uoi.cs.daintiness.hecate.parser.SqlInputParser;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;

public class HecateManager implements HecateAPI{
	
	private FileExporter exportManager;
	private SqlInputParser parser;
	private DifferenceExtractor differenceExtractor;
			
	public HecateManager(String path) {
		
		FileExporterFactory fileExporterFactory = new FileExporterFactory();
		exportManager = fileExporterFactory.createExportManger(path);
		
		ParserFactory parserFactory = new ParserFactory();
		parser = parserFactory.createHecateParser();
		
		DifferenceExtractorFactory diffExtractor = new DifferenceExtractorFactory();
		differenceExtractor = diffExtractor.createSqlDifferenceExtractor();
	}
	
	
	@Override
	public Schema parse(String path) {
		return parser.parse(path);
	}

	@Override
	public void export(DiffResult diffResult, Transitions transitions,
			String operation) {
		
		if(operation.equals("metrics")){
			exportManager.exportMetrics(diffResult);
		}
		else if(operation.equals("tables")){
			exportManager.exportTableMetrics(diffResult.getMetrics().getNumRevisions()+1, 
					diffResult.getTableInfo());
		}
		else if(operation.equals("transitions")){
			exportManager.exportTransitionChanges(transitions, diffResult);
		}
		else{
			try {
				throw new Exception("Wrong Operation Type! Please choose one of "
						+ "the following:\n- metrics\n- tables\n- xml ...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public DiffResult getDifference(Schema schema1, Schema schema2) {
		return differenceExtractor.getDifference(schema1, schema2);
	}


}
