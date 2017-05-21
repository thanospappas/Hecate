package gr.uoi.cs.daintiness.hecate.sql;

import java.util.HashMap;
import java.util.Map;

public class ForeignKey {
	private Map<Attribute, Attribute> references;

	public ForeignKey() {
		references = new HashMap<Attribute, Attribute>();
	}
	
	public void addReference(Attribute original, Attribute ref) {
		references.put(original, ref);
	}
	
	public boolean containsKey(Attribute attribute){
		if (references.containsKey(attribute)) {
			return true;
		}
		return false;
	}
	
	public Attribute getRef(Attribute attribute) {
		return references.get(attribute);
	}
	
	public String toString() {
		String buff = new String();
		buff = "Foreign Key: ";
		for (Map.Entry<Attribute, Attribute> entry : this.references.entrySet()) {
			Attribute or = entry.getKey();
			Attribute re = entry.getValue();
			buff += or.toString() + " -> " + re.toString() + "\n";
		}
		buff += "\n";
		return buff;
	}
	
	public Map<Attribute, Attribute> getRefs() {
		return references;
	}

	public boolean isEqual(ForeignKey foreignKey) {
		if (this.references.size() == foreignKey.references.size()) {
			// TODO check if keys are equal
			return false;
		} else {
			return false;
		}
	}
}
