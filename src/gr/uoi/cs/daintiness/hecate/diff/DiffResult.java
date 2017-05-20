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

	final private TransitionList transitionList;
	final private Metrics metrics;
	final private TablesInfo tablesInfo;
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
		this.metrics.resetRevisions();
	}
	
	public TransitionList getTransitionList(){
		return this.transitionList;
	}
	
	public TablesInfo getTableInfo(){
		return this.tablesInfo;
	}
	
	public Metrics getMetrics(){
		return this.metrics;
	}
	
}
