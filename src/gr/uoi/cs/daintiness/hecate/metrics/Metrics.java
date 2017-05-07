/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.metrics;


/**
 * 
 * @author iskoulis
 *
 */
public class Metrics {

	private String oldVersion;
	private String newVersion;
	private int insertions, deletions, alterations;
	private int tableIns, tableDel, tableAlt;
	private int attributeIns, attributeDel, attributeAlt;
	private int attributeTabIns, attributeTabDel;
	private int keyAlt;
	private int numOfTables, numOfAttributes;
	private int numOfNewTables, numOfNewAttributes;
	private static int revisions = 0;
	
	/**
	 * 
	 */
	public Metrics(String oldVersion, String newVersion) {
		this.oldVersion = oldVersion;
		this.newVersion = newVersion;
		reset();
	}
	
	public Metrics() {
		this.oldVersion = "not set";
		this.newVersion = "not set";
		reset();
	}
	
	public void setVersionNames(String oldVersion, String newVersion) {
		this.oldVersion = oldVersion;
		this.newVersion = newVersion;
	}

	private void reset() {
		insertions = deletions = alterations = 0;
		tableIns = tableDel = 0;
		attributeIns = attributeDel = 0;
		tableAlt = attributeAlt = keyAlt = 0;
		numOfTables = numOfAttributes = 0;
	}
	
	public void resetRevisions() {
		revisions = 0;
	}
	
	public void newRevision() {
		revisions++;
	}

	public void insertAttr () {
		attributeIns++; insertions++;
	}
	public void insertTabAttr () {
		attributeTabIns++; insertions++;
	}
	public void insetTable() {
		tableIns++; insertions++;
	}

	public void deleteAttr() {
		attributeDel++; deletions++;
	}
	public void deleteTabAttr() {
		attributeTabDel++; deletions++;
	}
	public void deleteTable() {
		tableDel++; deletions++;
	}

	public void alterAttr() {
		attributeAlt++;
		alterations++;
	}
	public void alterTable() {
		tableAlt++;
		alterations++;
	}
	public void alterKey() {
		keyAlt++;
		alterations++;
	}

	public void setOrigTables(int n) {
		numOfTables = n;
	}
	public void setOrigAttrs(int n) {
		numOfAttributes = n;
	}

	public void setNewTables(int n) {
		numOfNewTables = n;
	}
	public void setNewAttrs(int n) {
		numOfNewAttributes = n;
	}

	public int[] getTotalMetrics() {
		int i[] = {this.insertions, this.deletions, this.alterations};
		return i;
	}

	public int[] getTableMetrics() {
		int i[] = {this.tableIns, this.tableDel, this.tableAlt};
		return i;
	}

	public int[] getAttributeMetrics() {
		int i[] = {this.attributeIns, this.attributeDel, this.attributeAlt ,this.keyAlt, this.attributeTabIns, this.attributeTabDel};
		return i;
	}

	public int[] getOldSizes() {
		int i[] = {this.numOfTables, this.numOfAttributes};
		return i;
	}

	public int[] getNewSizes() {
		int i[] = {this.numOfNewTables, this.numOfNewAttributes};
		return i;
	}

	public String[] getVersionNames() {
		String[] ret = {oldVersion, newVersion};
		return ret;
	}

	public int getNumRevisions() {
		return revisions;
	}

	public void sanityCheck() {
		int insertionsCounter = tableIns + attributeIns + attributeTabIns;
		assert insertions == insertionsCounter: "Insertions misculculated";
		int deletionsCounter = tableDel + attributeDel + attributeTabDel;
		assert deletions == deletionsCounter: "Deletions misculculated";
		int tableGrowth = numOfNewTables - numOfTables;
		int tableChangesCounter = tableIns - tableDel;
		assert tableGrowth == tableChangesCounter: "Table changes misculculated";
		int attributeGrowth = numOfNewAttributes - numOfAttributes;
		int attributeChangesCounter = attributeIns + attributeTabIns - attributeDel - attributeTabDel; 
		assert attributeGrowth == attributeChangesCounter: "Attribute changes misculculated";
	}
}
