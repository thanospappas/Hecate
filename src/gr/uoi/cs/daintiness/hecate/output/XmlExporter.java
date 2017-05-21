package gr.uoi.cs.daintiness.hecate.output;
import gr.uoi.cs.daintiness.hecate.transitions.Deletion;
import gr.uoi.cs.daintiness.hecate.transitions.Insertion;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;
import gr.uoi.cs.daintiness.hecate.transitions.Update;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class XmlExporter extends TransitionChangesExporter{
	
	private Transitions transitions;
	
	public XmlExporter(Transitions transitions, String path){
		super(path);
		this.transitions = transitions;
	}
	
	public void exportTransitions(){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Update.class,
					Deletion.class, Insertion.class,
					TransitionList.class, Transitions.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			/*String parrent = (new File(super.getPath())).getParent();
			File dir = new File(parrent + File.separator + "results");
			if (!dir.exists()) {
				dir.mkdir();
			}*/
			
			//String filePath = dir.getPath();
			String filePath = super.getDirectory();
			filePath += File.separator + "transitions.xml";
			jaxbMarshaller.marshal(this.transitions, new FileOutputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
