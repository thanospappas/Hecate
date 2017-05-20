package gr.uoi.cs.daintiness.hecate.tests;

import gr.uoi.cs.daintiness.hecate.parser.ParserFactory;
import gr.uoi.cs.daintiness.hecate.parser.SqlInputParser;

import java.io.File;

public class ParserTesting {

	public ParserTesting() {}

	public static void main(String[] args) {

		String path = "/home/iskoulis";
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Coppermine/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/DekiWiki/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/e107/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Joomla 1.5/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/NucleusCMS/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/phpwiki/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/SlashCode/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/TikiWiki/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Typo3/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/wikimedia/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/XOOPS/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Zabbix/mysql/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Zabbix/oracle/schemata" ;
//		path = path + "/Projects/Evolution Datasets/CMS\'s/Zabbix/postgresql/schemata" ;

//		path = path + "/Projects/Evolution Datasets/Med/biosql/schemata" ;
//		path = path + "/Projects/Evolution Datasets/Med/Ensembl/schemata" ;
		
		path = path + "/Projects/Evolution Datasets/CERN/Atlas/schemata" ;
		
		File dir = new File(path);
		
		String[] list = dir.list();
		java.util.Arrays.sort(list);
		ParserFactory parserFactory = new ParserFactory();
		SqlInputParser parser = parserFactory.createHecateParser();
		for (int i = 0; i < list.length; i++)  {
			System.out.println(list[i]);
			parser.parse(path + File.separator + list[i]);
		}
		
//		System.out.println(list[0]);
//		HecateParser.parse(path + File.separator + "test");
	}
}
