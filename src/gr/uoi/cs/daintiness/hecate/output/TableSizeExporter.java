package gr.uoi.cs.daintiness.hecate.output;

import gr.uoi.cs.daintiness.hecate.metrics.tables.MetricsOverVersion;

public class TableSizeExporter extends TableMetricsExporter{

	public TableSizeExporter(String path) {
		super(path, "tables.csv");
	}

	@Override
	public void writeChanges(MetricsOverVersion metricsOverVersion, int i) {
		writeText(metricsOverVersion.getSize(i) + ";");
	}

	@Override
	public void writeLastColumn() {
		writeText("\n");	
	}

	@Override
	public void writeEmptyCells() {
		writeText("0;");		
	}

	@Override
	public void writeHeader(int versions) {
		writeVersionsLine(versions);	
	}

}
