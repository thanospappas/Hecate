/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.diff;

/**
 * 
 * @author iskoulis
 *
 */
public class Metrics {

	private int insertions, deletions, alterations;
	private int tableIns, tableDel, tableAlt;
	private int attrIns, attrDel, attrAlt;
	private int keyAlt;
	private int numOfTables, numOfAttributes;
	private int numOfNewTables, numOfNewAttributes;
	/**
	 * 
	 */
	public Metrics() {
		reset();
	}

	public void reset() {
		insertions = deletions = alterations = 0;
		tableIns = tableDel = 0;
		attrIns = attrDel = 0;
		tableAlt = attrAlt = keyAlt = 0;
		numOfTables = numOfAttributes = 0;
	}

	protected void insertAttr () {
		attrIns++; insertions++;
	}
	protected void insetTable() {
		tableIns++; insertions++;
	}

	protected void deleteAttr() {
		attrDel++; deletions++;
	}
	protected void deleteTable() {
		tableDel++; deletions++;
	}

	protected void alterAttr() {
		attrAlt++;
		alterations++;
	}
	protected void alterTable() {
		tableAlt++;
		// XXX this is wrong, must check before calling this
	}
	protected void alterKey() {
		keyAlt++;
		alterations++;
	}

	protected void setOrigTables(int n) {
		numOfTables = n;
	}
	protected void setOrigAttrs(int n) {
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
		int i[] = {this.attrIns, this.attrDel, this.attrAlt ,this.keyAlt};
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

	public void sanityCheck() throws Exception {
		if(insertions != tableIns + attrIns) throw new Exception("BIV!!");
		if(deletions != tableDel + attrDel) throw new Exception("BIV!!");
	}
}