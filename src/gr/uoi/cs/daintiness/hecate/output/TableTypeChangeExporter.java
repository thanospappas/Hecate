package gr.uoi.cs.daintiness.hecate.output;

import gr.uoi.cs.daintiness.hecate.metrics.tables.Changes;
import gr.uoi.cs.daintiness.hecate.metrics.tables.MetricsOverVersion;

public class TableTypeChangeExporter extends TableMetricsExporter{

	public TableTypeChangeExporter(String path) {
		super(path, "table_type.csv");
	}

	@Override
	public void writeChanges(MetricsOverVersion metricsOverVersion, int i) {
		Changes c = metricsOverVersion.getChanges(i);
		writeText(c.getAttrTypeChange() + ";");	
	}

	@Override
	public void writeLastColumn() {
		writeText("\n");	
	}

	@Override
	public void writeEmptyCells() {
		writeText("-;");	
	}

	@Override
	public void writeHeader(int versions) {
		writeVersionsLine(versions);	
	}

}
