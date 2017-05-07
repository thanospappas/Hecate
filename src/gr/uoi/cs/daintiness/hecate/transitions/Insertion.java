/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.transitions;

import gr.uoi.cs.daintiness.hecate.sql.Table;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author iskoulis
 *
 */
@XmlRootElement
public class Insertion extends AtomicChange {

	public Insertion() {
		super();
	}
	
	public void setTable(Table newTable) {
		super.setTable(newTable);
		this.type = "NewTable";
	}
}
