package gr.uoi.cs.daintiness.hecate.diff;

import gr.uoi.cs.daintiness.hecate.metrics.tables.ChangeType;
import gr.uoi.cs.daintiness.hecate.sql.Attribute;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.sql.SqlItem;
import gr.uoi.cs.daintiness.hecate.sql.Table;
import gr.uoi.cs.daintiness.hecate.transitions.Deletion;
import gr.uoi.cs.daintiness.hecate.transitions.Insertion;
import gr.uoi.cs.daintiness.hecate.transitions.Update;

import java.util.Iterator;

/**
 * This class is responsible for performing the diff algorithm
 * between two SQL schemas. It then stores some metrics about the
 * performed diff.
 * @author giskou
 *
 */
public class SqlDifferenceExtractor implements DifferenceExtractor{

	private static Insertion insertion;
	private static Deletion deletion;
	private static Update update;
	private static DiffResult diffResult;
		
	/**
	 * This function performs the main diff algorithm for
	 * finding the differences between the schemas that are 
	 * given as parameters. The algorithm is a modification of
	 * the SortMergeJoin algorithm found at DBMS's for joining
	 * two tables. The tables and attributes are stored on TreeMaps
	 * thus sorted according their name. Starting from the top of
	 * each Map we check the items for matches. If the original is
	 * larger lexicographically then the item of the modified Map does
	 * not exist in the original and so it's inserted and we move to
	 * the next item on the modified Map. Likewise, if the modified
	 * is larger lexicographically then the item on the original has been
	 * deleted and we move to the next item on the original Map. If a
	 * Map reaches at an end then the remaining items on the other Map
	 * are marked as inserted or deleted accordingly.
	 * @param schema1
	 *   The original schema
	 * @param schema2
	 *   The modified version of the original schema
	 */
	public DiffResult getDifference(Schema schema1, Schema schema2) {
		diffResult = new DiffResult();
		diffResult.metrics.newRevision();
		diffResult.setVersionNames(schema1.getName(), schema2.getName());
		String oldTableKey = null, newTableKey = null ;
		String oldAttrKey = null, newAttrKey = null ;
		Iterator<String> oldTableKeys = schema1.getTables().keySet().iterator() ;
		Iterator<Table> oldTableValues = schema1.getTables().values().iterator() ;
		Iterator<String> newTableKeys = schema2.getTables().keySet().iterator() ;
		Iterator<Table> newTableValues = schema2.getTables().values().iterator() ;
		setOriginalSizes(schema1.getSize(), schema2.getSize());
		
		if (oldTableKeys.hasNext() && newTableKeys.hasNext()){
			oldTableKey = oldTableKeys.next() ;
			Table oldTable = (Table) oldTableValues.next() ;
			newTableKey = newTableKeys.next() ;
			Table newTable = (Table) newTableValues.next() ;
			while(true) {
				insertion = null; deletion = null; update = null;
				if (oldTableKey.compareTo(newTableKey) == 0) {            // ** Matched tables
					match(oldTable, newTable);
					// check attributes
					Iterator<String> oldAttributeKeys = oldTable.getAttrs().keySet().iterator();
					Iterator<Attribute> oldAttributeValues = oldTable.getAttrs().values().iterator() ;
					Iterator<String> newAttributeKeys = newTable.getAttrs().keySet().iterator();
					Iterator<Attribute> newAttributeValues = newTable.getAttrs().values().iterator() ;
					
					if (oldAttributeKeys.hasNext() && newAttributeKeys.hasNext()){
						oldAttrKey = oldAttributeKeys.next() ;
						Attribute oldAttr = oldAttributeValues.next();
						newAttrKey = newAttributeKeys.next() ;
						Attribute newAttr = newAttributeValues.next();
						while (true) {
							// possible attribute match
							if (oldAttrKey.compareTo(newAttrKey) == 0) {
								// check attribute type
								if (oldAttr.getType().compareTo(newAttr.getType()) == 0){
									// ** Matched attributes
									if (oldAttr.isKey() == newAttr.isKey()) {
										match(oldAttr, newAttr);
									} else {// * attribute key changed
										handleAttrKeyChange(oldAttr, newAttr);
									}
								} else {// attribute type changed
									handleAttrTypeChange(oldAttr, newAttr);
								}
								// move both attributes
								if (oldAttributeKeys.hasNext() && newAttributeKeys.hasNext()) {
									oldAttrKey = oldAttributeKeys.next() ;
									oldAttr = oldAttributeValues.next();
									newAttrKey = newAttributeKeys.next() ;
									newAttr = newAttributeValues.next();
									continue;
								} else {// one of the lists is empty, must process the rest of the other
									break ;
								}
							} else if (oldAttrKey.compareTo(newAttrKey) < 0) {           // ** Deleted attributes
								handleAttrDel(oldAttr, newTable);
								// move old only attributes
								if (oldAttributeKeys.hasNext()) {
									oldAttrKey = oldAttributeKeys.next();
									oldAttr = oldAttributeValues.next();
									continue;
								} else {                  // no more old
									handleAttrIns(oldTable, newAttr);
									break ;
								}
							} else {                    // ** Inserted attributes
								handleAttrIns(oldTable, newAttr);
								// move new only
								if (newAttributeKeys.hasNext()) {
									newAttrKey = newAttributeKeys.next() ;
									newAttr = newAttributeValues.next();
									continue;
								} else {                  // no more new
									handleAttrDel(oldAttr, newTable);
									break ;
								}
							}
						}
					}
					// check remaining attributes
					while (oldAttributeKeys.hasNext()) {       // delete remaining old (not found in new)
						oldAttrKey = (String) oldAttributeKeys.next();
						Attribute oldAttr = oldAttributeValues.next();
						handleAttrDel(oldAttr, newTable);
					}
					while (newAttributeKeys.hasNext()) {        // insert remaining new (not found in old)
						newAttrKey = (String) newAttributeKeys.next();
						Attribute newAttr = newAttributeValues.next();
						handleAttrIns(oldTable, newAttr);
					}
					//  ** Done with attributes **
					if (newTable.getMode() == SqlItem.UPDATED) {
						tableAlt(newTable);
					}
					if (oldTableKeys.hasNext() && newTableKeys.hasNext()) {   // move both tables
						oldTableKey = oldTableKeys.next() ;
						oldTable = (Table) oldTableValues.next() ;
						newTableKey = newTableKeys.next() ;
						newTable = (Table) newTableValues.next() ;
						continue;
					} else {            // one list is empty
						break ;
					}
				} else if (oldTableKey.compareTo(newTableKey) < 0) {  // ** Table Deleted
					handleTableDel(oldTable);
					if (oldTableKeys.hasNext()) {                     // move old only
						oldTableKey = oldTableKeys.next() ;
						oldTable = (Table) oldTableValues.next() ;
						continue;
					} else {
						handleTableIns(newTable);
						break;
					}
				} else {                                             // ** Table Inserted
					handleTableIns(newTable);
					if (newTableKeys.hasNext()) {                    // move new only
						newTableKey = newTableKeys.next() ;
						newTable = (Table) newTableValues.next() ;
						continue;
					} else {
						handleTableDel(oldTable);
						break ;
					}
				}
			}
		}
		// check remaining table keys
		while (oldTableKeys.hasNext()) {
			oldTableKey = (String) oldTableKeys.next();
			Table oldTable = (Table) oldTableValues.next();
			handleTableDel(oldTable);
		}
		while (newTableKeys.hasNext()) {
			newTableKey = (String) newTableKeys.next();
			Table newTable = (Table) newTableValues.next();
			handleTableIns(newTable);
		}
		diffResult.metrics.sanityCheck();
		return diffResult;
	}

