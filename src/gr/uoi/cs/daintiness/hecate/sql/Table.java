package gr.uoi.cs.daintiness.hecate.sql;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Table implements SqlItem{
	@XmlElement
	private String name;
	private TreeMap<String, Attribute> attributes;
	@XmlElement
	private PrimaryKey primaryKey;
	@XmlElement
	private ForeignKey foreignKey;
	private int mode;
	
	public Table() {
		this.name = null;
		this.attributes = new TreeMap<String, Attribute>();
		this.primaryKey = null;
		this.foreignKey = new ForeignKey();
	}
	
	public Table(String name) {
		this.name = name;
		this.attributes = new TreeMap<String, Attribute>();
		this.primaryKey = new PrimaryKey();
		this.foreignKey = new ForeignKey();
	}
	
	public Table(String name, TreeMap<String, Attribute> attributes, PrimaryKey pKey) {
		this.name = name;
		this.attributes = new TreeMap<String, Attribute>();
		for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
			this.attributes.put(entry.getKey(), entry.getValue()) ;
		}
		this.primaryKey = pKey;
		this.foreignKey = new ForeignKey();
		this.updateAttributes();
	}
	
	public Table(String n, TreeMap<String, Attribute> a, PrimaryKey p, ForeignKey f) {
		this.name = n;
		this.attributes = a;
		this.primaryKey = p;
		this.foreignKey = f;
		this.updateAttributes();
	}
	
	public void addAttribute(Attribute attribute) {
		this.attributes.put(attribute.getName(), attribute);
		attribute.setTable(this);
		if (attribute.isKey()) {
			addAttrToPrimeKey(attribute);
		}
	}
	
	public void addAttrToPrimeKey(Attribute attribute) {
		attribute.setToKey();
		this.primaryKey.add(attribute);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSize() {
		return attributes.size();
	}
	
	public TreeMap<String, Attribute> getAttrs() {
		return this.attributes;
	}
	
	public PrimaryKey getPrimaryKey() {
		return this.primaryKey;
	}
	
	public ForeignKey getForeignKey() {
		return this.foreignKey;
	}
	
	@Override
	public int getMode(){
		return this.mode;
	}
	
	@Override
	public void setMode(int mode){
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String print() {
		String buff = new String();
		buff = "Table: " + this.name + "\n";
		for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
			Attribute a = entry.getValue();
			buff += "    " + a.print();
			if (foreignKey.containsKey(entry.getValue())) {
				Attribute at = foreignKey.getRef(entry.getValue());
				buff += " -> " + at.getTable().getName() + "." + at.getName();
			}
			buff += "\n";
		}
		return buff;
	}
	
	public Attribute getAttrAt(int i) {
		int c = 0;
		if (i >= 0 && i < attributes.size()){
			for (Map.Entry<String, Attribute> t : attributes.entrySet()) {
				if (c == i) {
					return t.getValue();
				}
				c++;
			}
		}
		return null;
	}

	private void updateAttributes() {
		for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
			entry.getValue().setTable(this);
		}
	}
}
