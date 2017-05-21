package gr.uoi.cs.daintiness.hecate.diff;

import gr.uoi.cs.daintiness.hecate.sql.Schema;

public interface DifferenceExtractor {
	
	public DiffResult getDifference(Schema schema1, Schema schema2);
	
}
