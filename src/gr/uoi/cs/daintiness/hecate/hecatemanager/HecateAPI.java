package gr.uoi.cs.daintiness.hecate.hecatemanager;

import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;
/**
* The HecateAPI implements the basic use-cases
* simply displays "Hello World!" to the standard output.
*
* @author  Nikos Koufos, Thanos Pappas, Mihalis Sotiriou
* @version 1.0
* @since   2017-05-20 
*/

public interface HecateAPI {

	/**
	   * This method implements the parsing process. It receives 
	   * as input the path of a schema file and extracts 
	   * the needed information.
	   * 
	   * @param path The path of the SQL data definition file.
	   * @return The schema of the database as a data structure.
	   */
	public Schema parse(String path);
	
	/**
	   * This method implements the export process. It can be 
	   * which extracts and stores the various tables and 
	   * transitions information on different files.
	   * 
	   * @param diffResult Data structure that stores all the information
	   * of a difference between 2 schemas
	   * @param transitions Data structure that stores a list of transitions.
	   * @param operation One of the following:
	   * <ul>
	   * <li>metrics: save metrics</li>
	   * <li>tables: save tables info</li>
	   * <li>transitions: save transitions information</li>
	   * </ul>
	   */
	public void export(DiffResult diffResult, Transitions transitions, String operation);
	
	/**
	   * This function performs the main algorithm for
	   * finding the differences between two schemas. 
	   * The algorithm is a modification of	the SortMergeJoin 
	   * algorithm found at DBMS's for joining two tables.
	   * 
	   * @param schema1 The original schema.
	   * @param schema2 The modified version of the original schema.
	   * @return The schema of the database as a data structure.
	   */
	public DiffResult getDifference(Schema schema1, Schema schema2);
	
	
}
