/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.metrics.tables;

import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author iskoulis
 *
 */
public class MetricsOverVersion extends TreeMap<Integer, TableMetrics> {

	private static final long serialVersionUID = 6222279078841584012L;
	
	private Changes total = null; 
	
	public Changes getChanges(int version) {
		return get(version).getChanges();
	}

	public int getSize(int version) {
		return get(version).getSize();
	}

	public int getBirth() {
		return this.firstKey();
	}

	public int getDeath() {
		return this.lastKey();
	}

	public int getLife() {
		return this.size();
	}

	public int getBirthSize() {
		return this.firstEntry().getValue().getSize();
	}

	public int getDeathSize() {
		return this.lastEntry().getValue().getSize();
	}

	public Changes getTotalChanges() {
		if (this.total != null) return this.total;
		Changes c = new Changes();
		for (Entry<Integer, TableMetrics> e : this.entrySet() ) {
			Changes versionChanges = e.getValue().getChanges();
			c.addInsertion(versionChanges.getInsertions());
			c.addDeletion(versionChanges.getDeletions());
			c.addAttrTypeChange(versionChanges.getAttrTypeChange());
			c.addKeyChange(versionChanges.getKeyChange());
		}
		this.total = c;
		return this.total;
	}
}
