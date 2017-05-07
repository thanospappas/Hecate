/**
 * 
 */
package gr.uoi.cs.daintiness.hecate.gui.swing;

import gr.uoi.cs.daintiness.hecate.diff.SqlDifferenceExtractor;
import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.diff.DifferenceExtractor;
//import gr.uoi.cs.daintiness.hecate.io.Export;
import gr.uoi.cs.daintiness.hecate.output.ExportManager;
import gr.uoi.cs.daintiness.hecate.output.FileExporter;
import gr.uoi.cs.daintiness.hecate.output.FileExporterFactory;
import gr.uoi.cs.daintiness.hecate.parser.HecateParser;
import gr.uoi.cs.daintiness.hecate.parser.SqlInputParser;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.sql.Table;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;

import java.io.File;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 * @author iskoulis
 *
 */
public class DiffWorker extends SwingWorker<DiffResult, Integer> {
	
	MainPanel mp;
	ProgressMonitor pm;
	File oldFile = null;
	File newFile = null;
	File folder = null;
	Schema oldSchema;
	Schema newSchema;
	private File directory;
	//private ExportManager exportManager;
	private FileExporter exportManager;
	private SqlInputParser parser;
	private DifferenceExtractor differenceExtractor;
	private DiffResult res;
	
	public DiffWorker(MainPanel mp,
			          File oldFile, File newFile) {
		this.mp = mp;
		this.oldFile = oldFile;
		this.newFile = newFile;
		this.parser = new HecateParser();
		this.differenceExtractor = new SqlDifferenceExtractor();
	}
	
	public DiffWorker(MainPanel mp, File folder) {
		this.mp = mp;
		this.folder = folder;
		this.parser = new HecateParser();
		this.differenceExtractor = new SqlDifferenceExtractor();
	}

	@Override
	protected DiffResult doInBackground() throws Exception {
		
		pm = new ProgressMonitor(mp.getRootPane(), "Working...", null, 0, 100);
		res = new DiffResult();

		if (oldFile != null && newFile != null) {
			pm.setMaximum(3);
			
			oldSchema = parser.parse(oldFile.getAbsolutePath());
			pm.setProgress(1);
			newSchema = parser.parse(newFile.getAbsolutePath());
			pm.setProgress(2);
			res = differenceExtractor.getDifference(oldSchema, newSchema);
			pm.setProgress(3);
			oldFile = null;
			newFile = null;
		} else if (folder != null){
			res.clear();
			Transitions trs = new Transitions();
			String[] list = folder.list();
			pm.setMaximum(list.length);
			String path = folder.getAbsolutePath();
			FileExporterFactory exportFactory = new FileExporterFactory();
			
			exportManager = exportFactory.createExportManger(path);
			java.util.Arrays.sort(list);
			//exportManager.exportMetrics(res, path);
			//Export.initCsvOutput(path);
			//Export.initMetrics(path);
			for (int i = 0; i < list.length-1; i++) {
				pm.setNote("Parsing " + list[i]);
				Schema schema = parser.parse(path + File.separator + list[i]);
				
				for (Entry<String, Table> e : schema.getTables().entrySet()) {
					String tname = e.getKey();
					int attrs = e.getValue().getSize();
					res.tablesInfo.addTable(tname, i, attrs);
				}
				pm.setNote("Parsing " + list[i+1]);
				Schema schema2 = parser.parse(path + File.separator + list[i+1]);
				if (i == list.length-2) {
					for (Entry<String, Table> e : schema2.getTables().entrySet()) {
						String tname = e.getKey();
						int attrs = e.getValue().getSize();
						res.tablesInfo.addTable(tname, i+1, attrs);
					}
				}
				pm.setNote(list[i] + "-" + list[i+1]);
				res = differenceExtractor.getDifference(schema, schema2);
				trs.add(res.transitionList);
				
				exportManager.exportMetrics(res, path);
				//Export.exportCSV(res,path);
				pm.setProgress(i+1);
				System.out.println("lala");
			}
			System.out.println("HAHAHA");
			try {
				exportManager.exportTableMetrics(path, res.metrics.getNumRevisions()+1, res.tablesInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exportManager.exportXml(trs, path);
			//Export.csv(trs,path);
			oldSchema = parser.parse(path + File.separator + list[0]);
			newSchema = parser.parse(path + File.separator + list[list.length-1]);
			res = differenceExtractor.getDifference(oldSchema, newSchema);
			String parrent = (new File(path)).getParent();
			directory = new File(parrent + File.separator + "results");
			System.out.println(directory);
									
			folder = null;
		}
		return res;
	}
	
	public DiffResult getRes() {
		return this.res;
	}
	
	@Override
	protected void done() {
		mp.drawSchema(oldSchema, "old");
		mp.drawSchema(newSchema, "new");
		pm.setProgress(pm.getMaximum());
		
		System.out.println(directory);
		JOptionPane.showConfirmDialog(null,
                "Metrics were exported to:" +directory.getPath() , "Metrics were saved", JOptionPane.DEFAULT_OPTION);
		super.done();
	}
}
