package gr.uoi.cs.daintiness.hecate.diff;
import gr.uoi.cs.daintiness.hecate.diff.SqlDifferenceExtractor;

public class DifferenceExtractorFactory {
	
	public DifferenceExtractor createSqlDifferenceExtractor(){
		return new SqlDifferenceExtractor();
	}
	
}
