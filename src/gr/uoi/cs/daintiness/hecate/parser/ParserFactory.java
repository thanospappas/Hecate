package gr.uoi.cs.daintiness.hecate.parser;

public class ParserFactory {
	
	public SqlInputParser createHecateParser(){
		return new HecateParser();
	}
	
}
