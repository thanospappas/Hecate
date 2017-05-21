package gr.uoi.cs.daintiness.hecate.parser;

import gr.uoi.cs.daintiness.hecate.sql.Schema;

public interface SqlInputParser {
	
	public Schema parse(String filePath);
}
