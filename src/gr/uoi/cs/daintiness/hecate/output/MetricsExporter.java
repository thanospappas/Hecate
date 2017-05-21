package gr.uoi.cs.daintiness.hecate.output;

import gr.uoi.cs.daintiness.hecate.diff.DiffResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MetricsExporter {

	public MetricsExporter(String path) {
		initMetrics(path);
	}

	public static String getDir(String path) {
		String parent = (new File(path)).getParent();
		File directory = new File(parent + File.separator + "results");
		if (!directory.exists()) {
			directory.mkdir();
		}
		return directory.getPath();
	}

	public void initMetrics(String path) {
		String filePath = getDir(path) + File.separator + "metrics.csv";
		BufferedWriter metrics;
		try {
			metrics = new BufferedWriter(new FileWriter(filePath));
			metrics.write("trID;time;oldVer;newVer;#oldT;#newT;#oldA;#newA"
					+ ";tIns;tDel;aIns;aDel;aTypeAlt;keyAlt;aTabIns;aTabDel\n");
			metrics.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exportMetrics(DiffResult res, String path) throws IOException {
		/*String parrent = (new File(path)).getParent();
		File dir = new File(parrent + File.separator + "results");
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath = dir.getPath() + File.separator + "metrics.csv";*/
		String filePath = getDir(path) + File.separator + "metrics.csv";
		FileWriter fileWriter = new FileWriter(filePath, true);
		BufferedWriter metrics = new BufferedWriter(fileWriter);
		String name = res.getMetrics().getVersionNames()[1];
		String time = name.substring(0, name.length() - 4);
		metrics.write(res.getMetrics().getNumRevisions() + ";" + // trID
				time + ";" + // time
				res.getMetrics().getVersionNames()[0] + ";" + // oldVer
				res.getMetrics().getVersionNames()[1] + ";" + // newVer
				res.getMetrics().getOldSizes()[0] + ";" + // #oldT
				res.getMetrics().getNewSizes()[0] + ";" + // #newT
				res.getMetrics().getOldSizes()[1] + ";" + // #oldA
				res.getMetrics().getNewSizes()[1] + ";" + // #newA
				res.getMetrics().getTableMetrics()[0] + ";" + // tIns
				res.getMetrics().getTableMetrics()[1] + ";" + // tDel
				res.getMetrics().getAttributeMetrics()[0] + ";" + // aIns
				res.getMetrics().getAttributeMetrics()[1] + ";" + // aDel
				res.getMetrics().getAttributeMetrics()[2] + ";" + // aTypeAlt
				res.getMetrics().getAttributeMetrics()[3] + ";" + // keyAlt
				res.getMetrics().getAttributeMetrics()[4] + ";" + // aTabIns
				res.getMetrics().getAttributeMetrics()[5] + "\n" // aTabDel
		);
		metrics.close();
	}

}
