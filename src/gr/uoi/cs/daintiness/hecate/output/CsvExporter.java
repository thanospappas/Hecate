package gr.uoi.cs.daintiness.hecate.output;
import gr.uoi.cs.daintiness.hecate.sql.Attribute;
import gr.uoi.cs.daintiness.hecate.transitions.AtomicChange;
import gr.uoi.cs.daintiness.hecate.transitions.Deletion;
import gr.uoi.cs.daintiness.hecate.transitions.Insertion;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;
import gr.uoi.cs.daintiness.hecate.transitions.Update;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CsvExporter extends TransitionChangesExporter{
	
	private static BufferedWriter transitionsFile;
	
	private Transitions transitions;
	
	public CsvExporter(Transitions transitions,String path){
		super(path);
		this.transitions = transitions;
	}
	
	public void  initCsvOutput(){
		String slashedPath = super.getDirectory() + File.separator;
		String transition = slashedPath + "transitions.csv";
		try {
			transitionsFile = new BufferedWriter(new FileWriter(transition));
			transitionsFile.write("trID;oldVer;newVer;Table;EventType;attrName;attrType;iskey;pkey;fkey\n");
			transitionsFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportTransitions() {
		this.initCsvOutput();
		String filePath = super.getDirectory() + File.separator + "transitions.csv";
		try {
			FileWriter fileWriter = new FileWriter(filePath, true);
			transitionsFile = new BufferedWriter(fileWriter);
			int version = 0;
			for(TransitionList list:transitions.getList()){
				
				version++;
				if(list.getTransition().isEmpty()){
					transitionsFile.write(version + ";");
					transitionsFile.write(list.getOldVersion() + ";");
					transitionsFile.write(list.getNewVersion() + ";");
					transitionsFile.write("-;-;-;-;-;-;-\n");	
				}
				
				ArrayList<AtomicChange> changes = list.getTransition();
				
				for(AtomicChange transition:changes){
					String tableName = transition.getAffectedTable().getName();
					
					Collection<Attribute> attributesAffected = transition.getAffectedAttributes();
					transitionsFile.write(version + ";");
					transitionsFile.write(list.getOldVersion() + ";");
					transitionsFile.write(list.getNewVersion() + ";");
					boolean firstTime = true;
					for(Attribute attribute: attributesAffected){
						if(firstTime){
							firstTime = false;
						}
						else{
							transitionsFile.write(version + ";");
							transitionsFile.write(list.getOldVersion() + ";");
							transitionsFile.write(list.getNewVersion() + ";");
						}
						transitionsFile.write(tableName + ";");
						if(transition instanceof Insertion)
							transitionsFile.write("Insertion:");
						else if(transition instanceof Deletion)
							transitionsFile.write("Deletion:");
						else if(transition instanceof Update)
							transitionsFile.write("Update:");
						transitionsFile.write(transition.getType() + ";");
						transitionsFile.write(attribute.getName() + ";");
						transitionsFile.write(attribute.getType() + ";");
						transitionsFile.write(attribute.isKey() + ";");
						transitionsFile.write(transition.getAffectedTable().getPrimaryKey().getMode() + ";");
						Attribute reference = transition.getAffectedTable().getForeignKey().getRef(attribute);
						
						if(reference != null){
							transitionsFile.write(reference.getName());
							transitionsFile.write("@" + transition.getAffectedTable().getForeignKey().getRef(attribute).getTable().getName() + "");
						}
						else{
							transitionsFile.write("-");
						}
							transitionsFile.write("\n");
					}
				}
			}
			
			transitionsFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
