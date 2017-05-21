# API REFERENCE

 The HecateAPI implements the 3 basic use-cases of the system.

### public Schema parse(String path);
	
	   This method implements the parsing process. It receives 
	   as input the path of a schema file and extracts 
	   the needed information.
	   
	   @param path The path of the SQL data definition file.
	   @return The schema of the database as a data structure.
	
### public void export(DiffResult diffResult, Transitions transitions, String operation);
	
	   This method implements the export process. It can be 
	   which extracts and stores the various tables and 
	   transitions information on different files.
	    
	   @param diffResult Data structure that stores all the information
	   of a difference between 2 schemas
	   @param transitions Data structure that stores a list of transitions.
	   @param operation One of the following:
	   
	   * **metrics:** save metrics
	   * **tables:** save tables info
	   * **transitions:** save transitions information
	    
	
### public DiffResult getDifference(Schema schema1, Schema schema2);
	
	   This function performs the main algorithm for
	   finding the differences between two schemas. 
	   The algorithm is a modification of	the SortMergeJoin 
	   algorithm found at DBMS's for joining two tables.
	   
	   @param schema1 The original schema.
	   @param schema2 The modified version of the original schema.
	   @return The schema of the database as a data structure.
	
	
	
}


## Authors

* **Nikos Koufos**
* **Thanos Pappas**
* **Mihalis Sotiriou**
