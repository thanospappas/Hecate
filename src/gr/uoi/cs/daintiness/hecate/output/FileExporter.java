package gr.uoi.cs.daintiness.hecate.output;

import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.metrics.tables.TablesInfo;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;

public interface FileExporter {
	
	public void exportTableMetrics(int versions, TablesInfo ti);
	public void exportMetrics(DiffResult res);
	public void exportTransitionChanges(Transitions transitions,DiffResult result);
}
