/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.transitions;

import gr.uoi.cs.daintiness.hecate.sql.Attribute;
import gr.uoi.cs.daintiness.hecate.sql.Table;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * @author iskoulis
 *
 */
public abstract class AtomicChange {

	@XmlElement(name="table")
	Table affectedTable;
	@XmlElement(name="attribute")
	Collection<Attribute> affectedAtributes;
	@XmlAttribute(name="type")
	String type;
	
	public AtomicChange() {
		affectedTable = null;
		type = null;
		affectedAtributes = new ArrayList<Attribute>();
	}
	
	public void setAttribute(Attribute newAttribute) throws Exception {
		if (type == null) {
			type = "UpdateTable";
		}
		if (affectedTable == null) {
			this.affectedTable = newAttribute.getTable();
		} else if (affectedTable != newAttribute.getTable()){
			throw new Exception("ta ekanes salata!");
		}
		this.affectedAtributes.add(newAttribute);
	}
	

	public void setTable(Table newTable) {
		this.affectedTable = newTable;
		this.affectedAtributes = newTable.getAttrs().values();
	}
	
	public Table getAffectedTable(){
		return affectedTable;
	}
	
	public String getType(){
		return this.type;
	}
	public int getNumOfAffAttributes() {
		return affectedAtributes.size();
	}
	
	public Collection<Attribute> getAffectedAttributes() {
		return affectedAtributes;
	}
	
	
}
