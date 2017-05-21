/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.gui.swing;

import gr.uoi.cs.daintiness.hecate.hecatemanager.ApiExecutioner;

import gr.uoi.cs.daintiness.hecate.metrics.Metrics;


import java.io.File;


import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 * @author iskoulis
 *
 */
public class DiffWorker extends SwingWorker<Void, Void> {
	
	private MainPanel mp;
	private ProgressMonitor pm;
	private File oldFile = null;
	private File newFile = null;
	private File folder = null;
	
	private File directory;
	private boolean folderDiffOn;
	
	
	private ApiExecutioner apiExecutioner;
	
	
	public DiffWorker(MainPanel mp,
			          File oldFile, File newFile) {
		this.mp = mp;
		this.oldFile = oldFile;
		this.newFile = newFile;
		folderDiffOn = false;
		
		apiExecutioner = new ApiExecutioner(null);
	}
	
	public DiffWorker(MainPanel mp, File folder) {
		this.mp = mp;
		this.folder = folder;
		
		System.out.println(this.folder.getAbsolutePath());
	
		apiExecutioner = new ApiExecutioner(this.folder.getAbsolutePath());
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		pm = new ProgressMonitor(mp.getRootPane(), "Working...", null, 0, 100);
	

		if (oldFile != null && newFile != null) {
			apiExecutioner.handleSchemaPairs(oldFile,newFile);
		} else if (folder != null){
			folderDiffOn = true;
			
			String[] list = folder.list();
			pm.setMaximum(list.length);
			String path = folder.getAbsolutePath();			
			java.util.Arrays.sort(list);
			
			for (int i = 0; i < list.length-1; i++) {
				pm.setNote("Parsing " + list[i]);
				
				
				apiExecutioner.setOldSchema(path + File.separator + list[i], i);
				
				pm.setNote("Parsing " + list[i+1]);
				
				apiExecutioner.setNewSchema(path + File.separator + list[i+1], i, list);
				
				pm.setNote(list[i] + "-" + list[i+1]);
				
				apiExecutioner.getDifference();
				apiExecutioner.exportMetrics();
				pm.setProgress(i+1);	
			}
			
			apiExecutioner.exportFiles(path, list);
			

			String parent = (new File(path)).getParent();
			directory = new File(parent + File.separator + "results");
			
			folder = null;
		}
		return null;
	}
	
	public Metrics getMetrics() {
		return apiExecutioner.getMetrics();
	}
	
	@Override
	protected void done() {
		
		mp.drawSchema(apiExecutioner.getOldSchema(), "old");
		mp.drawSchema(apiExecutioner.getNewSchema(), "new");
		pm.setProgress(pm.getMaximum());
		
		if(folderDiffOn){
			JOptionPane.showConfirmDialog(null,
	                "Metrics were exported to:" +directory.getPath() ,
	                "Metrics were saved", JOptionPane.DEFAULT_OPTION);
		}
		
		super.done();
	}
}