	private static void handleAttrIns(Table oldTable, Attribute newAttr) {
		diffResult.metrics.insertAttr();
		insert(newAttr);
		newAttr.setMode(SqlItem.INSERTED);
		oldTable.setMode(SqlItem.UPDATED);
		newAttr.getTable().setMode(SqlItem.UPDATED);
		diffResult.tablesInfo.addChange(oldTable.getName(), diffResult.metrics.getNumRevisions(), ChangeType.Insertion);
	}

	private static void handleAttrDel(Attribute oldAttr, Table newTable) {
		diffResult.metrics.deleteAttr();
		delete(oldAttr);
		oldAttr.setMode(SqlItem.DELETED);
		oldAttr.getTable().setMode(SqlItem.UPDATED);
		newTable.setMode(SqlItem.UPDATED);
		diffResult.tablesInfo.addChange(newTable.getName(), diffResult.metrics.getNumRevisions(), ChangeType.Deletion);
	}

	private static void handleAttrTypeChange(Attribute oldAttr, Attribute newAttr) {
		diffResult.metrics.alterAttr();
		update(newAttr, "TypeChange");
		oldAttr.getTable().setMode(SqlItem.UPDATED);
		newAttr.getTable().setMode(SqlItem.UPDATED);
		oldAttr.setMode(SqlItem.UPDATED);
		newAttr.setMode(SqlItem.UPDATED);
		diffResult.tablesInfo.addChange(newAttr.getTable().getName(), diffResult.metrics.getNumRevisions(), ChangeType.AttrTypeChange);
	}

