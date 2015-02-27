/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.transitions;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author iskoulis
 *
 */
@XmlRootElement
public class Transitions {
	
	@XmlElement(name="transition")
	private Collection<TransitionList> list;
	
	public Transitions() {
		list = new ArrayList<TransitionList>();
	}
	
	public void add(TransitionList in) {
		this.list.add(in);
	}
	
	public Collection<TransitionList> getList() {
		return list;
	}
}
