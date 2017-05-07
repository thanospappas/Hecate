/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.diff;

import gr.uoi.cs.daintiness.hecate.metrics.Metrics;
import gr.uoi.cs.daintiness.hecate.metrics.tables.TablesInfo;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;

/**
 * @author iskoulis
 *
 */
public class DiffResult {

	final public TransitionList transitionList;
	final public Metrics metrics;
	final public TablesInfo tablesInfo;
	/**
	 * 
	 */
	public DiffResult() {
		this.transitionList = new TransitionList();
		this.metrics = new Metrics();
		this.tablesInfo = new TablesInfo();
	}
	
	public void setVersionNames(String oldVersion, String newVersion) {
		this.transitionList.setVersionNames(oldVersion, newVersion);
		this.metrics.setVersionNames(oldVersion, newVersion);
	}
	
	public void clear() {
		this.tablesInfo.clear();
		metrics.resetRevisions();
	}
	
	public TransitionList getTransitionList(){
		return transitionList;
	}
	
	public TablesInfo getTableInfo(){
		return tablesInfo;
	}
}