	private static void handleAttrKeyChange(Attribute oldAttr, Attribute newAttr) {
		diffResult.metrics.alterKey();
		update(newAttr, "KeyChange");
		oldAttr.getTable().setMode(SqlItem.UPDATED);
		newAttr.getTable().setMode(SqlItem.UPDATED);
		oldAttr.setMode(SqlItem.UPDATED);
		newAttr.setMode(SqlItem.UPDATED);
		diffResult.tablesInfo.addChange(newAttr.getTable().getName(), diffResult.metrics.getNumRevisions(), ChangeType.KeyChange);
	}
	
	private static void handleTableDel(Table t) {
		delete(t);
		diffResult.metrics.deleteTable();
		markAll(t, SqlItem.DELETED);     // mark attributes deleted
	}
	
	private static void handleTableIns(Table t) {
		insert(t);
		diffResult.metrics.insetTable();
		markAll(t, SqlItem.INSERTED);     // mark attributes inserted
	}
	
	private static void tableAlt(Table t) {
		diffResult.metrics.alterTable();
	}

	private static void match(SqlItem oldI, SqlItem newI) {
		oldI.setMode(SqlItem.MACHED);
		newI.setMode(SqlItem.MACHED);
	}

	private static void markAll(Table t, int mode) {
		t.setMode(mode);
		for (Iterator<Attribute> i = t.getAttrs().values().iterator(); i.hasNext(); ) {
			i.next().setMode(mode);
			switch(mode){
				case SqlItem.INSERTED: diffResult.metrics.insertTabAttr(); break;
				case SqlItem.DELETED: diffResult.metrics.deleteTabAttr(); break;
				default:;
			}
		}
	}
	
	private static void insert(SqlItem item) {
		if (item.getClass() == Attribute.class) {
			if (insertion == null) {
				insertion = new Insertion();
				diffResult.transitionList.add(insertion);
			}
			try {
				insertion.setAttribute( (Attribute) item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (item.getClass() == Table.class) {
			insertion = new Insertion();
			diffResult.transitionList.add(insertion);
			insertion.setTable( (Table) item);
		}
	}
	
	private static void delete(SqlItem item) {
		if (item.getClass() == Attribute.class) {
			if (deletion == null) {
				deletion = new Deletion();
				diffResult.transitionList.add(deletion);
			}
			try {
				deletion.setAttribute( (Attribute) item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (item.getClass() == Table.class) {
			deletion = new Deletion();
			diffResult.transitionList.add(deletion);
			deletion.setTable( (Table) item);
		}
	}
	
	private static void update(Attribute item, String type) {
		if (update == null) {
			update = new Update();
			diffResult.transitionList.add(update);
		}
		try {
			update.updateAttribute((Attribute)item, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setOriginalSizes(int[] sizeA, int[] sizeB) {
		diffResult.metrics.setOrigTables(sizeA[0]); diffResult.metrics.setOrigAttrs(sizeA[1]);
		diffResult.metrics.setNewTables(sizeB[0]); diffResult.metrics.setNewAttrs(sizeB[1]);
	}
}
