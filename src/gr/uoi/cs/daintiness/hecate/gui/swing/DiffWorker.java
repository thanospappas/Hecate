/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.gui.swing;

import gr.uoi.cs.daintiness.hecate.HecateManager.ApiExecutioner;

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
	
	
	private ApiExecutioner ae;
	
	
	public DiffWorker(MainPanel mp,
			          File oldFile, File newFile) {
		this.mp = mp;
		this.oldFile = oldFile;
		this.newFile = newFile;
		folderDiffOn = false;
		
		ae = new ApiExecutioner(null);
	}
	
	public DiffWorker(MainPanel mp, File folder) {
		this.mp = mp;
		this.folder = folder;
		
		System.out.println(this.folder.getAbsolutePath());
	
		ae = new ApiExecutioner(this.folder.getAbsolutePath());
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		pm = new ProgressMonitor(mp.getRootPane(), "Working...", null, 0, 100);
	

		if (oldFile != null && newFile != null) {
			ae.handleSchemaPairs(oldFile,newFile);
		} else if (folder != null){
			folderDiffOn = true;
			
			String[] list = folder.list();
			pm.setMaximum(list.length);
			String path = folder.getAbsolutePath();			
			java.util.Arrays.sort(list);
			
			for (int i = 0; i < list.length-1; i++) {
				pm.setNote("Parsing " + list[i]);
				
				
				ae.setOldSchema(path + File.separator + list[i], i);
				
				pm.setNote("Parsing " + list[i+1]);
				
				ae.setNewSchema(path + File.separator + list[i+1], i, list);
				
				pm.setNote(list[i] + "-" + list[i+1]);
				
				ae.getDifference();
				ae.exportMetrics();
				pm.setProgress(i+1);	
			}
			
			ae.exportFiles(path, list);
			

			String parrent = (new File(path)).getParent();
			directory = new File(parrent + File.separator + "results");
			
			folder = null;
		}
		return null;
	}
	
	public Metrics getMetrics() {
		return ae.getMetrics();
	}
	
	@Override
	protected void done() {
		
		mp.drawSchema(ae.getOldSchema(), "old");
		mp.drawSchema(ae.getNewSchema(), "new");
		pm.setProgress(pm.getMaximum());
		
		if(folderDiffOn){
			JOptionPane.showConfirmDialog(null,
	                "Metrics were exported to:" +directory.getPath() , "Metrics were saved", JOptionPane.DEFAULT_OPTION);
		}
		
		super.done();
	}
}
