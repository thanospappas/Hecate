package gr.uoi.cs.daintiness.hecate.output;

import java.util.ArrayList;

public class TableMetricsExporterFactory {

	public TableMetricsExporter createTableInsertionExporter(String path) {
		return new TableInsertionExporter(path);
	}

	public TableMetricsExporter createTableDeletionExporter(String path) {
		return new TableDeletionExporter(path);
	}

	public TableMetricsExporter createTableAllExporter(String path) {
		return new TableAllChangesExporter(path);
	}

	public TableMetricsExporter createTableKeyChangeExporter(String path) {
		return new TableKeyChangeExporter(path);
	}

	public TableMetricsExporter createTableStatsExporter(String path,int versions) {
		return new TableStatsExporter(path, versions);
	}

	public TableMetricsExporter createTableTypeChangeExporter(String path) {
		return new TableTypeChangeExporter(path);
	}
	
	public TableMetricsExporter createTableSizeExporter(String path) {
		return new TableSizeExporter(path);
	}

	public ArrayList<TableMetricsExporter> createExporters(String path, int versions) {
		ArrayList<TableMetricsExporter> tableMetricsExporters = new ArrayList<TableMetricsExporter>();
		tableMetricsExporters.add(createTableAllExporter(path));
		tableMetricsExporters.add(createTableDeletionExporter(path));
		tableMetricsExporters.add(createTableInsertionExporter(path));
		tableMetricsExporters.add(createTableKeyChangeExporter(path));
		tableMetricsExporters.add(createTableStatsExporter(path,versions));
		tableMetricsExporters.add(createTableTypeChangeExporter(path));
		tableMetricsExporters.add(createTableSizeExporter(path));
		return tableMetricsExporters;
	}

}
